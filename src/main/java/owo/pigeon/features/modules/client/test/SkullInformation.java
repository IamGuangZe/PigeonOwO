package owo.pigeon.features.modules.client.test;

import net.minecraft.block.BlockSkull;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import owo.pigeon.features.Category;
import owo.pigeon.features.Module;
import owo.pigeon.utils.ChatUtil;

import static owo.pigeon.utils.WorldUtil.isNotNull;

public class SkullInformation extends Module {
    public SkullInformation() {
        super("SkullInformation", Category.CLIENT, -1);
    }

    private BlockPos temp;

    @Override
    public void onUpdate() {
        if (isNotNull()) {
            if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {

                BlockPos blockPos = mc.objectMouseOver.getBlockPos();

                if (mc.theWorld.getBlockState(blockPos).getBlock() instanceof BlockSkull && blockPos != temp) {

                    temp = blockPos;
                    TileEntity tileEntity = mc.theWorld.getTileEntity(blockPos);

                    if (tileEntity instanceof TileEntitySkull) {
                        TileEntitySkull skullTileEntity = (TileEntitySkull) tileEntity;

                        // 获取NBT数据
                        NBTTagCompound nbt = new NBTTagCompound();
                        skullTileEntity.writeToNBT(nbt);

                        // 检查是否有UUID
                        if (nbt.hasKey("Owner")) {
                            NBTTagCompound ownerTag = nbt.getCompoundTag("Owner");
                            if (ownerTag.hasKey("Id")) {
                                String uuid = ownerTag.getString("Id");
                                ChatUtil.sendMessage("Skull UUID: " + uuid);
                            } else {
                                ChatUtil.sendMessage("Skull has no UUID.");
                            }
                        } else {
                            ChatUtil.sendMessage("Skull has no owner.");
                        }
                    }
                }
            }
        }
    }
}
