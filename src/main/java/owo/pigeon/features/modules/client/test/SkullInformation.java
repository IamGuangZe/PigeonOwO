package owo.pigeon.features.modules.client.test;

import net.minecraft.block.BlockSkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.WorldUtil;

import static owo.pigeon.utils.WorldUtil.isNotNull;

public class SkullInformation extends Module {
    public SkullInformation() {
        super("SkullInformation", Category.CLIENT, -1);
    }

    private BlockPos temp;

    @Override
    public void onTick() {
        if (isNotNull()) {
            if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {

                BlockPos blockPos = mc.objectMouseOver.getBlockPos();

                if (mc.theWorld.getBlockState(blockPos).getBlock() instanceof BlockSkull && blockPos != temp) {

                    temp = blockPos;

                    String uuid = WorldUtil.getSkullUUID(blockPos);

                    if (uuid != null) {
                        ChatUtil.sendMessage("Skull UUID: " + uuid);
                    } else {
                        ChatUtil.sendMessage("Skull has no UUID.");
                    }
                }
            }
        }
    }
}
