package owo.pigeon.gui;

public abstract class AbstractDisplableItem {

    public int x, y, width, height = 0;

    protected abstract void drawScreen(int mouseX, int mouseY, float partialTicks);

    public boolean isHovered(int mouseX, int mouseY) {
        return (mouseX > x && mouseX < x + width) && (mouseY > y && mouseY < y + height);
    }

    protected static boolean isHovered(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX - width <= x && mouseY >= y && mouseY - height <= y;//获取鼠标位置是否在指定位置
    }
}
