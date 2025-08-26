package owo.pigeon.features.modules.client.test;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSkull;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import owo.pigeon.features.Category;
import owo.pigeon.features.Module;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.WorldUtil;

public class EntityInformation extends Module {
    public EntityInformation() {
        super("EntityInformation", Category.CLIENT, -1);
    }

    private Entity temp;
    @Override
    public void onUpdate() {
        if (WorldUtil.isNotNull()) {
            if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {

                Entity target = mc.objectMouseOver.entityHit;

                if (target != null && target != temp) {
                    temp = target;
                    ChatUtil.sendMessage(String.valueOf(target));
                    ChatUtil.sendMessage("Name : " + target.getName());

                    // 检测是否为盔甲架
                    if (target instanceof EntityArmorStand) {
                        EntityArmorStand armorStand = (EntityArmorStand) target;
                        ItemStack helmet = armorStand.getCurrentArmor(3); // 索引 3 是头盔
                        if (helmet != null) {
                            ChatUtil.sendMessage("Helmet: " + helmet);

                            // 检测头盔是否为头颅
                            if (helmet.getItem() instanceof ItemSkull) {
                                NBTTagCompound tagCompound = helmet.getTagCompound();
                                if (tagCompound != null && tagCompound.hasKey("SkullOwner", 10)) { // 检查 NBT 中的 SkullOwner
                                    NBTTagCompound skullOwner = tagCompound.getCompoundTag("SkullOwner");
                                    if (skullOwner.hasKey("Id")) { // 获取 UUID
                                        String uuid = skullOwner.getString("Id");
                                        ChatUtil.sendMessage("Helmet is a Skull with UUID: " + uuid);
                                    } else {
                                        ChatUtil.sendMessage("Helmet is a Skull but no UUID found.");
                                    }
                                } else {
                                    ChatUtil.sendMessage("Helmet is a Skull but no SkullOwner NBT.");
                                }
                            }

                        } else {
                            ChatUtil.sendMessage("Helmet: None");
                        }
                    }
                }
            }
        }
    }
}
