package owo.pigeon.commands.impl;

import owo.pigeon.commands.Command;
import owo.pigeon.configs.SettingConfig;
import owo.pigeon.utils.ChatUtil;

public class ConfigCommand extends Command {
    public ConfigCommand() {
        super("config");
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            return;
        }

        switch (args[0].toLowerCase()) {
            case "save":
                SettingConfig.save(args[1]);
                break;
            case "load":
                SettingConfig.load(args[1]);
                break;
            default:
                ChatUtil.sendMessage("aaa");
        }
    }
}
