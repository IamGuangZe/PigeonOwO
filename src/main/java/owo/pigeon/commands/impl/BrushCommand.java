package owo.pigeon.commands.impl;

import net.minecraft.block.Block;
import owo.pigeon.commands.Command;
import owo.pigeon.features.modules.hypixel.Skyblock.Dungeon.DungeonBrush;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.ModuleUtil;

public class BrushCommand extends Command {
    public BrushCommand() {
        super("brush");
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 0) {
            ModuleUtil.moduleToggle(DungeonBrush.class);
            return;
        }

        if (args[0].equalsIgnoreCase("help")) {
            DungeonBrush.usageMessage();
        } else if (args[0].equalsIgnoreCase("setblock")) {

            if (args.length < 2) {
                ChatUtil.sendMessage("&cPlease type Block!");

            } else {
                Block temp = Block.getBlockFromName(args[1]);

                if (temp == null) {
                    ChatUtil.sendMessage("&cBlock Not Found!");
                    return;
                }

                ((DungeonBrush)ModuleUtil.getModule(DungeonBrush.class)).block.setValue(temp);
                ChatUtil.sendMessage(String.valueOf(temp));
            }

        } else {
            ChatUtil.sendMessage("&cIncorrect usage.");
        }
    }
}
