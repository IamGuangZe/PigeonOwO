package owo.pigeon.utils;

import com.google.common.collect.Sets;
import net.minecraft.item.ItemStack;

import java.util.Set;

public class HypixelUtil {
    private static final Set<String> SKYBLOCK = Sets.newHashSet(
            "SKYBLOCK", "SKYBLOCK CO-OP", "空岛生存", "空島生存", "SKIBLOCK"
    );

    public static final Set<String> ZOMBIES = Sets.newHashSet(
            "ZOMBIES", "僵尸末日", "殭屍末日"
    );

    public static final String[] JOINLOBBY = {
            "joined the lobby!",
            "进入了大厅！",
            "加入了大廳！"
    };

    public static final String[] GOLD = {
            "\\+\\d+ Gold",
            "\\+\\d+金钱"
    };

    public static final String[] REPAIREDWINDOW = {
            "Repairing windows. Keep holding SNEAK to continue repairing.",
            "Stopped repairing. There are enemies nearby!",
            "You can't repair windows while enemies are nearby!",
            "You have fully repaired this window!",

            "正在修复窗户。保持潜行状态以继续修复。",
            "因附近有敌人，已停止修复！",
            "附近有敌人时你将无法修复窗户！",
            "你已经完全修复此窗户！"
    };

    public static final String[] COIN = {
            "\\+\\d+ coins!",
            "\\+\\d+ 硬币!"
    };


    public static boolean isInGame(String game) {
        String scoreboardname = PlayerUtil.getScoreboardDisplayNameWithoutColor();
        if (scoreboardname == null) {
            return false;
        }
        switch (game.toLowerCase()) {
            case "zombies": {
                if (ZOMBIES.contains(scoreboardname)) {
                    return true;
                }
                break;
            }
            case "skyblock": {
                if (SKYBLOCK.contains(scoreboardname)) {
                    return true;
                }
                break;
            }
            default: throw new IllegalArgumentException("Inviad game type");
        }
        return false;
    }

    public static boolean isZombiesStart() {
        // 暂时搁置
        if (isInGame("zombies")) {
            return true;
        }
        return false;
    }

    public static boolean isHypixelZombieWeapon(int slot) {
        if (!isInGame("zombies") || slot > 3 || slot < 0) {
            return false;
        }
        ItemStack itemStack = PlayerUtil.getItemStackfromSlot(slot);
        if (itemStack == null) {
            return false;   // 物品为空时则直接返回为假
        } else {
            return !String.valueOf(itemStack).contains("item.deyPowder") &&   // 物品不为染料
                    !itemStack.getDisplayName().contains("#");  // 物品名称不包含# (Gun #2 / Gun #3)
        }
    }

    public static String getHypixelZombieWeapon(int slot) {
        if (isHypixelZombieWeapon(slot)) {
            return String.valueOf(PlayerUtil.getItemStackfromSlot(slot).getDisplayName());
        }
        return "&cNone";
    }
}
