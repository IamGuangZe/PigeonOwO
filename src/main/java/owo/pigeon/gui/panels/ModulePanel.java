package owo.pigeon.gui.panels;

import net.minecraft.client.gui.Gui;
import owo.pigeon.features.modules.Module;
import owo.pigeon.features.modules.client.ClickGui;
import owo.pigeon.gui.AbstractDisplableItem;
import owo.pigeon.utils.ModuleUtil;

import java.awt.*;

import static owo.pigeon.utils.FontUtil.fontRenderer;

public class ModulePanel extends AbstractDisplableItem {

    private Module module;

    private boolean hovered;

    public ModulePanel(Module module, int x, int y, int width, int height) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    protected void drawScreen(int mouseX, int mouseY, float partialTicks) {
        hovered = isHovered(mouseX, mouseY, x, y, width, height);

        switch (((ClickGui) ModuleUtil.getModule(ClickGui.class)).style.getValue()) {
            case OLD:
                int color_old = module.isEnable() ?
                        new Color(20, 20, 20, 186).getRGB() :
                        new Color(50, 50, 50, 186).getRGB();
                Gui.drawRect(x,y,x + width,y + height, color_old);
                fontRenderer.drawStringWithShadow(
                        module.name,
                        (x + ((float) width / 2)) - ((float) fontRenderer.getStringWidth(module.name) / 2),
                        (float) (y + (height / 2)) - (float) fontRenderer.FONT_HEIGHT / 2,
                        Color.WHITE.getRGB());
                break;

            case NEW:
            default:
                Gui.drawRect(x, y, x + width, y + height, new Color(0, 0, 0, 100).getRGB());
                int color_new = module.isEnable() ? Color.WHITE.getRGB() : Color.GRAY.getRGB();
                fontRenderer.drawStringWithShadow(
                        module.name,
                        (x + ((float) width / 2)) - ((float) fontRenderer.getStringWidth(module.name) / 2),
                        (float) (y + (height / 2)) - (float) fontRenderer.FONT_HEIGHT / 2,
                        color_new);
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (hovered && mouseButton == 0) {
            module.toggle();
        }
    }
}
