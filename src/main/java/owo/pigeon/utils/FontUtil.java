package owo.pigeon.utils;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StringUtils;

import static owo.pigeon.features.modules.Module.mc;

public class FontUtil {
    public static final FontRenderer fontRenderer = mc.fontRendererObj;

    private static String parseColor(String text) {
        return text.replaceAll("&", "§").replaceAll("§§", "&");
    }

    private static String removeColor(String text) {
        return text.replaceAll("§.", "");
    }

    public static int getStringWidth(String text) {
        return fontRenderer.getStringWidth(StringUtils.stripControlCodes(text));
    }

    public static int getFontHeight() {
        return fontRenderer.FONT_HEIGHT;
    }

    public static void drawString(String text, int x, int y, int color) {
        fontRenderer.drawString(text, x, y, color);
    }

    public static void drawString(String text, int x, int y) {
        fontRenderer.drawString(parseColor(text), x, y, 0xFFFFFF);
    }

    public static void drawStringWithShadow(String text, double x, double y, int color) {
        fontRenderer.drawStringWithShadow(text, (float) x, (float) y, color);
    }

    public static void drawStringWithShadow(String text, double x, double y) {
        fontRenderer.drawStringWithShadow(parseColor(text), (float) x, (float) y, 0xFFFFFF);
    }

    public static void drawCenteredString(String text, int x, int y, int color) {
        drawString(text, x - fontRenderer.getStringWidth(text) / 2, y, color);
    }

    public static void drawCenteredStringWithShadow(String text, double x, double y, int color) {
        drawStringWithShadow(text, x - (double) (fontRenderer.getStringWidth(text) / 2), y, color);
    }

    public static void drawTotalCenteredString(String text, int x, int y, int color) {
        drawString(text, x - fontRenderer.getStringWidth(text) / 2, y - fontRenderer.FONT_HEIGHT / 2, color);
    }

    public static void drawTotalCenteredStringWithShadow(String text, double x, double y, int color) {
        drawStringWithShadow(text, x - (double) (fontRenderer.getStringWidth(text) / 2), y - (double) ((float) fontRenderer.FONT_HEIGHT / 2.0f), color);
    }
}
