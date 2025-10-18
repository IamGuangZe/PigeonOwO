package owo.pigeon.commands;

import owo.pigeon.utils.ChatUtil;

import static owo.pigeon.commands.CommandManager.commandPrefix;

public abstract class Command {
    private final String command;

    abstract public void execute(String[] args);

    protected Command(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public String getUsage() {
        return commandPrefix + command;
    }

    public void sendCommandError(String message) {
        ChatUtil.sendMessage("&c" + message);
    }

    public void sendUsage() {
        ChatUtil.sendMultiLineMessage(getUsage());
    }
}
