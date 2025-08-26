package owo.pigeon.features.modules.hypixel;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import owo.pigeon.features.Category;
import owo.pigeon.features.Module;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import owo.pigeon.utils.ChatUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.*;

// 想把Autotip的功能搬过来 技术不够遂放弃

/**
 * AutoTips 单类实现（Java 8，支持 Hypixel，带日志输出）
 */
public class AutoTip extends Module {
    public AutoTip() {
        super("AutoTip", Category.HYPIXEL, -1);
    }
    private final Minecraft mc = Minecraft.getMinecraft();

    // 任务管理
    private final Map<TaskType, ScheduledFuture<?>> tasks = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

    // Tip 队列
    private final Queue<Tip> tipQueue = new ConcurrentLinkedQueue<>();

    // 会话信息
    private SessionKey sessionKey;
    private boolean loggedIn = false;

    // 默认定时
    private long keepAliveRate = 60;
    private long tipWaveRate = 30;
    private long tipCycleRate = 5;

    private final String version = "3.0.1";
    private final String mcVersion = "1.8.9";

    @Override
    public void onEnable() {
        if (!onHypixel()) return;
        login();
    }

    @Override
    public void onDisable() {
        logout();
        scheduler.shutdownNow();
    }

    private boolean onHypixel() {
        ServerData server = mc.getCurrentServerData();
        return server != null && server.serverIP.toLowerCase().contains("hypixel");
    }

    // ------------------- Session -------------------
    private void login() {
        if (loggedIn) return;
        GameProfile profile = mc.getSession().getProfile();
        String uuid = profile.getId().toString().replace("-", "");
        String hash = HashUtil.hash(uuid + HashUtil.getNextSalt());

        LoginRequest request = new LoginRequest(profile, hash);
        LoginReply reply = request.execute();

        if (reply != null && reply.isSuccess()) {
            sessionKey = reply.getSessionKey();
            loggedIn = true;

            keepAliveRate = reply.getKeepAliveRate();
            tipWaveRate = reply.getTipWaveRate();
            tipCycleRate = reply.getTipCycleRate();

            addRepeatingTask(TaskType.KEEP_ALIVE, this::keepAlive, keepAliveRate, keepAliveRate);
            addRepeatingTask(TaskType.TIP_WAVE, this::tipWave, 0, tipWaveRate);

            ChatUtil.sendMessage("[AutoTips] 登录成功，SessionKey: " + sessionKey.getKey());
        } else {
            ChatUtil.sendMessage("[AutoTips] 登录失败！");
        }
    }

    private void logout() {
        if (!loggedIn) return;
        if (sessionKey != null) new LogoutRequest(sessionKey).execute();
        loggedIn = false;
        sessionKey = null;
        tipQueue.clear();
        cancelTask(TaskType.KEEP_ALIVE);
        cancelTask(TaskType.TIP_WAVE);
        cancelTask(TaskType.TIP_CYCLE);
        ChatUtil.sendMessage("[AutoTips] 已登出");
    }

    // ------------------- Tip -------------------
    private void tipWave() {
        ChatUtil.sendMessage("[AutoTips] TIP_WAVE 执行，获取最新 Tip 列表");
        if (sessionKey == null) return;

        TipRequest request = new TipRequest(sessionKey);
        TipReply reply = request.execute();

        if (reply != null && reply.isSuccess()) {
            tipQueue.addAll(reply.getTips());
            ChatUtil.sendMessage("[AutoTips] Tip 队列已更新: " + getTipQueueString());
        } else {
            tipQueue.addAll(TipReply.getDefault().getTips());
            ChatUtil.sendMessage("[AutoTips] 获取 Tip 队列失败，使用默认 'all'");
        }

        addRepeatingTask(TaskType.TIP_CYCLE, this::tipCycle, 0, tipCycleRate);
    }

    private void tipCycle() {
        ChatUtil.sendMessage("[AutoTips] TIP_CYCLE 执行");
        if (!onHypixel() || tipQueue.isEmpty()) {
            cancelTask(TaskType.TIP_CYCLE);
            ChatUtil.sendMessage("[AutoTips] TIP_CYCLE 队列为空或不在 Hypixel，任务取消");
            return;
        }

        Tip tip = tipQueue.poll();
        if (tip != null) {
            mc.thePlayer.sendChatMessage(tip.getAsCommand());
            ChatUtil.sendMessage("[AutoTips] 已打赏: " + tip.toString() + "，剩余队列: " + getTipQueueString());
        }
    }

    private void keepAlive() {
        // 可选心跳接口，可调用服务器心跳
    }

    // ------------------- Task -------------------
    private void addRepeatingTask(TaskType type, Runnable r, long delay, long period) {
        if (tasks.containsKey(type)) return;
        Runnable wrapped = () -> {
            ChatUtil.sendMessage("[AutoTips] 触发任务: " + type.name());
            r.run();
        };
        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(wrapped, delay, period, TimeUnit.SECONDS);
        tasks.put(type, future);
    }

    private void cancelTask(TaskType type) {
        if (tasks.containsKey(type)) {
            tasks.get(type).cancel(true);
            tasks.remove(type);
        }
    }

    private enum TaskType { KEEP_ALIVE, TIP_WAVE, TIP_CYCLE }

    // ------------------- Tip 队列打印 -------------------
    private String getTipQueueString() {
        if (tipQueue.isEmpty()) return "空";
        StringBuilder sb = new StringBuilder();
        for (Tip t : tipQueue) {
            sb.append(t.toString()).append(", ");
        }
        return sb.substring(0, sb.length() - 2);
    }

    // ------------------- 核心类 -------------------
    public static class SessionKey {
        private final String key;
        public SessionKey(String key) { this.key = key; }
        public String getKey() { return key; }
        @Override
        public String toString() { return key; }
    }

    public static class Tip {
        private final String gamemode;
        private final String username;

        public Tip(String gamemode, String username) {
            this.gamemode = gamemode;
            this.username = username;
        }

        public String getGamemode() { return gamemode; }
        public String getUsername() { return username != null ? username : ""; }
        public String getAsCommand() { return "/tip " + toString(); }
        @Override
        public String toString() {
            return (username != null && !username.isEmpty() ? username + " " : "") + gamemode;
        }
    }

    public static class HashUtil {
        private static final SecureRandom RANDOM = new SecureRandom();
        public static String getNextSalt() { return new BigInteger(130, RANDOM).toString(32); }
        public static String hash(String str) {
            try {
                byte[] digest = digest(str, "SHA-1");
                return new BigInteger(digest).toString(16);
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalArgumentException(e);
            }
        }
        private static byte[] digest(String str, String algorithm) throws NoSuchAlgorithmException {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] strBytes = str.getBytes(StandardCharsets.UTF_8);
            return md.digest(strBytes);
        }
    }

    // ------------------- Login -------------------
    private class LoginRequest  {
        private final GameProfile profile;
        private final String hash;

        public LoginRequest(GameProfile profile, String hash) {
            this.profile = profile;
            this.hash = hash;
        }

        public LoginReply execute() {
            int totalTips = getTotalTips(); // 获取累计小费

            // 构建请求
            HttpUriRequest request = GetBuilder.of("login")
                    .addParameter("username", profile.getName())
                    .addParameter("uuid", profile.getId().toString().replace("-", ""))
                    .addParameter("tips", totalTips)
                    .addParameter("v", version)
                    .addParameter("mc", mcVersion)
                    .addParameter("os", System.getProperty("os.name"))
                    .addParameter("hash", hash)
                    .build();

            // 打印参数调试
            ChatUtil.sendMessage("[AutoTips] 登录参数 "
                    + ": username=" + profile.getName()
                    + ", uuid=" + profile.getId().toString().replace("-", "")
                    + ", tips=" + totalTips
                    + ", v=" + version
                    + ", mc=" + mcVersion
                    + ", os=" + System.getProperty("os.name")
                    + ", hash=" + hash);

            // 发送请求并获取返回
            Optional<LoginReply> opt = RequestHandler.getReply(request, LoginReply.class);

            // 判断失败条件
            if (!opt.isPresent() || opt.get().sessionKey == null || "dummy".equals(opt.get().sessionKey.getKey())) {
                ChatUtil.sendMessage("[AutoTips] 登录失败！返回 JSON: " + RequestHandler.lastJson);
                return new LoginReply(false); // 登录失败
            }

            // 登录成功
            LoginReply real = opt.get();
            ChatUtil.sendMessage("[AutoTips] 登录成功！SessionKey: " + real.sessionKey.getKey());
            return real;
        }

        private int getTotalTips() {
            int tipsSent = 0;      // 已发送小费总数
            int tipsReceived = 0;  // 已收到小费总数
            return tipsSent + tipsReceived;
        }
    }


    private static class LoginReply {
        private boolean success = true;
        private SessionKey sessionKey = new SessionKey("dummy");
        private long keepAliveRate = 60;
        private long tipWaveRate = 30;
        private long tipCycleRate = 5;

        public LoginReply() {}
        public LoginReply(boolean success) { this.success = success; }

        public boolean isSuccess() { return success; }
        public SessionKey getSessionKey() { return sessionKey; }
        public long getKeepAliveRate() { return keepAliveRate; }
        public long getTipWaveRate() { return tipWaveRate; }
        public long getTipCycleRate() { return tipCycleRate; }
    }

    // ------------------- Logout -------------------
    private class LogoutRequest {
        private final SessionKey sessionKey;
        public LogoutRequest(SessionKey sessionKey) { this.sessionKey = sessionKey; }
        public void execute() {
            HttpUriRequest request = GetBuilder.of("logout")
                    .addParameter("key", sessionKey.getKey())
                    .build();
            RequestHandler.getReply(request, Void.class);
        }
    }

    // ------------------- Tip -------------------
    private class TipRequest {
        private final SessionKey sessionKey;
        public TipRequest(SessionKey sessionKey) { this.sessionKey = sessionKey; }
        public TipReply execute() {
            HttpUriRequest request = GetBuilder.of("tip")
                    .addParameter("key", sessionKey.getKey())
                    .build();
            return RequestHandler.getReply(request, TipReply.class)
                    .orElseGet(TipReply::getDefault);
        }
    }

    private static class TipReply {
        private List<Tip> tips = new ArrayList<>();
        private boolean success = true;

        public TipReply() {}
        public TipReply(boolean success) { this.success = success; }
        public List<Tip> getTips() { return tips; }
        public boolean isSuccess() { return success; }
        public static TipReply getDefault() {
            TipReply reply = new TipReply();
            reply.tips.add(new Tip("all", null));
            return reply;
        }
    }

    // ------------------- HTTP 请求工具 -------------------
    private static class GetBuilder {
        private static final String BASE = "https://api.autotip.pro/";
        private final RequestBuilder builder;

        private GetBuilder(String endpoint) { this.builder = RequestBuilder.get().setUri(BASE + endpoint); }
        public static GetBuilder of(String endpoint) { return new GetBuilder(endpoint); }
        public GetBuilder addParameter(String k, Object v) { builder.addParameter(k, String.valueOf(v)); return this; }
        public HttpUriRequest build() { return builder.build(); }
    }

    private static class RequestHandler {
        private static final Gson GSON = new Gson();
        public static String lastJson = ""; // ← 新增

        public static <T> Optional<T> getReply(HttpUriRequest request, Class<T> clazz) {
            try {
                HttpURLConnection conn = (HttpURLConnection) request.getURI().toURL().openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("User-Agent", "Autotip v1.0");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                InputStream input = (conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) ?
                        conn.getInputStream() : conn.getErrorStream();

                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                byte[] data = new byte[1024];
                int nRead;
                while ((nRead = input.read(data)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                buffer.flush();
                String json = new String(buffer.toByteArray(), StandardCharsets.UTF_8);
                input.close();

                lastJson = json; // ← 保存 JSON
                if (json.isEmpty()) return Optional.empty();

                T obj = GSON.fromJson(json, clazz);
                return Optional.ofNullable(obj);

            } catch (IOException | JsonParseException e) {
                e.printStackTrace();
                ChatUtil.sendMessage("[AutoTips] 请求失败: " + e.getMessage());
                return Optional.empty();
            }
        }
    }

}
