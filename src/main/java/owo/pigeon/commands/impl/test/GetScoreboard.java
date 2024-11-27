package owo.pigeon.commands.impl.test;

import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import owo.pigeon.commands.Command;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.PlayerUtil;

import static owo.pigeon.modules.Module.mc;

public class GetScoreboard extends Command {

    public GetScoreboard() {
        super("GetScoreboard");
    }

    @Override
    public void execute(String[] args) {
        Scoreboard scoreboard = mc.theWorld.getScoreboard();
        ScoreObjective sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1);
        if (args.length == 0) {
            ChatUtil.sendMessage(scoreboard.toString());

            if (sidebarObjective != null) {
                String objectiveName = ChatUtil.removeColor(sidebarObjective.getDisplayName());
                ChatUtil.sendMessage(sidebarObjective.toString());
                ChatUtil.sendMessage(objectiveName);
            }
        } else {
            ChatUtil.sendMessage(PlayerUtil.getLineOfSidebar(Integer.parseInt(args[0])));
        }
    }
}
