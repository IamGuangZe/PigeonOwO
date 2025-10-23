package owo.pigeon.features.modules.client;

import org.lwjgl.input.Keyboard;
import owo.pigeon.Pigeon;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.settings.ModeSetting;

public class ClickGui extends Module {
    public ClickGui() {
        super("ClickGui", Category.CLIENT, Keyboard.KEY_RSHIFT);
    }

    public enum styleEnum {
        OLD, NEW
    }

    public ModeSetting<styleEnum> style = setting("style", styleEnum.NEW, "", v -> true);

    @Override
    public void onEnable() {
        mc.displayGuiScreen(Pigeon.clickGuiScreen);
    }

    @Override
    public void onDisable() {
        if (mc.currentScreen == Pigeon.clickGuiScreen) mc.displayGuiScreen(null);
    }
}
