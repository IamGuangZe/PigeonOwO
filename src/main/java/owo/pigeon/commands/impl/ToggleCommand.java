package owo.pigeon.commands.impl;

import owo.pigeon.Pigeon;
import owo.pigeon.commands.Command;
import owo.pigeon.modules.Module;
import owo.pigeon.utils.ChatUtil;

public class ToggleCommand extends Command {
    public ToggleCommand() {
        super("Toggle");
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 0) {
            ChatUtil.sendMessage("&cPlease type a module!");
            return;
        }
        String module = args[0];
        boolean found = false;
        for (Module modules : Pigeon.modulemanager.getAllModules()) {
            if (modules.Name.equalsIgnoreCase(module)) {
                modules.toggle();
                found = true;
            }
        }

        if (!found) {
            ChatUtil.sendMessage("&cModule not found!");
        }
    }
}
