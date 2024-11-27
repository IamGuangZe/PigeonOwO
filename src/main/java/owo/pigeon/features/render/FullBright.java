package owo.pigeon.features.render;

import org.lwjgl.input.Keyboard;
import owo.pigeon.modules.Module;

public class FullBright extends Module {
    public FullBright() {
        super("FullBright", Keyboard.KEY_K);
    }

    public float rawgamma;

    public void onUpdate() {
        mc.gameSettings.gammaSetting = 15;
    }

    public void onEnable() {
        rawgamma = mc.gameSettings.gammaSetting;
    }

    public void onDisable() {
        if (rawgamma == 15){
            mc.gameSettings.gammaSetting = 1;
        } else {
            mc.gameSettings.gammaSetting = rawgamma;
        }
    }
}
