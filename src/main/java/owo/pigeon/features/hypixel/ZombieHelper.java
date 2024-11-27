package owo.pigeon.features.hypixel;

import org.lwjgl.input.Keyboard;
import owo.pigeon.modules.Module;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.HypixelUtil;
import owo.pigeon.utils.PlayerUtil;

import static owo.pigeon.utils.CheckUtil.NotnullCheck;

public class ZombieHelper extends Module {
    public ZombieHelper() {
        super("ZombieHelper", Keyboard.KEY_Z);
    }

    public boolean stats = true;
    public boolean autoswitch = true;
    int h = mc.fontRendererObj.FONT_HEIGHT + 2;

    @Override
    public void onUpdate() {
        if (NotnullCheck()) {
            if (HypixelUtil.isInGame("zombies")) {
                if (stats) {
                    ChatUtil.drawString("Zombies Stats", 5, 5);
                    ChatUtil.drawString("Sword : " + HypixelUtil.getHypixelZombieWeapon(0), 5, 5 + h);
                    ChatUtil.drawString("Gun #1 : " + HypixelUtil.getHypixelZombieWeapon(1), 5, 5 + h * 2);
                    ChatUtil.drawString("Gun #2 : " + HypixelUtil.getHypixelZombieWeapon(3), 5, 5 + h * 3);
                    ChatUtil.drawString("Gun #3 : " + HypixelUtil.getHypixelZombieWeapon(2), 5, 5 + h * 4);
                }
            } else {
                ChatUtil.sendMessage("&cYou are Not in Zombies!");
                disable();
            }
        }
    }

    @Override
    public void onRightClick() {
        if (NotnullCheck()) {
            int slot = mc.thePlayer.inventory.currentItem;
            if (HypixelUtil.isInGame("zombies") &&
                    HypixelUtil.isHypixelZombieWeapon(slot) &&
                    autoswitch
            ) {
                // 检测下一个物品栏是不是武器
                if (HypixelUtil.isHypixelZombieWeapon(slot + 1)) {
                    PlayerUtil.switchItemSlot(slot + 1,null);
                } else if (HypixelUtil.isHypixelZombieWeapon(slot + 2)) {
                    PlayerUtil.switchItemSlot(slot + 2,null);
                } else {
                    PlayerUtil.switchItemSlot(1,null);
                }
            }
        }
    }
}
