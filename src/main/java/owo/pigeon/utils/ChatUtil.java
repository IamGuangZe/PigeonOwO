package owo.pigeon.utils;

import net.minecraft.util.ChatComponentText;
import owo.pigeon.features.utils.SafeMessage;

import static owo.pigeon.features.modules.Module.mc;

public class ChatUtil {

    // 发送客户端消息
    public static void sendRawMessage(String message) {
        if (mc.ingameGUI != null && mc.ingameGUI.getChatGUI() != null) {
            mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(message));
        }
    }

    // 发送不带前缀且可上色的消息
    public static void sendColoredRawMessage(String message) {
        sendRawMessage(OtherUtil.parseColor(message));
    }

    // 发送带客户端前缀且可上色的消息
    public static void sendMessage(String message) {
        sendRawMessage(OtherUtil.parseColor("&8[&3PigeonOwO&8]&r " + message));
    }

    // 发送带客户端前缀且不可上色的消息
    public static void sendUncoloredMessage(String message){
        sendRawMessage(OtherUtil.parseColor("&8[&3PigeonOwO&8]&r ") + message);
    }

    // 发送自定义前缀且可上色的消息
    public static void sendCustomPrefixMessage(String prefix,String message) {
        sendRawMessage(OtherUtil.parseColor("&8[&3" + prefix + "&8]&r " + message));
    }

    // 直到mc.ingameGUI不为null时发送带客户端前缀且可上色的消息
    public static void sendSafeMessage(String message) {
        SafeMessage.messages.add(message);
    }

    // 发送支持换行的带客户端前缀且可上色消息
    public static void sendMultiLineMessage(String message) {
        if (message == null) return;
        String[] lines = message.split("\n");
        for (String line : lines) {
            sendMessage(line);
        }
    }
}
