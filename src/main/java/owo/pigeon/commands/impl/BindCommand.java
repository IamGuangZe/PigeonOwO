package owo.pigeon.commands.impl;

import org.lwjgl.input.Keyboard;
import owo.pigeon.commands.Command;
import owo.pigeon.features.modules.Module;
import owo.pigeon.features.modules.ModuleManager;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.CommandUtil;
import owo.pigeon.utils.ModuleUtil;

import java.util.Map;
import java.util.TreeMap;

import static owo.pigeon.commands.CommandManager.commandPrefix;

public class BindCommand extends Command {
    public BindCommand() {
        super("bind");
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            CommandUtil.sendCommandError(CommandUtil.errorReason.UnknownOrIncompleteCommand,
                    this.getCommand(),
                    args,
                    args.length
            );
            return;
        }

        String input = args[0];
        if (ModuleUtil.isModuleExist(input)) {
            String modulename = ModuleUtil.getName(input);
            if (args.length < 2) {
                CommandUtil.sendCommandError(CommandUtil.errorReason.UnknownOrIncompleteCommand,
                        this.getCommand(),
                        args,
                        args.length
                );
                return;
            }

            int key = Keyboard.getKeyIndex(args[1].toUpperCase());

            if (key == 0) {
                ModuleUtil.moduleSetKey(input, -1);
                ChatUtil.sendMessage("&a" + modulename + " has been unbound!");
            } else {
                ModuleUtil.moduleSetKey(input, key);
                ChatUtil.sendMessage("&a" + modulename + " has been bound to " + args[1].toUpperCase() + " (keycode : " + key + ") !");
            }

        } else if (input.equalsIgnoreCase("list")) {

            Map<Integer, String> bindlist = new TreeMap<>();

            for (Module module : ModuleManager.modules) {
                if (module.Key != -1) {
                    bindlist.put(module.Key, module.name);
                }
            }

            ChatUtil.sendMessage("&8Key Bindings List:");
            for (Map.Entry<Integer, String> entry : bindlist.entrySet()) {
                String keyName = Keyboard.getKeyName(entry.getKey()); // 将 keycode 转成按键名
                ChatUtil.sendMessage("&7[" + keyName + "] " + entry.getValue());
            }

        } else {
            CommandUtil.sendCommandError(CommandUtil.errorReason.IncorrectArgument,
                    this.getCommand(),
                    args,
                    0
            );
        }
    }

    @Override
    public String getUsage() {
        return commandPrefix + "bind <module> <key>\n" +
                commandPrefix + "bind list [<page>]";
    }
}
