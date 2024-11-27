package owo.pigeon.features.movement;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;
import owo.pigeon.modules.Module;
import owo.pigeon.utils.CheckUtil;

public class Eagle extends Module {
    public Eagle() {
        super("Eagle", Keyboard.KEY_N);
    }

    @Override
    public void onUpdate() {
        if (CheckUtil.NotnullCheck()) {
            if (!Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
                if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ)).getBlock() == Blocks.air && mc.thePlayer.onGround && mc.currentScreen == null) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                    //mc.thePlayer.setSneaking(true);
//                    mc.thePlayer.moveStrafing = (float)((double) mc.thePlayer.movementInput.moveStrafe * 0.3D);
//                    mc.thePlayer.moveForward = (float)((double)mc.thePlayer.movementInput.moveForward * 0.3D);
                } else {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
//                    mc.thePlayer.movementInput.sneak = false;
//                    mc.thePlayer.movementInput.updatePlayerMoveState();
                }
                //mc.playerController.updateController();
            }
        }
    }

    @Override
    public void onDisable() {
        if (!Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())){
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
        }
    }
}
