package owo.pigeon.features.modules.hypixel;

import owo.pigeon.features.Category;
import owo.pigeon.features.Module;
import owo.pigeon.settings.EnableSetting;
import owo.pigeon.utils.FontUtils;
import owo.pigeon.utils.hypixel.HypixelGames;
import owo.pigeon.utils.hypixel.HypixelUtil;
import owo.pigeon.utils.PlayerUtil;

import static owo.pigeon.utils.WorldUtil.isNotNull;

public class ZombieHelper extends Module {
    public ZombieHelper() {
        super("ZombieHelper", Category.HYPIXEL, -1);
    }

    public EnableSetting cyclicSwitch = setting("Cyclic Wwitch",true,"Auto cyclic switch weapons to reduce the impact of gun CD time.",v->true);

    public boolean stats = true;

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
            if (stats) {
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
                    HypixelUtil.isHypixelZombieWeapon(slot) &&
                    cyclicSwitch.getValue()
            ) {
                // 检测下一个物品栏是不是武器
                if (HypixelUtil.isHypixelZombieWeapon(slot + 1)) {
                    PlayerUtil.switchItemSlot(slot + 1);
                } else if (HypixelUtil.isHypixelZombieWeapon(slot + 2)) {
                    PlayerUtil.switchItemSlot(slot + 2);
                } else {
                    PlayerUtil.switchItemSlot(1);
                }
            }
        }
    }
}
