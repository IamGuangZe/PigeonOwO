package owo.pigeon.commands.impl;

import owo.pigeon.commands.Command;
import owo.pigeon.commands.CommandManager;

import static owo.pigeon.commands.CommandManager.commandPrefix;
import static owo.pigeon.features.modules.Module.mc;

public class SayCommand extends Command {
    public SayCommand() {
        super("say");
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 0) {
            sendUsage();
            return;
        }
        String Text = String.join(" ", args);
        CommandManager.isSay = true;
        mc.thePlayer.sendChatMessage(Text);
    }

    @Override
    public String getUsage() {
        return commandPrefix + "say <message ...>";
    }
}
