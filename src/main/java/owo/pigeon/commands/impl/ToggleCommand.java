package owo.pigeon.commands.impl;

import owo.pigeon.commands.Command;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.ModuleUtil;

public class ToggleCommand extends Command {
    public ToggleCommand() {
        super("Toggle");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            ChatUtil.sendMessage("&cPlease type a module!");
            return;
        }

        String module = args[0];
        if (ModuleUtil.isModuleExist(module)) {

            String action = args.length > 1 ? args[1].toLowerCase() : "";

            switch (action) {
                case "enable":
                    ModuleUtil.moduleEnable(module);
                    break;
                case "disable":
                    ModuleUtil.moduleDisable(module);
                    break;
                default:
                    ModuleUtil.moduleToggle(module);
                    break;
            }

        } else {
            ChatUtil.sendMessage("&cModule not found!");
        }

        /*boolean found = false;
        for (Module modules : Pigeon.modulemanager.getAllModules()) {
            if (modules.Name.equalsIgnoreCase(module)) {
                modules.toggle();
                found = true;
            }
        }

        if (!found) {
            ChatUtil.sendMessage("&cModule not found!");
        }*/
    }
}
