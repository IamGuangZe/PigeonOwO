package owo.pigeon.gui;

import net.minecraft.client.gui.GuiScreen;
import owo.pigeon.gui.panels.CategoryPanel;
import owo.pigeon.features.Category;
import owo.pigeon.features.Module;
import owo.pigeon.features.modules.client.ClickGui;
import owo.pigeon.utils.ModuleUtil;

import java.io.IOException;
import java.util.ArrayList;

public class ClickGuiScreen extends GuiScreen {

    private final ArrayList<CategoryPanel> categoryPanels = new ArrayList<>();

    public ClickGuiScreen() {
        int startX = 10;
        for (Category category : Category.values()) {
            categoryPanels.add(new CategoryPanel(category,startX,10,85,20));
            startX += 85 + 3;
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
        for (CategoryPanel panel : categoryPanels) {
            panel.drawScreen(mouseX, mouseY, partialTicks);
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
