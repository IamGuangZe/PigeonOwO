package owo.pigeon.commands;

public abstract class Command {
    private final String command;

    abstract public void execute(String[] args);

    protected Command(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
