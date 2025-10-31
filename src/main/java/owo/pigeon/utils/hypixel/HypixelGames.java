package owo.pigeon.utils.hypixel;

import com.google.common.collect.Sets;

import java.util.Set;

public enum HypixelGames {
    SKYBLOCK(Sets.newHashSet("SKYBLOCK", "SKYBLOCK CO-OP", "空岛生存", "空島生存", "SKIBLOCK")),
    ZOMBIES(Sets.newHashSet("ZOMBIES", "僵尸末日", "殭屍末日")),
    MURDER(Sets.newHashSet("MURDER MYSTERY", "密室杀手")),
    PIXELPARTY(Sets.newHashSet("PIXEL PARTY", "像素派对", "跳色舞會")),
    BEDWARS(Sets.newHashSet("BED WARS", "起床战争", "床戰"));

    private final Set<String> displayNames;

    HypixelGames(Set<String> displayNames) {
        this.displayNames = displayNames;
    }

    public Set<String> getDisplayNames() {
        return displayNames;
    }
}
