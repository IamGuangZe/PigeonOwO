package owo.pigeon.commands.impl;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import owo.pigeon.commands.Command;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.WorldUtil;

import static owo.pigeon.features.Module.mc;

public class FillCommand extends Command {
    public FillCommand() {
        super("fill");
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 7) {
            ChatUtil.sendMessage("&cIncorrect usage.");
            return;
        }

        Block block = Block.getBlockFromName(args[6]);
        if (block == null) {
            ChatUtil.sendMessage("&cBlock Not Found");
            return;
        }

        Double startPosX = WorldUtil.parseCoordinate(args[0], mc.thePlayer.posX);
        Double startPosY = WorldUtil.parseCoordinate(args[1], mc.thePlayer.posY);
        Double startPosZ = WorldUtil.parseCoordinate(args[2], mc.thePlayer.posZ);
        Double endPosX   = WorldUtil.parseCoordinate(args[3], mc.thePlayer.posX);
        Double endPosY   = WorldUtil.parseCoordinate(args[4], mc.thePlayer.posY);
        Double endPosZ   = WorldUtil.parseCoordinate(args[5], mc.thePlayer.posZ);

        if (startPosX == null || startPosY == null || startPosZ == null ||
                endPosX == null   || endPosY == null   || endPosZ == null) {
            ChatUtil.sendMessage("&cInvalid coordinate input.");
            return;
        }

        BlockPos startPos = new BlockPos((int) Math.floor(startPosX), (int) Math.floor(startPosY), (int) Math.floor(startPosZ));
        BlockPos endPos = new BlockPos((int) Math.floor(endPosX), (int) Math.floor(endPosY), (int) Math.floor(endPosZ));

        WorldUtil.fillBlock(startPos,endPos,block);
    }
}
