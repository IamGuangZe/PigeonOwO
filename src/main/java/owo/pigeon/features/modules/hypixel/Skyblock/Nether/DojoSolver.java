package owo.pigeon.features.modules.hypixel.Skyblock.Nether;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.MovingObjectPosition;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.settings.EnableSetting;
import owo.pigeon.utils.PlayerUtil;
import owo.pigeon.utils.hypixel.skyblock.Dojo;
import owo.pigeon.utils.hypixel.skyblock.Island;
import owo.pigeon.utils.hypixel.skyblock.SkyblockUtil;

import static owo.pigeon.utils.WorldUtil.isNotNull;

public class DojoSolver extends Module {
    public DojoSolver() {
        super("DojoSolver", Category.HYPIXEL, -1);
    }
    public EnableSetting Force = setting("force",true,"Hide bad Zombie",v->true);
    public EnableSetting Discipline = setting("discipline",true,"Auto switch sword in discipline.",v->true);

    @Override
    public void onUpdate() {
        if (isNotNull()) {
            if (SkyblockUtil.isIsland(Island.CrimsonIsle)) {
                if (Discipline.getValue()) {
                    if (SkyblockUtil.isDojo(Dojo.Discipline)) {
                        if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {

                            Entity target = mc.objectMouseOver.entityHit;

                            if (target instanceof EntityZombie) {

                                EntityZombie zombie = (EntityZombie) target;

                                ItemArmor helmet = (ItemArmor) zombie.getCurrentArmor(3).getItem();

                                if (helmet.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
                                    PlayerUtil.switchItemSlot(0);
                                }
                                if (helmet.getArmorMaterial() == ItemArmor.ArmorMaterial.IRON) {
                                    PlayerUtil.switchItemSlot(1);
                                }
                                if (helmet.getArmorMaterial() == ItemArmor.ArmorMaterial.GOLD) {
                                    PlayerUtil.switchItemSlot(2);
                                }
                                if (helmet.getArmorMaterial() == ItemArmor.ArmorMaterial.DIAMOND) {
                                    PlayerUtil.switchItemSlot(3);
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    @Override
    public void onRender3D() {
        if (SkyblockUtil.isIsland(Island.CrimsonIsle)) {
            if (Force.getValue()){
                if (SkyblockUtil.isDojo(Dojo.Force)) {
                    for (Entity entity : mc.theWorld.loadedEntityList) {
                        if (entity instanceof EntityZombie) {
                            EntityZombie zombie = (EntityZombie) entity;
                            if (zombie.getCurrentArmor(3)!=null) {
                                ItemArmor helmet = (ItemArmor) zombie.getCurrentArmor(3).getItem();

                                if (helmet.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
                                    mc.theWorld.removeEntity(entity);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
