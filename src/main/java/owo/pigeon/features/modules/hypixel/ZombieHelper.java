package owo.pigeon.features.modules.hypixel;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import owo.pigeon.features.Category;
import owo.pigeon.features.Module;
import owo.pigeon.settings.EnableSetting;
import owo.pigeon.utils.FontUtil;
import owo.pigeon.utils.OtherUtil;
import owo.pigeon.utils.PlayerUtil;
import owo.pigeon.utils.WorldUtil;
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
    public EnableSetting smartThirdGun = setting("Smart Third Gun",true,"(For AA)Use the third gun when Giant and The Old One spawning.",v->true);

    private int round = -1;
    private boolean third = false;

    @Override
    public void onUpdate() {
        if (WorldUtil.isNotNull()) {
            if (HypixelUtil.isInGame(HypixelGames.ZOMBIES)) {
                third = thirdGun.getValue() || smartThirdGun();

                String sidebarLine = WorldUtil.getSidebarLineTopDown(3);
                if (sidebarLine != null) {
                    round = OtherUtil.regexGetPartInteger("Round (\\d+)", OtherUtil.removeColor(sidebarLine), 1);
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

                FontUtil.drawStringWithShadow(setting,5,5 + h * 5);
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

    public boolean smartThirdGun () {
        boolean third_ = false;

        if (!smartThirdGun.getValue()) {
            return false;
        }

        for (Entity entity : mc.theWorld.loadedEntityList) {
            // Giant
            if (entity instanceof EntityGiantZombie) {
                third_ = true;
            }

            // The old one (Code sourced from ZombieCat-https://github.com/iM4dCat/ZombieCat)
            if (entity instanceof EntityZombie && ((EntityZombie) entity).isChild() && entity.getInventory() != null && entity.getInventory()[0] != null && entity.getInventory()[0].getItem() == Items.diamond_sword) {
                third_ = true;
            }
        }

        if (round > 52) {
            third_ = true;
        }
        return third_;
    }
}