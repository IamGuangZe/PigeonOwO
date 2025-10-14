package owo.pigeon.commands.impl;

import owo.pigeon.commands.Command;
import owo.pigeon.commands.CommandManager;
import owo.pigeon.utils.ChatUtil;

import java.util.List;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("Help");
    }

    @Override
    public void execute(String[] args) {
        List<Command> commandList = CommandManager.getAllCommands();
        int maxpage = (int) Math.ceil((double)commandList.size() / 7);
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
        int end = Math.min(start + 7, commandList.size());

        ChatUtil.sendMessage("&8Command List (Page " + page + "/" + maxpage + ")");

        for (int i = start; i < end; i++) {
            if (i > commandList.size() - 1) break;
            ChatUtil.sendMessage("&7" + CommandManager.chatPrefix + " " + commandList.get(i).getCommand().toLowerCase());
        }

        ChatUtil.sendMessage("&8Use \"" + CommandManager.chatPrefix + " help page\" to view other commands.");
    }
}