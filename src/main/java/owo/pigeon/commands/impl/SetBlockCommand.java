package owo.pigeon.commands.impl;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import owo.pigeon.commands.Command;
import owo.pigeon.utils.ChatUtil;
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
            sendUsage();
            return;
        }

        Block block = Block.getBlockFromName(args[3]);
        if (block == null) {
            ChatUtil.sendMessage("&cThere is no such block with name " + args[3]);
            return;
        }

        Double posX = WorldUtil.parseCoordinate(args[0], mc.thePlayer.posX);
        Double posY = WorldUtil.parseCoordinate(args[1], mc.thePlayer.posY);
        Double posZ = WorldUtil.parseCoordinate(args[2], mc.thePlayer.posZ);

        if (posX == null) {
            ChatUtil.sendMessage("&c" + args[0] + " is not a valid number");
            return;
        }
        if (posY == null) {
            ChatUtil.sendMessage("&c" + args[1] + " is not a valid number");
            return;
        }
        if (posZ == null) {
            ChatUtil.sendMessage("&c" + args[2] + " is not a valid number");
            return;
        }

        BlockPos pos = new BlockPos((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posZ));

        WorldUtil.setBlock(pos,block);
    }

    @Override
    public String getUsage() {
        return commandPrefix + "setblock <x> <y> <z> <tilename>";
    }
}
