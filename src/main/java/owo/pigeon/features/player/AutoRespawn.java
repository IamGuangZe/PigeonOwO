package owo.pigeon.features.player;

import org.lwjgl.input.Keyboard;
import owo.pigeon.modules.Module;

public class AutoRespawn extends Module {
    public AutoRespawn() {
        super("AutoRespawn", Keyboard.KEY_J);
    }

    @Override
    public void onUpdate() {
        if (mc.theWorld != null) {
            if (mc.thePlayer.isDead) {
                mc.thePlayer.respawnPlayer();
            }
        }
    }
}
