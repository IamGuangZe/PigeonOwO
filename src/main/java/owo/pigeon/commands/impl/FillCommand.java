package owo.pigeon.commands.impl;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import owo.pigeon.commands.Command;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.WorldUtil;

import static owo.pigeon.commands.CommandManager.commandPrefix;
import static owo.pigeon.features.modules.Module.mc;

public class FillCommand extends Command {
    public FillCommand() {
        super("fill");
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 7) {
            sendUsage();
            return;
        }

        Block block = Block.getBlockFromName(args[6]);
        if (block == null) {
            ChatUtil.sendMessage("&cThere is no such block with name " + args[6]);
            return;
        }

        Double startPosX = WorldUtil.parseCoordinate(args[0], mc.thePlayer.posX);
        Double startPosY = WorldUtil.parseCoordinate(args[1], mc.thePlayer.posY);
        Double startPosZ = WorldUtil.parseCoordinate(args[2], mc.thePlayer.posZ);
        Double endPosX   = WorldUtil.parseCoordinate(args[3], mc.thePlayer.posX);
        Double endPosY   = WorldUtil.parseCoordinate(args[4], mc.thePlayer.posY);
        Double endPosZ   = WorldUtil.parseCoordinate(args[5], mc.thePlayer.posZ);

        if (startPosX == null) {
            ChatUtil.sendMessage("&c" + args[0] + " is not a valid number");
            return;
        }
        if (startPosY == null) {
            ChatUtil.sendMessage("&c" + args[1] + " is not a valid number");
            return;
        }
        if (startPosZ == null) {
            ChatUtil.sendMessage("&c" + args[2] + " is not a valid number");
            return;
        }
        if (endPosX == null) {
            ChatUtil.sendMessage("&c" + args[3] + " is not a valid number");
            return;
        }
        if (endPosY == null) {
            ChatUtil.sendMessage("&c" + args[4] + " is not a valid number");
            return;
        }
        if (endPosZ == null) {
            ChatUtil.sendMessage("&c" + args[5] + " is not a valid number");
            return;
        }

        BlockPos startPos = new BlockPos((int) Math.floor(startPosX), (int) Math.floor(startPosY), (int) Math.floor(startPosZ));
        BlockPos endPos = new BlockPos((int) Math.floor(endPosX), (int) Math.floor(endPosY), (int) Math.floor(endPosZ));

        WorldUtil.fillBlock(startPos,endPos,block);
    }

    @Override
    public String getUsage() {
        return commandPrefix + "fill <x1> <y1> <z1> <x2> <y2> <z2> <tilename>";
    }
}
