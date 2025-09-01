package owo.pigeon.features.modules.hypixel;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import owo.pigeon.features.Category;
import owo.pigeon.features.Module;
import owo.pigeon.settings.EnableSetting;
import owo.pigeon.utils.FontUtils;
import owo.pigeon.utils.PlayerUtil;
import owo.pigeon.utils.hypixel.HypixelGames;
import owo.pigeon.utils.hypixel.HypixelUtil;

import static owo.pigeon.utils.WorldUtil.isNotNull;

public class ZombieHelper extends Module {
    public ZombieHelper() {
        super("ZombieHelper", Category.HYPIXEL, -1);
    }

    public EnableSetting hud = setting("HUD",true,"",v->true);
    public EnableSetting cyclicSwitch = setting("Cyclic Wwitch",true,"Auto cyclic switch weapons to reduce the impact of gun CD time.",v->true);
    public EnableSetting thirdGun = setting("Third Gun",false,"Switch includes the third gun.",v->true);
    public EnableSetting smartThirdGun = setting("Smart Third Gun",true,"Use the third gun when Giant or The Old One spawning.",v->true);

    /*@Override
    public void onUpdate() {
        if (isNotnull()) {
            if (!HypixelUtil.isInGame("zombies")) {
                ChatUtil.sendMessage("&cYou are Not in Zombies!");
                disable();
            }
        }
    }*/

    @Override
    public void onRender2D() {
        if (isNotNull()) {
            if (hud.getValue() && HypixelUtil.isInGame(HypixelGames.ZOMBIES)) {
                FontUtils.drawStringWithShadow("Zombies", 5, 5);
                FontUtils.drawStringWithShadow("Sword : " + HypixelUtil.getHypixelZombieWeapon(0), 5, 5 + h);
                FontUtils.drawStringWithShadow("Gun #1 : " + HypixelUtil.getHypixelZombieWeapon(1), 5, 5 + h * 2);
                FontUtils.drawStringWithShadow("Gun #2 : " + HypixelUtil.getHypixelZombieWeapon(2), 5, 5 + h * 3);
                FontUtils.drawStringWithShadow("Gun #3 : " + HypixelUtil.getHypixelZombieWeapon(3), 5, 5 + h * 4);
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

                // 是否使用3rd
                boolean third = thirdGun.getValue() && smartThirdGun();

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

    public boolean smartThirdGun () {
        boolean third = false;

        if (!smartThirdGun.getValue()) {
            return false;
        }

        for (Entity entity : mc.theWorld.loadedEntityList) {
            // Giant
            if (entity instanceof EntityGiantZombie) {
                third = true;
            }

            // The old one (Code sourced from ZombieCat-https://github.com/iM4dCat/ZombieCat)
            if (entity instanceof EntityZombie && ((EntityZombie) entity).isChild() && entity.getInventory() != null && entity.getInventory()[0] != null && entity.getInventory()[0].getItem() == Items.diamond_sword) {
                third = true;
            }
        }

        return third;
    }
}