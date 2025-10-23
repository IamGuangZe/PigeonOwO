package owo.pigeon.commands.impl;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import owo.pigeon.commands.Command;
import owo.pigeon.utils.CommandUtil;
import owo.pigeon.utils.WorldUtil;

import static owo.pigeon.commands.CommandManager.commandPrefix;
import static owo.pigeon.features.modules.Module.mc;

public class SetBlockCommand extends Command {
    public SetBlockCommand() {
        super("setblock");
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 4) {
            CommandUtil.sendCommandError(CommandUtil.errorReason.ExpectedInteger,
                    this.getCommand(),
                    args,
                    args.length
            );
            return;
        }

        Block block = Block.getBlockFromName(args[3]);
        if (block == null) {
            CommandUtil.sendCommandError(CommandUtil.errorReason.UnknownBlock,
                    this.getCommand(),
                    args,
                    3
            );
            return;
        }

        Double posX = WorldUtil.parseCoordinate(args[0], mc.thePlayer.posX);
        Double posY = WorldUtil.parseCoordinate(args[1], mc.thePlayer.posY);
        Double posZ = WorldUtil.parseCoordinate(args[2], mc.thePlayer.posZ);

        if (posX == null) {
            CommandUtil.sendCommandError(CommandUtil.errorReason.ExpectedInteger,
                    this.getCommand(),
                    args,
                    0
            );
            return;
        }
        if (posY == null) {
            CommandUtil.sendCommandError(CommandUtil.errorReason.ExpectedInteger,
                    this.getCommand(),
                    args,
                    1
            );
            return;
        }
        if (posZ == null) {
            CommandUtil.sendCommandError(CommandUtil.errorReason.ExpectedInteger,
                    this.getCommand(),
                    args,
                    2
            );
            return;
        }

        BlockPos pos = new BlockPos((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posZ));

        WorldUtil.setBlock(pos, block);
    }

    @Override
    public String getUsage() {
        return commandPrefix + "setblock <pos> <block>";
    }
}
