package owo.pigeon.commands.impl;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import owo.pigeon.commands.Command;
import owo.pigeon.utils.CommandUtil;
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
            CommandUtil.sendCommandError(CommandUtil.errorReason.UnknownOrIncompleteCommand,
                    this.getCommand(),
                    args,
                    args.length
            );
            return;
        }

        Block block = Block.getBlockFromName(args[6]);
        if (block == null) {
            CommandUtil.sendCommandError(CommandUtil.errorReason.UnknownBlock,
                    this.getCommand(),
                    args,
                    6
            );
            return;
        }

        Double startPosX = WorldUtil.parseCoordinate(args[0], mc.thePlayer.posX);
        Double startPosY = WorldUtil.parseCoordinate(args[1], mc.thePlayer.posY);
        Double startPosZ = WorldUtil.parseCoordinate(args[2], mc.thePlayer.posZ);
        Double endPosX   = WorldUtil.parseCoordinate(args[3], mc.thePlayer.posX);
        Double endPosY   = WorldUtil.parseCoordinate(args[4], mc.thePlayer.posY);
        Double endPosZ   = WorldUtil.parseCoordinate(args[5], mc.thePlayer.posZ);

        if (startPosX == null) {
            CommandUtil.sendCommandError(CommandUtil.errorReason.ExpectedInteger,
                    this.getCommand(),
                    args,
                    0
            );
            return;
        }
        if (startPosY == null) {
            CommandUtil.sendCommandError(CommandUtil.errorReason.ExpectedInteger,
                    this.getCommand(),
                    args,
                    1
            );
            return;
        }
        if (startPosZ == null) {
            CommandUtil.sendCommandError(CommandUtil.errorReason.ExpectedInteger,
                    this.getCommand(),
                    args,
                    2
            );
            return;
        }
        if (endPosX == null) {
            CommandUtil.sendCommandError(CommandUtil.errorReason.ExpectedInteger,
                    this.getCommand(),
                    args,
                    3
            );
            return;
        }
        if (endPosY == null) {
            CommandUtil.sendCommandError(CommandUtil.errorReason.ExpectedInteger,
                    this.getCommand(),
                    args,
                    4
            );
            return;
        }
        if (endPosZ == null) {
            CommandUtil.sendCommandError(CommandUtil.errorReason.ExpectedInteger,
                    this.getCommand(),
                    args,
                    5
            );
            return;
        }

        BlockPos startPos = new BlockPos((int) Math.floor(startPosX), (int) Math.floor(startPosY), (int) Math.floor(startPosZ));
        BlockPos endPos = new BlockPos((int) Math.floor(endPosX), (int) Math.floor(endPosY), (int) Math.floor(endPosZ));

        WorldUtil.fillBlock(startPos,endPos,block);
    }

    @Override
    public String getUsage() {
        return commandPrefix + "fill <from> <to> <block>";
    }
}
