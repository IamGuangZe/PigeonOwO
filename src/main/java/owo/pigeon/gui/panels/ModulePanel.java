package owo.pigeon.gui.panels;

import net.minecraft.client.gui.Gui;
import owo.pigeon.features.modules.Module;
import owo.pigeon.gui.AbstractDisplableItem;

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
        int color = module.isEnable() ?
                new Color(20, 20, 20, 186).getRGB() :
                new Color(50, 50, 50, 186).getRGB();
        Gui.drawRect(x,y,x + width,y + height, color);
        fontRenderer.drawStringWithShadow(
                module.name,
                (x + ((float) width / 2)) - ((float) fontRenderer.getStringWidth(module.name) / 2),
                (float) (y + (height / 2)) - (float) fontRenderer.FONT_HEIGHT / 2,
                Color.WHITE.getRGB());
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (hovered && mouseButton == 0) {
            module.toggle();
        }
    }
}
