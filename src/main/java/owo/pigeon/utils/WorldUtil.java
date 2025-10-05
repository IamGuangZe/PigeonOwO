package owo.pigeon.utils;

import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static owo.pigeon.features.Module.mc;

public class WorldUtil {
    // 检测玩家和世界是否为空
    public static boolean isNotNull() {
        return (mc.thePlayer != null && mc.theWorld != null);
    }

    // 创建方块
    public static void setBlock(int posX, int posY, int posZ, Block block) {
        mc.theWorld.setBlockState(new BlockPos(posX,posY,posZ), block.getDefaultState());
    }
    public static void setBlock(BlockPos pos, Block block) {
        mc.theWorld.setBlockState(pos, block.getDefaultState());
    }
    public static void setBlock(BlockPos pos, IBlockState iBlockState) {
        mc.theWorld.setBlockState(pos, iBlockState);
    }

    public static void fillBlock(int startPosX,int startPosY,int startPosZ,int endPosX,int endPosY,int endPosZ,Block block) {
        if (startPosX > endPosX){
            int temp = startPosX;
            startPosX  = endPosX;
            endPosX = temp;
        }
        if (startPosY > endPosY){
            int temp = startPosY;
            startPosY = endPosY;
            endPosY = temp;
        }
        if (startPosZ > endPosZ){
            int temp = startPosZ;
            startPosZ = endPosZ;
            endPosZ = temp;
        }


        for (int x = startPosX; x <= endPosX; x++) {
            for (int y = startPosY; y <= endPosY; y++) {
                for (int z = startPosZ; z <= endPosZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    mc.theWorld.setBlockState(pos, block.getDefaultState());
                }
            }
        }
    }
    public static void fillBlock(BlockPos startPos, BlockPos endPos, Block block) {
        if (startPos.getX() > endPos.getX()){
            int temp = startPos.getX();
            startPos = new BlockPos(endPos.getX(),startPos.getY(),startPos.getZ());
            endPos = new BlockPos(temp,endPos.getY(),endPos.getZ());
        }
        if (startPos.getY() > endPos.getY()){
            int temp = startPos.getY();
            startPos = new BlockPos(startPos.getX(),endPos.getY(),startPos.getZ());
            endPos = new BlockPos(endPos.getX(),temp,endPos.getZ());
        }
        if (startPos.getZ() > endPos.getZ()){
            int temp = startPos.getZ();
            startPos = new BlockPos(startPos.getX(),startPos.getY(),endPos.getZ());
            endPos = new BlockPos(endPos.getX(),endPos.getY(),temp);
        }


        for (int x = startPos.getX(); x <= endPos.getX(); x++) {
            for (int y = startPos.getY(); y <= endPos.getY(); y++) {
                for (int z = startPos.getZ(); z <= endPos.getZ(); z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    mc.theWorld.setBlockState(pos, block.getDefaultState());
                }
            }
        }
    }
    public static void fillBlock(BlockPos startPos, BlockPos endPos, IBlockState iBlockState) {
        if (startPos.getX() > endPos.getX()){
            int temp = startPos.getX();
            startPos = new BlockPos(endPos.getX(),startPos.getY(),startPos.getZ());
            endPos = new BlockPos(temp,endPos.getY(),endPos.getZ());
        }
        if (startPos.getY() > endPos.getY()){
            int temp = startPos.getY();
            startPos = new BlockPos(startPos.getX(),endPos.getY(),startPos.getZ());
            endPos = new BlockPos(endPos.getX(),temp,endPos.getZ());
        }
        if (startPos.getZ() > endPos.getZ()){
            int temp = startPos.getZ();
            startPos = new BlockPos(startPos.getX(),startPos.getY(),endPos.getZ());
            endPos = new BlockPos(endPos.getX(),endPos.getY(),temp);
        }


        for (int x = startPos.getX(); x <= endPos.getX(); x++) {
            for (int y = startPos.getY(); y <= endPos.getY(); y++) {
                for (int z = startPos.getZ(); z <= endPos.getZ(); z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    mc.theWorld.setBlockState(pos, iBlockState);
                }
            }
        }
    }

    // 获取方块颜色
    public static String getBlockColor(IBlockState state) {
        Block block = state.getBlock();
        if (block instanceof BlockColored || block instanceof BlockCarpet) {
            EnumDyeColor color = (EnumDyeColor) state.getValue(BlockColored.COLOR);
            return color.getName();
        } else if (block instanceof BlockStainedGlass || block instanceof BlockStainedGlassPane) {
            EnumDyeColor color = (EnumDyeColor) state.getValue(BlockStainedGlass.COLOR);
            return color.getName();
        }
        return null;
    }
    public static Integer getBlockColorRGB(IBlockState state) {
        Block block = state.getBlock();
        EnumDyeColor color = null;
        if (block instanceof BlockColored || block instanceof BlockCarpet) {
            color = (EnumDyeColor) state.getValue(BlockColored.COLOR);
        } else if (block instanceof BlockStainedGlass || block instanceof BlockStainedGlassPane) {
            color = (EnumDyeColor) state.getValue(BlockStainedGlass.COLOR);
        }
        if (color != null) {
            return ItemDye.dyeColors[color.getMetadata()];
        }
        return null;
    }

    // 获取计分板的标题
    public static String getScoreboardDisplayName() {
        Scoreboard scoreboard = mc.theWorld.getScoreboard();
        ScoreObjective sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1);
        if (sidebarObjective != null) {
            return sidebarObjective.getDisplayName();
        }
        return null;
    }
    public static String getScoreboardDisplayNameWithoutColor() {
        if (getScoreboardDisplayName() == null){
            return null;
        }
        return OtherUtil.removeColor(getScoreboardDisplayName());
    }

    // 获取计分板对应行数的内容(服务器不可用)
    /*public static String getLineOfSidebar(int line) {
        Scoreboard scoreboard = mc.theWorld.getScoreboard();

        ScoreObjective sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1);  // 1 是侧边栏显示的位置

        if (sidebarObjective == null) {
            return null;  // 如果没有设置目标，返回空
        }

        List<Score> scores = (List<Score>) scoreboard.getSortedScores(sidebarObjective);
        ChatUtil.sendMessage(scores.toString());

        if (scores.size() >= line) {
            // 获取第三行的分数内容
            Score LineScore = scores.get(line - 1);  // 索引从 0 开始，第line行索引为 line - 1
            return LineScore.getPlayerName();  // 获取分数对应的玩家名称或显示的文本
        } else {
            return null;  // 如果少于line行，返回 null
        }
    }*/

    // 获取计分板对应行数的内容(fixed)
    public static String getSidebarLineBottonUp(int line) {
        List<String> lines = getSidebarLines();
        if (line > 0 && line <= lines.size()) {
            return lines.get(line - 1); // 第 line 行 (索引从 0 开始)
        } else {
            return null; // 如果指定行不存在，返回 null
        }
    }
    public static String getSidebarLineTopDown(int line) {
        List<String> lines = getSidebarLines();
        if (line > 0 && line <= lines.size()) {
            return lines.get(lines.size() - line); // 倒序取
        } else {
            return null;
        }
    }
    public static List<String> getSidebarLines() {
        Scoreboard scoreboard = mc.theWorld.getScoreboard();
        if (scoreboard == null) {
            return new ArrayList<>();
        }

        // 获取显示槽 1 中的目标
        String objective = scoreboard.getObjectiveInDisplaySlot(1) == null ? null : scoreboard.getObjectiveInDisplaySlot(1).getName();
        if (objective == null) {
            return new ArrayList<>();
        }

        List<String> sidebarLines = new ArrayList<>();

        // 获取并排序得分
        Collection<Score> scores = scoreboard.getSortedScores(scoreboard.getObjective(objective));
        for (Score score : scores) {
            if (score != null && score.getPlayerName() != null && !score.getPlayerName().startsWith("#")) {
                // 限制最多 15 条得分信息
                if (sidebarLines.size() < 15) {
                    ScorePlayerTeam team = scoreboard.getPlayersTeam(score.getPlayerName());
                    String formattedName = ScorePlayerTeam.formatPlayerName(team, score.getPlayerName());
                    sidebarLines.add(OtherUtil.removeEmoji(formattedName));
                }
            }
        }
        return sidebarLines;
    }

    // 处理坐标
    public static Integer parseCoordinate(String arg, int base) {
        try {
            if (arg.equals("~")) {
                return base;
            } else if (arg.startsWith("~")) {
                String offset = arg.substring(1);
                if (offset.isEmpty()) {
                    return base;
                } else {
                    return base + Integer.parseInt(offset);
                }
            } else {
                return Integer.parseInt(arg);
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
