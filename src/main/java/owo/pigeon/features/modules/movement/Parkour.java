package owo.pigeon.features.modules.movement;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.utils.WorldUtil;

public class Parkour extends Module {

    public Parkour() {
        super("Parkour", Category.MOVEMENT, -1);
    }

    @Override
    public void onTick() {
        if (WorldUtil.isNotNull()) {
            if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ)).getBlock() == Blocks.air && mc.thePlayer.onGround) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), true);
                //mc.thePlayer.jump();
            } else {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
            }
        }
    }
}
