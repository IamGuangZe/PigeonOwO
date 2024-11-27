package owo.pigeon.utils;

import net.minecraft.util.ChatComponentText;

import static owo.pigeon.modules.Module.mc;

public class ChatUtil {

    // 允许用&代替§使文本上色
    public static String parseColor(String msg) {
        return msg.replaceAll("&","§")  //& -> §
                .replaceAll("§§","&");  //&& -> §§ -> &
    }

    // 使文本去色
    public static String removeColor(String msg) {
        return msg.replaceAll("§.", "");
    }

    // 发送客户端消息
    public static void sendRawMessage(String message) {
        mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(message));
    }

    // 发送带客户端前缀且可上色的消息
    public static void sendMessage(String message) {
        sendRawMessage(parseColor("&8[&3PigeonOwO&8]&r " + message));
    }

    // 发送带客户端前缀且不可上色的消息
    public static void sendMessageWithoutColor(String message){
        sendRawMessage(parseColor("&8[&3PigeonOwO&8]&r ") + message);
    }

    // 在屏幕上绘制文本
    public static void drawString(String text, int x, int y) {
        // 给文本上色
        drawStringWithoutColor(parseColor(text),x,y);
    }

    // 在屏幕上绘制不带颜色的文本
    public static void drawStringWithoutColor(String text, int x, int y) {
        // 使用 Minecraft 的 fontRenderer 绘制文字
        mc.fontRendererObj.drawStringWithShadow(text,x,y,0xFFFFFF);
    }
}
