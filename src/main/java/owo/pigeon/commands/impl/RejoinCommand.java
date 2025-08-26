package owo.pigeon.commands.impl;

import owo.pigeon.commands.Command;
import owo.pigeon.features.commands.Rejoin;

public class RejoinCommand extends Command {
    public RejoinCommand() {
        super("rej");
    }

    @Override
    public void execute(String[] args) {
        Rejoin.rejoin();
    }
}
