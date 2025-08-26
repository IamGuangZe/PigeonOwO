package owo.pigeon.commands.impl;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import owo.pigeon.commands.Command;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.WorldUtil;

import static owo.pigeon.features.Module.mc;

public class FillCommand extends Command {
    public FillCommand() {
        super("Fill");
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

        Integer startPosX = WorldUtil.parseCoordinate(args[0], (int) mc.thePlayer.posX);
        Integer startPosY = WorldUtil.parseCoordinate(args[1], (int) mc.thePlayer.posY);
        Integer startPosZ = WorldUtil.parseCoordinate(args[2], (int) mc.thePlayer.posZ);
        Integer endPosX   = WorldUtil.parseCoordinate(args[3], (int) mc.thePlayer.posX);
        Integer endPosY   = WorldUtil.parseCoordinate(args[4], (int) mc.thePlayer.posY);
        Integer endPosZ   = WorldUtil.parseCoordinate(args[5], (int) mc.thePlayer.posZ);

        if (startPosX == null || startPosY == null || startPosZ == null ||
                endPosX == null   || endPosY == null   || endPosZ == null) {
            ChatUtil.sendMessage("&cInvalid coordinate input.");
            return;
        }

        BlockPos startPos = new BlockPos(startPosX,startPosY,startPosZ);
        BlockPos endPos = new BlockPos(endPosX,endPosY,endPosZ);

        WorldUtil.fillBlock(startPos,endPos,block);
    }
}
