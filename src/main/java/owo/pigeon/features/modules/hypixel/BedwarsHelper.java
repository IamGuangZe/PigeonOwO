package owo.pigeon.features.modules.hypixel;

import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.settings.EnableSetting;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.FontUtil;
import owo.pigeon.utils.OtherUtil;
import owo.pigeon.utils.WorldUtil;
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
    public EnableSetting enchantmentDetector = setting("enchantment-detector", true, v -> true);

    private final String regex = "^([A-Z])\\s+([A-Za-z]+):\\s*([0-9]|[✓✗])(\\s+YOU)?$";
    private final List<BedwarsTeamInfo> teamsInfo = new ArrayList<>();

    @Override
    public void onTick() {
        if (WorldUtil.isNotNull()) {
            if (isGameStart()) {
                getTeamInfo();
            } else {
                teamsInfo.clear();
            }
        }
    }

    @Override
    public void onRender2D() {
        if (WorldUtil.isNotNull()) {
            if (hud.getValue() && isGameStart()) {
                FontUtil.drawStringWithShadow("Bed Wars", 5, 5);
                for (int i = 0; i < teamsInfo.size(); i++) {
                    BedwarsTeamInfo teamInfo = teamsInfo.get(i);
                    BedwarsTeams team = teamInfo.getTeam();
                    String status = teamInfo.getStatus();

                    String info = "&" + team.getColorChat() + "[" + team.getPrefix() + "] " + team.name() + " : " + status;
                    FontUtil.drawStringWithShadow(info,5, 5 + h * i);
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

            if (OtherUtil.isContainsRegex(regex,rawLine)) {
                BedwarsTeams team = BedwarsTeams.getTeamFromChar(rawLine.charAt(0));
                String status = OtherUtil.regexGetPart(regex,rawLine,3);
                if (team == null) continue;
                if (status == null) status = "✗";

                BedwarsTeamInfo existing = null;
                for (BedwarsTeamInfo teamInfo : teamsInfo) {
                    if (teamInfo.getTeam() == team) {
                        existing = teamInfo;
                        break;
                    }
                }

                if (existing == null) {
                    BedwarsTeamInfo teamInfo = new BedwarsTeamInfo(team);
                    teamInfo.setStatus(status);
                    teamsInfo.add(teamInfo);

                    ChatUtil.sendMessage("ADD");
                } else {
                    existing.setStatus(status);

                    ChatUtil.sendMessage("SET");
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
}

//S Gray: ?
//P Pink: ?
//W White: ?
//A Aqua: ?
//Y Yellow: ?
//G Green: ?
//B Blue: ?
//R Red: ?
//✓ ✗

