package owo.pigeon.utils;

import net.minecraft.util.ChatComponentText;

import static owo.pigeon.features.Module.mc;

public class ChatUtil {

    // 发送客户端消息
    public static void sendRawMessage(String message) {
        mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(message));
    }

    // 发送带客户端前缀且可上色的消息
    public static void sendMessage(String message) {
        sendRawMessage(OtherUtil.parseColor("&8[&3PigeonOwO&8]&r " + message));
    }

    // 发送带客户端前缀且不可上色的消息
    public static void sendMessageWithoutColor(String message){
        sendRawMessage(OtherUtil.parseColor("&8[&3PigeonOwO&8]&r ") + message);
    }

    // 发送自定义前缀且可上色的消息
    public static void sendCustomPrefixMessage(String prefix,String message) {
        sendRawMessage(OtherUtil.parseColor("&8[&3" + prefix + "&8]&r " + message));
    }
}
