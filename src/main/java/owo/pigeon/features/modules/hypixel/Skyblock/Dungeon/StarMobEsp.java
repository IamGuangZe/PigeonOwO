package owo.pigeon.features.modules.hypixel.Skyblock.Dungeon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.AxisAlignedBB;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.utils.RenderUtil;

import java.awt.*;

public class StarMobEsp extends Module {

    public StarMobEsp() {
        super("StarMobEsp", Category.HYPIXEL, -1);
    }

    @Override
    public void onRender3D() {
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityArmorStand && entity.hasCustomName()) {
                EntityArmorStand e = (EntityArmorStand) entity;
                if (e.hasCustomName()) {
                    if (e.getCustomNameTag().contains("✯") && e.getCustomNameTag().contains("❤"))
                        RenderUtil.drawOutlinedBoxEsp(new AxisAlignedBB(entity.posX - 0.5D, entity.posY - 2.0D, entity.posZ - 0.5D, entity.posX + 0.5D, entity.posY, entity.posZ + 0.5D), new Color(255, 0, 0));
                }
            }
        }
    }
}
