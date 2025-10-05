package owo.pigeon.utils.hypixel;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import owo.pigeon.utils.PlayerUtil;
import owo.pigeon.utils.WorldUtil;

import static owo.pigeon.features.Module.mc;

public class HypixelUtil {
    public static boolean isInHypixel() {
        ServerData serverData = mc.getCurrentServerData();
        if (serverData == null) return false;

        String ip = serverData.serverIP == null ? "" : serverData.serverIP.toLowerCase();
        String name = serverData.serverName == null ? "" : serverData.serverName.toLowerCase();
        String firstLine = WorldUtil.getSidebarLineBottonUp(1) == null ? "" : WorldUtil.getSidebarLineBottonUp(1).toLowerCase();

        return ip.contains("hypixel.net") || name.contains("hypixel") || firstLine.contains("hypixel");
    }

    public static boolean isInGame(HypixelGames game) {
        String scoreboardname = WorldUtil.getScoreboardDisplayNameWithoutColor();
        if (scoreboardname == null) {
            return false;
        }

        switch (game) {
            case MURDER:
                return HypixelData.MURDER.contains(scoreboardname);
            case SKYBLOCK:
                return HypixelData.SKYBLOCK.contains(scoreboardname);
            case ZOMBIES:
                return HypixelData.ZOMBIES.contains(scoreboardname);
            case PIXELPARTY:
                return HypixelData.PIXELPARTY.contains(scoreboardname);
            default:
                return false;
        }
    }

    public static boolean isNPC(Entity entity) {
        if (entity instanceof EntityOtherPlayerMP) {

            EntityLivingBase entityLivingBase = (EntityLivingBase) entity;

            return entityLivingBase.getUniqueID().version() == 2;

        } else {
            return false;
        }
    }

    public static boolean isHypixelZombieWeapon(int slot) {
        if (!isInGame(HypixelGames.ZOMBIES) || slot > 3 || slot < 0) {
            return false;
        }
        ItemStack itemStack = PlayerUtil.getItemStackfromSlot(slot);
        if (itemStack == null) {
            return false;   // 物品为空时则直接返回为假
        } else {
            return !String.valueOf(itemStack).contains("item.deyPowder") &&   // 物品不为染料
                    !itemStack.getDisplayName().contains("#") &&  // 物品名称不包含# (Gun #2 / Gun #3)
                    (
                     itemStack.getItem() instanceof ItemTool ||
                     itemStack.getItem() instanceof ItemSword ||
                     itemStack.getItem() instanceof ItemHoe ||
                     itemStack.getItem() == Items.flint ||              // black hole gun
                     itemStack.getItem() == Items.flint_and_steel ||    // double barrel shotgun
                     itemStack.getItem() == Items.shears               // elder gun
                    );
        }
    }

    public static boolean isHypixelZombieGun(int slot) {
        if (isHypixelZombieWeapon(slot)) {
            ItemStack itemStack = PlayerUtil.getItemStackfromSlot(slot);
            if (itemStack == null) {
                return false;
            }
            return !(itemStack.getItem() == Items.iron_sword ||
                    itemStack.getItem() == Items.diamond_axe ||
                    itemStack.getItem() == Items.golden_axe);
        } else {
            return false;
        }
    }

    public static String getHypixelZombieWeapon(int slot) {
        if (isHypixelZombieWeapon(slot)) {
            return String.valueOf(PlayerUtil.getItemStackfromSlot(slot).getDisplayName());
        }
        return "&cNone";
    }
}
