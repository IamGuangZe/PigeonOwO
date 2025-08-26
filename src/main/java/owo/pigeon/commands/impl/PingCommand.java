package owo.pigeon.commands.impl;

import owo.pigeon.commands.Command;
import owo.pigeon.features.commands.Ping;

public class PingCommand extends Command {
    public PingCommand() {
        super("Ping");
    }

    @Override
    public void execute(String[] args) {
        Ping.setInvokedCommand(true);
        Ping.sengPing();
    }
}
