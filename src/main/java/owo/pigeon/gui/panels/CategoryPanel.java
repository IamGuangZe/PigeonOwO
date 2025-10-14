package owo.pigeon.gui.panels;

import net.minecraft.client.gui.Gui;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.gui.AbstractDisplableItem;
import owo.pigeon.utils.ModuleUtil;

import java.awt.*;
import java.util.ArrayList;

import static owo.pigeon.utils.FontUtil.fontRenderer;

public class CategoryPanel extends AbstractDisplableItem {
    private final Category category;

    private boolean movepanel;
    private boolean hovered;
    private boolean displaymodule;
    private ArrayList<ModulePanel> modulePanels = new ArrayList<>();

    private int mx;
    private int my;

    public CategoryPanel(Category category, int x, int y, int width, int height) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        hovered = isHovered(mouseX, mouseY, x, y, width, height);
        if (movepanel) {
            x = mx + mouseX;
            y = my + mouseY;
        }

        Gui.drawRect(x,y,x + width,y + height, new Color(0, 0, 0, 255).getRGB());
        fontRenderer.drawStringWithShadow(
                category.name(),
                (x + ((float) width / 2)) - ((float) fontRenderer.getStringWidth(category.name()) / 2),
                (float) (y + (height / 2)) - (float) fontRenderer.FONT_HEIGHT / 2,
                Color.WHITE.getRGB());

        modulePanels.clear();
        if (displaymodule) {
            ArrayList<Module> modules = new ArrayList<>( ModuleUtil.getAllModulesFromCategory(category) );
            int startY = y + height;
            for (Module m : modules) {
                modulePanels.add(new ModulePanel(m, x, startY, width, height));
                startY += height;
            }

            for (ModulePanel modulePanel : modulePanels) {
                modulePanel.drawScreen(mouseX, mouseY, 20);
            }
        }
    }

    public Category getCategory() {
        return category;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (hovered && mouseButton == 0) {
            movepanel = true;
            mx = x - mouseX;
            my = y - mouseY;
        } else if (hovered && mouseButton == 1) {
            displaymodule = !displaymodule;
        }

        for (ModulePanel modulePanel : modulePanels) {
            modulePanel.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (state == 0){
            movepanel = false;
        }
    }
}
