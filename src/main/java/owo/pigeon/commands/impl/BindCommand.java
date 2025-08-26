package owo.pigeon.commands.impl;

import org.lwjgl.input.Keyboard;
import owo.pigeon.commands.Command;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.ModuleUtil;

public class BindCommand extends Command {
    public BindCommand() {
        super("Bind");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            ChatUtil.sendMessage("&cPlease type a module!");
            return;
        }

        String module = args[0];
        if (ModuleUtil.isModuleExist(module)) {
            String modulename = ModuleUtil.getregisteredname(module);
            if (args.length == 1) {
                ChatUtil.sendMessage("&cPlease type a key!");
                return;
            }

            int key = Keyboard.getKeyIndex(args[1].toUpperCase());

            if (key == 0) {
                ModuleUtil.moduleSetKey(module,-1);
                ChatUtil.sendMessage(modulename + " has been unbound!");
            } else {
                ModuleUtil.moduleSetKey(module,key);
                ChatUtil.sendMessage(modulename + " has been bound to " + args[1].toUpperCase() + " (keycode : " + key + ") !");
            }

        } else {
            ChatUtil.sendMessage("&cModule not found!");
        }
    }
}
