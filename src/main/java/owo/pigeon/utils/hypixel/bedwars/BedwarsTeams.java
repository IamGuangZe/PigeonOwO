package owo.pigeon.utils.hypixel.bedwars;

public enum BedwarsTeams {
    RED("R","c",16711680),
    BLUE("B","9",255),
    GREEN("G","a",32768),
    YELLOW("Y","e",16776960),
    AQUA("A","b",65532),
    WHITE("W","f",16777215),
    PINK("P","d",8388736),
    GRAY("S","8",8421504);

    private final String prefix;
    private final String colorChat;
    private final int armorColor;

    BedwarsTeams(String perfix, String colorChar, int armorColor) {
        this.prefix = perfix;
        this.colorChat = colorChar;
        this.armorColor = armorColor;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getColorChat() {
        return colorChat;
    }

    public int getArmorColor() {
        return armorColor;
    }

    public static BedwarsTeams getTeamFromColor(int color) {
        for (BedwarsTeams team : values()) {
            if (team.armorColor == color) return team;
        }
        return null;
    }

    public static BedwarsTeams getTeamFromChar(char colorChar) {
        return getTeamFromString(String.valueOf(colorChar));
    }

    public static BedwarsTeams getTeamFromString(String colorChar) {
        for (BedwarsTeams team : values()) {
            if (team.prefix.equalsIgnoreCase(colorChar) || team.colorChat.equalsIgnoreCase(colorChar)) {
                return team;
            }
        }
        return null;
    }
}
