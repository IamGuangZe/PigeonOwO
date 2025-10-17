package owo.pigeon.commands.impl;

import owo.pigeon.commands.Command;
import owo.pigeon.configs.PigeonowoConfig;
import owo.pigeon.utils.ChatUtil;

import static owo.pigeon.commands.CommandManager.commandPrefix;

public class PrefixCommand extends Command {
    public PrefixCommand() {
        super("prefix");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            sendUsage();
            return;
        }

        commandPrefix = args[0].charAt(0);
        PigeonowoConfig.setPrefix(commandPrefix);
        ChatUtil.sendMessage("&aThe prefix has been set to \" " + commandPrefix + " \"!");
    }

    @Override
    public String getUsage() {
        return commandPrefix + "prefix <char>";
    }
}
