package owo.pigeon.features.player;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;
import owo.pigeon.modules.Module;
import owo.pigeon.utils.CheckUtil;
import owo.pigeon.utils.PlayerUtil;

public class AutoTool extends Module {
    public AutoTool() {
        super("AutoTool", Keyboard.KEY_G);
    }

    public static boolean switchback = true;
    public static boolean israwslot = true;
    public static int rawslot = 0;

    @Override
    public void onUpdate() {
        if (CheckUtil.NotnullCheck()) {
            if (CheckUtil.isBreakingBlock()) {
                BlockPos blockPos = mc.objectMouseOver.getBlockPos();
                Block block = mc.theWorld.getBlockState(blockPos).getBlock();
                ItemStack tool = PlayerUtil.getBestToolA(block);
                if (tool != null) {
                    PlayerUtil.switchItemSlot(PlayerUtil.getSlotfromItemStack(tool),tool);
                    israwslot = false;
                } else {
                    PlayerUtil.switchItemSlot(rawslot,null);
                }
            } else {
                if (!israwslot) {
                    if (switchback) {
                        PlayerUtil.switchItemSlot(rawslot,null);
                    }
                    israwslot = true;
                }
                rawslot = mc.thePlayer.inventory.currentItem;
            }
        }
    }
}
