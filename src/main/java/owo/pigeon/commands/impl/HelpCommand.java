package owo.pigeon.commands.impl;

import owo.pigeon.commands.Command;
import owo.pigeon.commands.CommandManager;
import owo.pigeon.utils.ChatUtil;

import static owo.pigeon.commands.CommandManager.commandPrefix;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help");
    }
    
    @Override
    public void execute(String[] args) {
        int maxpage = (int) Math.ceil((double)CommandManager.commands.size() / 7);
        int page = 1;
        try {
            if (args.length > 0) {
                int input = Integer.parseInt(args[0]);
                if (input > 0 && input <= maxpage) {
                    page = input;
                }
            }
        } catch (NumberFormatException ignored) {

        }

        int start = (page - 1) * 7;
        int end = Math.min(start + 7, CommandManager.commands.size());

        ChatUtil.sendMessage("&8Command List (Page " + page + "/" + maxpage + ")");

        for (int i = start; i < end; i++) {
            if (i > CommandManager.commands.size() - 1) break;
            ChatUtil.sendMessage("&7" + commandPrefix + " " + CommandManager.commands.get(i).getCommand().toLowerCase());
        }

        ChatUtil.sendMessage("&8Use \"" + commandPrefix + " help page\" to view other commands.");
    }

    @Override
    public String getUsage() {
        return commandPrefix + "help [page]";
    }
}