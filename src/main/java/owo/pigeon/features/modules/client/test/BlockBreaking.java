package owo.pigeon.features.modules.client.test;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.util.BlockPos;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.PlayerUtil;
import owo.pigeon.utils.WorldUtil;

public class BlockBreaking extends Module {

    public BlockBreaking() {
        super("BlockBreaking", Category.CLIENT,-1);
    }

    @Override
    public void onTick() {
        if (WorldUtil.isNotNull()) {
            if (PlayerUtil.isBreakingBlock()) {
                BlockPos blockPos = mc.objectMouseOver.getBlockPos();
                Block block = mc.thePlayer.worldObj.getBlockState(blockPos).getBlock();
                ChatUtil.sendMessage("Player is Breaking Block!");
                ChatUtil.sendMessage(String.valueOf(blockPos));
                ChatUtil.sendMessage(String.valueOf(block));
                if (block instanceof BlockColored) {
                    BlockColored blockColored = (BlockColored) block;
                    int colorIndex = blockColored.getMetaFromState(mc.theWorld.getBlockState(blockPos));
                    ChatUtil.sendMessage("Block Color : " + colorIndex);
                }
            }
        }
    }
}
