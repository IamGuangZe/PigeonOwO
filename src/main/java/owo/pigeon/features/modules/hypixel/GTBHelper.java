package owo.pigeon.features.modules.hypixel;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import owo.pigeon.features.Category;
import owo.pigeon.features.Module;
import owo.pigeon.settings.EnableSetting;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.FontUtils;
import owo.pigeon.utils.OtherUtil;
import owo.pigeon.utils.WorldUtil;
import owo.pigeon.utils.hypixel.HypixelData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GTBHelper extends Module {
    public GTBHelper() {
        super("GTBHelper", Category.HYPIXEL,-1);

        for (String word : HypixelData.BTGLIST) {
            preprocessedList.add(new WordData(word));
        }
    }

    public EnableSetting autoAnswer = setting("Auto Answer",false,"Automatically respond only when there is just one remaining result.",v->true);
    public EnableSetting secondAnswer = setting("Second Answer",true,"Only automatically respond after others have finished guessing.",v->true);

    String Theme = "&mUnknown";

    @SubscribeEvent
    public void onChatReceive(ClientChatReceivedEvent event) {
        if (GTBHelper.this.isEnable()) {
            String message = OtherUtil.removeColor(event.message.getFormattedText());
            if (event.type == 0 && message.contains("Round:")) {
                reload();
            }

            if (event.type == 2 && message.contains("The theme is ")) {
                String oldTheme = Theme;
                Theme = message.replaceAll("The theme is ", "");
                if (Objects.equals(oldTheme, Theme) || !Theme.contains("_")) {
                    return;
                }

                String[] guesses = guess();
                String output = String.join("&r, &6", guesses);
                ChatUtil.sendCustomPrefixMessage(this.name,"&aGuess (" + guesses.length + "): &6" + output);

                if (autoAnswer.getValue() && guesses.length == 1 && !guesses[0].contains("Not Found")) {
                    mc.thePlayer.sendChatMessage(guesses[0]);
                }
            }
        }
    }

    @Override
    public void onRender2D() {
        if (WorldUtil.isNotNull()){
            FontUtils.drawStringWithShadow("Guess The Build",5,5);
            FontUtils.drawStringWithShadow("Theme : " + Theme,5,5 + h);
        }
    }

    @Override
    public void onWorldLoad() {
        reload();
    }

    @Override
    public void onEnable() {
        reload();
    }

    private void reload() {
        Theme = "&cUnknown";
    }

    // 以下内容都是Chatgpt写的 我懒得写了(
    private final List<WordData> preprocessedList = new ArrayList<>();

    private static class WordData {
        String word;
        String[] parts;
        int[] partLengths;

        WordData(String word) {
            this.word = word;
            this.parts = word.split(" ");
            this.partLengths = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                partLengths[i] = parts[i].length();
            }
        }
    }

    private String[] guess() {
        if (Theme == null || Theme.isEmpty()) return new String[]{"&cNot Found"};

        List<String> result = new ArrayList<>();

        // 预处理 Theme
        String[] themeParts = Theme.split(" ");
        int[] themeLengths = new int[themeParts.length];
        for (int i = 0; i < themeParts.length; i++) {
            themeLengths[i] = themeParts[i].length();
        }

        for (WordData data : preprocessedList) {
            // 段数和长度快速过滤
            if (data.parts.length != themeParts.length) continue;

            boolean lengthMismatch = false;
            for (int i = 0; i < data.parts.length; i++) {
                if (data.partLengths[i] != themeLengths[i]) {
                    lengthMismatch = true;
                    break;
                }
            }
            if (lengthMismatch) continue;

            // 按段字符匹配
            boolean match = true;
            for (int i = 0; i < data.parts.length; i++) {
                String wordPart = data.parts[i];
                String themePart = themeParts[i];

                // 新增长度检查
                if (wordPart.length() != themePart.length()) {
                    match = false;
                    break;
                }

                for (int j = 0; j < themePart.length(); j++) {
                    char tc = themePart.charAt(j);
                    if (tc != '_' && tc != wordPart.charAt(j)) {
                        match = false;
                        break;
                    }
                }
                if (!match) break;
            }
            if (match) {
                result.add(data.word);
            }
        }
        if (result.isEmpty()) {
            return new String[]{"&cNot Found"};
        }
        return result.toArray(new String[0]);
    }
}
