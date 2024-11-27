package owo.pigeon.commands.impl;

import owo.pigeon.commands.Command;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.TimerExample;

import static owo.pigeon.modules.Module.mc;

public class PingCommand extends Command {
    public PingCommand() {
        super("Ping");
    }

    @Override
    public void execute(String[] args) {
        if (TimerExample.isTimerRunning) {
            ChatUtil.sendMessage("&cAlready pinging!");
        } else {
            mc.thePlayer.sendChatMessage("/pigeongetping");
            TimerExample.startTimer();
        }
    }
}
