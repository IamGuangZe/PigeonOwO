package owo.pigeon.commands.impl;

import owo.pigeon.commands.Command;
import owo.pigeon.utils.ChatUtil;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("Help");
    }

    @Override
    public void execute(String[] args) {
        ChatUtil.sendMessage("Help Message.");
    }
}