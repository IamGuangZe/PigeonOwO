package owo.pigeon.gui;

import net.minecraft.client.gui.GuiScreen;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.features.modules.client.ClickGui;
import owo.pigeon.gui.panels.CategoryPanel;
import owo.pigeon.utils.ModuleUtil;

import java.io.IOException;
import java.util.ArrayList;

public class ClickGuiScreen extends GuiScreen {

    public final ArrayList<CategoryPanel> categoryPanels = new ArrayList<>();

    public ClickGuiScreen() {
        int x = 5;
        int y = 5;
        int width = 90;
        int height = 19;
        for (Category category : Category.values()) {
            categoryPanels.add(new CategoryPanel(category,x,y,width,height));
            x += width + 2;
            //y += height + 5;
        }
    }

    @Override
    public void onGuiClosed() {
        ModuleUtil.getAllModules()
                .stream().filter(it -> it instanceof ClickGui)
                .filter(Module::isEnable)
                .forEach(Module::disable);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        for (CategoryPanel categorypanel : categoryPanels) {
            categorypanel.drawScreen(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (CategoryPanel categorypanel : categoryPanels) {
            categorypanel.mouseClicked(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        for (CategoryPanel categorypanel : categoryPanels) {
            categorypanel.mouseReleased(mouseX, mouseY, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }
}
