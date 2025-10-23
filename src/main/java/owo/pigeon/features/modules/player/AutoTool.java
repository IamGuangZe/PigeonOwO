package owo.pigeon.features.modules.player;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.settings.EnableSetting;
import owo.pigeon.utils.PlayerUtil;
import owo.pigeon.utils.WorldUtil;

public class AutoTool extends Module {
    public AutoTool() {
        super("AutoTool", Category.PLAYER, -1);
    }

    public EnableSetting switchBack = setting("switch-back", true, v -> true);

    private boolean israwslot = true;
    private int rawslot = 0;

    @Override
    public void onTick() {
        if (WorldUtil.isNotNull()) {
            if (PlayerUtil.isBreakingBlock()) {
                BlockPos blockPos = mc.objectMouseOver.getBlockPos();
                Block block = mc.theWorld.getBlockState(blockPos).getBlock();
                ItemStack tool = PlayerUtil.getBestToolFromHotbar(block);
                if (tool != null) {
                    PlayerUtil.switchItemSlot(PlayerUtil.getSlotfromItemStackEx(tool, true));
                    israwslot = false;
                } else {
                    PlayerUtil.switchItemSlot(rawslot);
                }
            } else {
                if (!israwslot) {
                    if (switchBack.getValue()) {
                        PlayerUtil.switchItemSlot(rawslot);
                    }
                    israwslot = true;
                }
                rawslot = mc.thePlayer.inventory.currentItem;
            }
        }
    }
}
