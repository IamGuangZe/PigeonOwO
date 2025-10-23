package owo.pigeon.features.modules.movement;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.utils.WorldUtil;

public class LegitSpeed extends Module {
    public LegitSpeed() {
        super("LegitSpeed", Category.MOVEMENT, -1);
    }

    @Override
    public void onTick() {
        if (WorldUtil.isNotNull()) {
            if (!Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {
                if (!mc.thePlayer.isInWater() && !mc.thePlayer.isInLava() && !mc.thePlayer.capabilities.allowFlying) {
                    if (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0) {
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
        if (!Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
        }
    }
}
