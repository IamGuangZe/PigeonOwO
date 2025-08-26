package owo.pigeon.features.modules.render;

import owo.pigeon.features.Category;
import owo.pigeon.features.Module;

public class FullBright extends Module {
    public FullBright() {
        super("FullBright", Category.RENDER,-1);
    }

    private float rawgamma;

    public void onUpdate() {
        mc.gameSettings.gammaSetting = 15;
    }

    public void onEnable() {
        rawgamma = mc.gameSettings.gammaSetting;
    }

    public void onDisable() {
        if (rawgamma > 1){
            mc.gameSettings.gammaSetting = 1;
        } else {
            mc.gameSettings.gammaSetting = rawgamma;
        }
    }
}
