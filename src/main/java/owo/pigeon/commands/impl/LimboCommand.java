package owo.pigeon.commands.impl;

import owo.pigeon.commands.Command;
import owo.pigeon.utils.hypixel.HypixelUtil;

import static owo.pigeon.features.modules.Module.mc;

public class LimboCommand extends Command {
    public LimboCommand() {
        super("limbo");
    }

    @Override
    public void execute(String[] args) {
        if (HypixelUtil.isInHypixel()) {
            mc.thePlayer.sendChatMessage("\u00a7");
        } else {
            sendCommandError("You must be on Hypixel to use this command!");
        }
    }

    @Override
    public String getUsage() {
        return super.getUsage();
    }
}
