package owo.pigeon.features.modules.hypixel;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.AxisAlignedBB;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.injections.mixins.IAccessorEntityPlayer;
import owo.pigeon.settings.EnableSetting;
import owo.pigeon.settings.FloatSetting;
import owo.pigeon.settings.ModeSetting;
import owo.pigeon.utils.*;
import owo.pigeon.utils.hypixel.HypixelGames;
import owo.pigeon.utils.hypixel.HypixelUtil;

import java.awt.*;

import static owo.pigeon.utils.WorldUtil.isNotNull;

public class ZombieHelper extends Module {
    public ZombieHelper() {
        super("ZombieHelper", Category.HYPIXEL, -1);
    }

    public enum espModeEnum {
        FULL, EYE, BOTH
    }

    public EnableSetting hud = setting("hud", true, v -> true);
    public EnableSetting easyRevive = setting("easy-revive", true, v -> true);
    public EnableSetting cyclicSwitch = setting("cyclic-switch", true, v -> true);
    public EnableSetting thirdGun = setting("3rd", false, v -> true);
    public EnableSetting smartThirdGun = setting("smart-3rd", true, v -> true);
    public FloatSetting health = setting("health", 10F, 1F, 20F, v -> true);

    public EnableSetting esp = setting("esp", true, v -> true);
    public ModeSetting<espModeEnum> espMode = setting("esp-mode", espModeEnum.EYE, v -> true);

    public EnableSetting hidePlayer = setting("hide-player", true, v -> true);
    public FloatSetting distance = setting("distance", 2.5F, 0F, 10F, v -> true);

    private int round = -1;
    private boolean third = false;

    @Override
    public void onTick() {
        if (WorldUtil.isNotNull()) {
            if (HypixelUtil.isInGame(HypixelGames.ZOMBIES)) {
                third = thirdGun.getValue() || smartThirdGun();

                String sidebarLine = WorldUtil.getSidebarLineTopDown(3);
                if (sidebarLine != null) {
                    round = OtherUtil.regexGetPartInteger("Round (\\d+)", OtherUtil.removeColor(sidebarLine), 1);
                }

                if (easyRevive.getValue()) {
                    for (Entity entity : mc.theWorld.loadedEntityList) {
                        if (entity instanceof EntityPlayer) {
                            EntityPlayer player = (EntityPlayer) entity;
                            if (((IAccessorEntityPlayer) player).getSleeping()) {
                                player.setEntityBoundingBox(new AxisAlignedBB(
                                        player.posX - 0.3,
                                        player.posY,
                                        player.posZ - 0.3,
                                        player.posX + 0.3,
                                        player.posY + 1.8,
                                        player.posZ + 0.3
                                ));
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onRightClickEnd() {
        if (isNotNull()) {
            int slot = mc.thePlayer.inventory.currentItem;
            if (HypixelUtil.isInGame(HypixelGames.ZOMBIES) &&
                    HypixelUtil.isHypixelZombieGun(slot) &&
                    cyclicSwitch.getValue()
            ) {
                int targetSlot = -1;

                // 按顺序检测后一格或后两格是否为枪 如果是则为目标格
                if (HypixelUtil.isHypixelZombieGun(slot + 1)) {
                    targetSlot = slot + 1;
                } else if (HypixelUtil.isHypixelZombieGun(slot + 2)) {
                    targetSlot = slot + 2;
                }

                if (targetSlot == 3 && !third) {
                    PlayerUtil.switchItemSlot(1);
                } else if (targetSlot != -1) {
                    PlayerUtil.switchItemSlot(targetSlot);
                } else {
                    PlayerUtil.switchItemSlot(1);
                }
            }
        }
    }

    @Override
    public void onRender2D() {
        if (isNotNull()) {
            if (hud.getValue() && HypixelUtil.isInGame(HypixelGames.ZOMBIES)) {
                FontUtil.drawStringWithShadow("Zombies", 5, 5);
                FontUtil.drawStringWithShadow("Sword : " + HypixelUtil.getHypixelZombieWeapon(0), 5, 5 + h);
                FontUtil.drawStringWithShadow("Gun #1 : " + HypixelUtil.getHypixelZombieWeapon(1), 5, 5 + h * 2);
                FontUtil.drawStringWithShadow("Gun #2 : " + HypixelUtil.getHypixelZombieWeapon(2), 5, 5 + h * 3);
                FontUtil.drawStringWithShadow("Gun #3 : " + HypixelUtil.getHypixelZombieWeapon(3), 5, 5 + h * 4);

                String setting = "Round : " + round + " | 3rd : ";
                if (third) {
                    setting += "&a&lTrue";
                } else {
                    setting += "&c&lFalse";
                }

                FontUtil.drawStringWithShadow(setting, 5, 5 + h * 5);
            }
        }
    }

    @Override
    public void onRender3D() {
        if (isNotNull()) {
            if (esp.getValue() && HypixelUtil.isInGame(HypixelGames.ZOMBIES)) {
                for (Entity entity : mc.theWorld.loadedEntityList) {
                    if (entity instanceof EntityLivingBase &&
                            !(entity instanceof EntityChicken) &&
                            !(entity instanceof EntityCow && ((EntityCow) entity).isChild()) &&
                            !(entity instanceof EntityPig && ((EntityPig) entity).isChild()) &&
                            !(entity instanceof EntityWolf && ((EntityWolf) entity).isChild()) &&
                            !(entity instanceof EntitySheep && ((EntitySheep) entity).isChild()) &&
                            !(entity instanceof EntityPlayer) &&
                            !(entity instanceof EntityWither) &&
                            !(entity instanceof EntityArmorStand) &&
                            entity.isEntityAlive()
                    ) {
                        Color color = new Color(0, 255, 255);
                        if (entity instanceof EntityGiantZombie) {
                            color = new Color(102, 51, 153);
                        } else if (entity instanceof EntityZombie && ((EntityZombie) entity).isChild() && entity.getInventory() != null && entity.getInventory()[0] != null && entity.getInventory()[0].getItem() == Items.diamond_sword) {
                            color = new Color(0, 255, 0);
                        }

                        if (isEyeHeightValid((EntityLivingBase) entity)) {
                            switch (espMode.getValue()) {
                                case FULL:
                                    RenderUtil.drawSmoothOutlinedBoxEsp(entity, color);
                                    break;

                                case EYE:
                                    RenderUtil.drawSmoothCustomBoxEsp(entity, getEyeBoundingBox((EntityLivingBase) entity), color);
                                    break;

                                case BOTH:
                                    RenderUtil.drawSmoothOutlinedBoxEsp(entity, new Color(255, 255, 255));
                                    RenderUtil.drawSmoothCustomBoxEsp(entity, getEyeBoundingBox((EntityLivingBase) entity), color);
                            }
                        } else {
                            RenderUtil.drawSmoothOutlinedBoxEsp(entity, color);
                        }
                    }

                    if (entity instanceof EntityPlayer && !(entity instanceof EntityPlayerSP)) {
                        EntityPlayer player = (EntityPlayer) entity;
                        if (hidePlayer.getValue()) {
                            double dx = mc.thePlayer.posX - player.posX;
                            double dy = mc.thePlayer.posY - player.posY;
                            double dz = mc.thePlayer.posZ - player.posZ;
                            double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);

                            // 小于设定距离则设为隐身，否则显示
                            player.setInvisible(dist < distance.getValue());
                        } else {
                            player.setInvisible(false);
                        }
                    }
                }
            }
        }
    }

    private boolean smartThirdGun() {
        boolean smartThird = false;

        if (!smartThirdGun.getValue()) {
            return false;
        }

        for (Entity entity : mc.theWorld.loadedEntityList) {
            // Giant
            if (entity instanceof EntityGiantZombie) {
                smartThird = true;
            }

            // The old one (Code sourced from ZombieCat-https://github.com/iM4dCat/ZombieCat)
            if (entity instanceof EntityZombie && ((EntityZombie) entity).isChild() && entity.getInventory() != null && entity.getInventory()[0] != null && entity.getInventory()[0].getItem() == Items.diamond_sword) {
                smartThird = true;
            }
        }

        if (mc.thePlayer.getHealth() <= health.getValue()) {
            smartThird = true;
        }

        if (round == 36 || round > 52) {
            smartThird = true;
        }
        return smartThird;
    }

    private boolean isEyeHeightValid(EntityLivingBase entity) {

        double eyeHeight = entity.getEyeHeight();

        if (eyeHeight <= 0) {
            return false;
        }

        double headTop = entity.posY + entity.height;
        double eyeLevel = entity.posY + eyeHeight;
        double eyeToHead = headTop - eyeLevel;

        // 检查2倍眼睛到头顶的距离是否超过全身高度
        return (2 * eyeToHead) <= entity.height;
    }

    private AxisAlignedBB getEyeBoundingBox(EntityLivingBase entity) {
        double eyeHeight = entity.getEyeHeight();
        double headTop = entity.posY + entity.height;
        double eyeLevel = entity.posY + eyeHeight;

        double eyeToHead = headTop - eyeLevel;

        return new AxisAlignedBB(
                entity.posX - entity.width / 2,
                headTop - 2 * eyeToHead,
                entity.posZ - entity.width / 2,
                entity.posX + entity.width / 2,
                headTop,
                entity.posZ + entity.width / 2
        );
    }
}
