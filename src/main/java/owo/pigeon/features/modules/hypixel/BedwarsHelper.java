package owo.pigeon.features.modules.hypixel;

import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.BlockPos;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.settings.EnableSetting;
import owo.pigeon.utils.*;
import owo.pigeon.utils.hypixel.HypixelGames;
import owo.pigeon.utils.hypixel.HypixelUtil;
import owo.pigeon.utils.hypixel.bedwars.BedwarsTeamInfo;
import owo.pigeon.utils.hypixel.bedwars.BedwarsTeams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static owo.pigeon.utils.WorldUtil.isNotNull;

public class BedwarsHelper extends Module {
    public BedwarsHelper() {
        super("BedwarsHelper", Category.HYPIXEL, -1);
    }

    public EnableSetting hud = setting("hud", true, v -> true);
    public EnableSetting enchantDetector = setting("enchant-detector", true, v -> true);

    private final String regex = "^([A-Z])\\s+([A-Za-z]+):\\s*([0-9]|[✓✗])(\\s+YOU)?$";
    private final List<BedwarsTeamInfo> teamsInfo = new ArrayList<>();

    @Override
    public void onTick() {
        if (WorldUtil.isNotNull()) {
            if (isGameStart()) {
                getTeamInfo();

                if (enchantDetector.getValue()) {
                    for (EntityPlayer player : mc.theWorld.playerEntities) {
                        Character colorChar = PlayerUtil.getTeamScoreBoardColorPrefix(player);
                        if (colorChar == null) continue;

                        BedwarsTeams team = BedwarsTeams.getTeamFromChar(colorChar);
                        if (team == null) continue;

                        BedwarsTeamInfo info = getTeamInfoByTeam(team);
                        if (info == null) continue;

                        ItemStack armorItemStack = player.getCurrentArmor(2);
                        int protection = 0;
                        if (armorItemStack != null) {
                            protection = ItemUtil.getProtectionLevel(armorItemStack);
                        }
                        if (info.getProtectionLevel() < protection) {
                            ChatUtil.sendCustomPrefixMessage(this.name, "Team &" + team.getColorChar() + team.name() + " &r purchased &aProtection " + getEnchantLevelColor(protection) + protection);
                            info.setProtectionLevel(protection);
                        }

                        ItemStack heldItemStack = player.getHeldItem();
                        int sharpness = 0;
                        if (heldItemStack != null && heldItemStack.getItem() instanceof ItemSword) {
                            sharpness = ItemUtil.getSharpnessLevel(heldItemStack);
                        }
                        if (info.getSharpnessLevel() < sharpness) {
                            ChatUtil.sendCustomPrefixMessage(this.name, "Team &" + team.getColorChar() + team.name() + " &r purchased &cSharpness " + getEnchantLevelColor(sharpness) + sharpness);
                            info.setSharpnessLevel(sharpness);
                        }
                    }
                }

            } else {
                teamsInfo.clear();
            }
        }
    }

    @Override
    public void onRender2D() {
        if (WorldUtil.isNotNull()) {
            if (hud.getValue() && isGameStart()) {
                FontUtil.drawStringWithShadow("Bedwars", 5, 5);

                if (teamsInfo.isEmpty()) {
                    FontUtil.drawStringWithShadow("&cDidn't find team info in sidebar!", 5, 5 + h);
                }

                for (int i = 0; i < teamsInfo.size(); i++) {
                    BedwarsTeamInfo teamInfo = teamsInfo.get(i);
                    BedwarsTeams team = teamInfo.getTeam();
                    String status = teamInfo.getStatus();

                    int protection = teamInfo.getProtectionLevel();
                    int sharpness = teamInfo.getSharpnessLevel();

                    String info = "&" + team.getColorChar() + "[" + team.getPrefix() + "] " + team.name() + "&r: " +
                            getStatusColor(status) + status;
                    String enchants = enchantDetector.getValue() ?
                            "&7 | &aP&r: " + getEnchantLevelColor(protection) + protection +
                                    "&7 | &cS&r: " + getEnchantLevelColor(sharpness) + sharpness : "";
                    FontUtil.drawStringWithShadow(info + enchants, 5, 5 + h * i);
                }
            }
        }
    }

    @Override
    public void onWorldLoad() {
        teamsInfo.clear();
    }

    @Override
    public void onEnable() {
        teamsInfo.clear();
    }

    private void getTeamInfo() {
        List<String> sidebarLines = WorldUtil.getSidebarLines();
        Collections.reverse(sidebarLines);

        for (String line : sidebarLines) {
            String rawLine = OtherUtil.removeColor(line);

            if (OtherUtil.isContainsRegex(regex, rawLine)) {
                BedwarsTeams team = BedwarsTeams.getTeamFromChar(rawLine.charAt(0));
                String status = OtherUtil.regexGetPart(regex, rawLine, 3);
                if (team == null) continue;
                if (status == null) status = "✗";

                BedwarsTeamInfo info = getTeamInfoByTeam(team);

                if (info == null) {
                    BedwarsTeamInfo teamInfo = new BedwarsTeamInfo(team);
                    teamInfo.setStatus(status);
                    teamsInfo.add(teamInfo);
                } else {
                    info.setStatus(status);
                }
            }
        }
    }

    private boolean isGameStart() {
        if (!isNotNull()) return false;
        if (!HypixelUtil.isInGame(HypixelGames.BEDWARS)) return false;

        IBlockState lobbyBlockState = mc.theWorld.getBlockState(new BlockPos(-55, 77, 0));
        if (lobbyBlockState == null || lobbyBlockState.getBlock() instanceof BlockPortal) return false;

        for (int y = 255; y >= 64; y--) {
            IBlockState readyBlockState = mc.theWorld.getBlockState(new BlockPos(0, y, 0));
            if (readyBlockState == null || readyBlockState.getBlock() instanceof BlockAir) continue;
            if (readyBlockState.getBlock() instanceof BlockStainedGlass) return false;
            break;
        }

        return true;
    }

    private BedwarsTeamInfo getTeamInfoByTeam(BedwarsTeams team) {
        for (BedwarsTeamInfo info : teamsInfo) {
            if (info.getTeam() == team) return info;
        }
        return null;
    }

    private String getEnchantLevelColor(int level) {
        switch (level) {
            case 0:
                return "&7";
            case 1:
                return "&2";
            case 2:
                return "&e";
            case 3:
                return "&6";
            default:
                return "&c&l";
        }
    }

    private String getStatusColor(String status) {
        switch (status) {
            case "✗":
                return "&c&l";
            case "✓":
                return "&a&l";
            default:
                return "&a";
        }
    }
}
