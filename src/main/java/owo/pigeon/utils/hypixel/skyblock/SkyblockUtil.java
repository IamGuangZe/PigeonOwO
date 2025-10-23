package owo.pigeon.utils.hypixel.skyblock;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import owo.pigeon.utils.OtherUtil;
import owo.pigeon.utils.WorldUtil;
import owo.pigeon.utils.hypixel.HypixelGames;
import owo.pigeon.utils.hypixel.HypixelUtil;

import java.util.Collection;

import static owo.pigeon.features.modules.Module.mc;

public class SkyblockUtil {

    public static Island getIsland() {
        NetHandlerPlayClient netHandlerPlayClient = mc.thePlayer != null ? mc.thePlayer.sendQueue : null;
        Collection<NetworkPlayerInfo> networkPlayerInfo = netHandlerPlayClient.getPlayerInfoMap();

        if (mc.isSingleplayer()) {
            return Island.SinglePlayer;
        }

        // Check if the player is in Skyblock area
        if (!HypixelUtil.isInGame(HypixelGames.SKYBLOCK)) {
            return Island.Unknown;
        }

        if (networkPlayerInfo == null) {
            return Island.Unknown;
        }

        String area = null;
        for (NetworkPlayerInfo info : networkPlayerInfo) {
            if (info != null && info.getDisplayName() != null) {
                String displayName = info.getDisplayName().getUnformattedText();
                if (displayName.startsWith("Area: ") || displayName.startsWith("Dungeon: ")) {
                    area = info.getDisplayName().getFormattedText();
                    break;
                }
            }
        }
        if (area != null) {
            for (Island island : Island.values()) {
                if (area.toLowerCase().contains(island.getDisplayName().toLowerCase())) {
                    return island;
                }
            }
        }
        return Island.Unknown;
    }

    public static boolean isIsland(Island island) {
        return getIsland() == island;
    }

    // Dungeon
    public static Floor getFloor() {
        if (isIsland(Island.SinglePlayer)) {
            return Floor.E;
        }
        for (String s : WorldUtil.getSidebarLines()) {
            String f = OtherUtil.removeColor(s);
            // ⏣ The Catac.*ombs \\((.*)\\)
            String floorName = OtherUtil.regexGetPart(" ⏣ The Catacombs \\((.*)\\)", f, 1);
            if (floorName != null) {
                return Floor.valueOf(floorName);
            }
        }
        return null;
    }

    public static int getFloorNumber(Floor floor) {
        switch (floor) {
            case E:
                return 0;
            case F1:
            case M1:
                return 1;
            case F2:
            case M2:
                return 2;
            case F3:
            case M3:
                return 3;
            case F4:
            case M4:
                return 4;
            case F5:
            case M5:
                return 5;
            case F6:
            case M6:
                return 6;
            case F7:
            case M7:
                return 7;
            default:
                return -1;
        }
    }

    public static int getF7Stage() {
        double posY = mc.thePlayer.posY;
        if (posY < 62) {
            return 5;
        } else if (posY < 107) {
            return 4;
        } else if (posY < 166) {
            return 3;
        } else if (posY < 219) {
            return 2;
        } else {
            return 1;
        }
    }

    public static boolean isFloor(Floor floor) {
        if (isIsland(Island.Dungeon)) {
            return getFloor() == floor;
        }
        return false;
    }

    public static boolean isInBoss(Floor floor) {

        double posX = mc.thePlayer.posX;
        double posZ = mc.thePlayer.posZ;
        int floorNumber = getFloorNumber(floor);

        if (floorNumber == 1) {
            return posX > -71 && posZ > -39;
        } else if (floorNumber <= 4) {
            return posX > -39 && posZ > -39;
        } else if (floorNumber <= 6) {
            return posX > -39 && posZ > -7;
        } else if (floorNumber == 7) {
            return posX > -7 && posZ > -7;
        }

        return false;
    }

    public static boolean isInBoss(int floor) {

        double posX = mc.thePlayer.posX;
        double posZ = mc.thePlayer.posZ;

        if (floor == 1) {
            return posX > -71 && posZ > -39;
        } else if (floor <= 4) {
            return posX > -39 && posZ > -39;
        } else if (floor <= 6) {
            return posX > -39 && posZ > -7;
        } else if (floor == 7) {
            return posX > -7 && posZ > -7;
        }

        return false;
    }

    // Crimson
    public static boolean isInDojo() {
        if (isIsland(Island.CrimsonIsle)) {
            double posX = mc.thePlayer.posX;
            double posY = mc.thePlayer.posY;
            double posZ = mc.thePlayer.posZ;
            return posY < 106 && posY > 96 && posX < -189 && posX > -225 && posZ < -580 && posZ > -616;
        }
        return false;
    }

    public static Dojo getDojo() {
        for (String s : WorldUtil.getSidebarLines()) {
            String f = OtherUtil.removeColor(s);
            //Challenge: .*
            String dojoName = OtherUtil.regexGetPart("Challenge: (.*)", f, 1);
            if (dojoName != null) {
                return Dojo.valueOf(dojoName);
            }
        }
        return null;
    }

    public static boolean isDojo(Dojo dojo) {
        return getDojo() == dojo;
    }
}
