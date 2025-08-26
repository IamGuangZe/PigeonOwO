package owo.pigeon.commands.impl;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import owo.pigeon.commands.Command;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.WorldUtil;

import static owo.pigeon.features.Module.mc;

public class SetBlockCommand extends Command {
    public SetBlockCommand() {
        super("Setblock");
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 4) {
            ChatUtil.sendMessage("&cIncorrect usage.");
            return;
        }

        Block block = Block.getBlockFromName(args[3]);
        if (block == null) {
            ChatUtil.sendMessage("&cBlock Not Found");
            return;
        }

        Integer posX = WorldUtil.parseCoordinate(args[0], (int) mc.thePlayer.posX);
        Integer posY = WorldUtil.parseCoordinate(args[1], (int) mc.thePlayer.posY);
        Integer posZ = WorldUtil.parseCoordinate(args[2], (int) mc.thePlayer.posZ);

        if (posX == null || posY == null || posZ == null) {
            ChatUtil.sendMessage("&cInvalid coordinate input.");
            return;
        }

        BlockPos pos = new BlockPos(posX, posY, posZ);

        WorldUtil.setBlock(pos,block);
    }
}
