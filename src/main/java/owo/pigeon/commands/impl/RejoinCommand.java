package owo.pigeon.commands.impl;

import owo.pigeon.commands.Command;
import owo.pigeon.features.commands.Rejoin;
import owo.pigeon.utils.hypixel.HypixelUtil;

public class RejoinCommand extends Command {
    public RejoinCommand() {
        super("rejoin");
    }

    @Override
    public void execute(String[] args) {
        if (HypixelUtil.isInHypixel()) {
            Rejoin.rejoin();
        } else {
            sendCommandError("You must be on Hypixel to use this command!");
        }
    }

    @Override
    public String getUsage() {
        return super.getUsage();
    }
}
