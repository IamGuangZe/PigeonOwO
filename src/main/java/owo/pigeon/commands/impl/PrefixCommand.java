package owo.pigeon.commands.impl;

import owo.pigeon.commands.Command;
import owo.pigeon.commands.CommandManager;
import owo.pigeon.configs.PigeonowoConfig;
import owo.pigeon.utils.ChatUtil;

public class PrefixCommand extends Command {
    public PrefixCommand() {
        super("prefix");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            ChatUtil.sendMessage("&cPlease enter a character!");
            return;
        }

        CommandManager.chatPrefix = args[0].charAt(0);
        PigeonowoConfig.setPrefix(CommandManager.chatPrefix);
        ChatUtil.sendMessage("&aThe prefix has been set to \" " + CommandManager.chatPrefix + " \"!");
    }
}
