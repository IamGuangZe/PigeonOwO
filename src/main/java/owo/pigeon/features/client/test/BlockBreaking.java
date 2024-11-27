package owo.pigeon.features.client.test;

import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;
import owo.pigeon.modules.Module;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.CheckUtil;

public class BlockBreaking extends Module {

    public BlockBreaking() {
        super("BlockBreaking", Keyboard.KEY_L);
    }

    @Override
    public void onUpdate() {
        if (CheckUtil.NotnullCheck()) {
            if (CheckUtil.isBreakingBlock()) {
                BlockPos blockPos = mc.objectMouseOver.getBlockPos();
                ChatUtil.sendMessage("Player is Breaking Block!");
                ChatUtil.sendMessage(String.valueOf(blockPos));
                ChatUtil.sendMessage(String.valueOf(mc.thePlayer.worldObj.getBlockState(blockPos).getBlock()));
            }
        }
    }
}
