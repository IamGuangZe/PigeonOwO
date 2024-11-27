package owo.pigeon.features.movement;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;
import owo.pigeon.modules.Module;
import owo.pigeon.utils.CheckUtil;

public class Parkour extends Module {

    public Parkour() {
        super("Parkour", Keyboard.KEY_M);
    }

    @Override
    public void onUpdate() {
        if (CheckUtil.NotnullCheck()) {
            if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ)).getBlock() == Blocks.air && mc.thePlayer.onGround) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), true);
                //mc.thePlayer.jump();
            } else {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
            }
        }
    }
}
