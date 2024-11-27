package owo.pigeon.features.movement;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import owo.pigeon.modules.Module;
import owo.pigeon.utils.CheckUtil;

public class LegitSpeed extends Module {
    public LegitSpeed() {
        super("LegitSpeed", Keyboard.KEY_V);
    }

    @Override
    public void onUpdate() {
        if (CheckUtil.NotnullCheck()) {
            if (!Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {
                if (!mc.thePlayer.isInWater() || !mc.thePlayer.isInLava()) {
                    if ((mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0) && mc.thePlayer.onGround) {
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), true);
                        //mc.thePlayer.jump();  //在一格水中会强制跳跃而不是上浮
                    } else {
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
                    }
                }
            }
        }
    }

    @Override
    public void onDisable() {
        if (!Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())){
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
        }
    }
}
