package owo.pigeon.commands.impl;

import owo.pigeon.commands.Command;
import owo.pigeon.configs.ConfigManager;
import owo.pigeon.configs.SettingConfig;
import owo.pigeon.utils.ChatUtil;

import java.io.File;

import static owo.pigeon.commands.CommandManager.commandPrefix;

public class ConfigCommand extends Command {
    public ConfigCommand() {
        super("config");
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            sendUsage();
            return;
        }

        String action = args[0].toLowerCase();

        switch (action) {
            case "save":
                if (args.length < 2) {
                    sendUsage();
                    return;
                }
                SettingConfig.save(args[1]);
                break;

            case "load":
                if (args.length < 2) {
                    sendUsage();
                    return;
                }
                SettingConfig.load(args[1]);
                break;

            case "rename":
                if (args.length < 3) {
                    sendUsage();
                    return;
                }
                File oldFile = new File(ConfigManager.settingDir, args[1] + ".json");
                File newFile = new File(ConfigManager.settingDir, args[2] + ".json");

                if (!oldFile.exists()) {
                    ChatUtil.sendMessage("&cConfig &o" + args[1] + ".json &r&cdoes not exist!");
                    return;
                }

                if (newFile.exists()) {
                    ChatUtil.sendMessage("&cConfig &o" + args[2] + ".json &r&calready exists!");
                    return;
                }

                if (oldFile.renameTo(newFile)) {
                    ChatUtil.sendMessage("&aConfig &o" + args[1] + ".json &ahas been renamed to &o" + args[2] + ".json");
                } else {
                    ChatUtil.sendMessage("&cFailed to rename config!");
                }
                break;

            case "delete":
                if (args.length < 2) {
                    sendUsage();
                    return;
                }
                File delFile = new File(ConfigManager.settingDir, args[1] + ".json");
                if (!delFile.exists()) {
                    ChatUtil.sendMessage("&cConfig &o" + args[1] + ".json &r&cdoes not exist!");
                    return;
                }

                if (delFile.delete()) {
                    ChatUtil.sendMessage("&aConfig &o" + args[1] + ".json &r&ahas been deleted.");
                } else {
                    ChatUtil.sendMessage("&cFailed to delete config!");
                }
                break;

            case "list":
                File dir = ConfigManager.settingDir;
                if (!dir.exists() || !dir.isDirectory()) {
                    ChatUtil.sendMessage("&cNo configs found!");
                    return;
                }

                String[] files = dir.list((d, name) -> name.endsWith(".json"));
                if (files == null || files.length == 0) {
                    ChatUtil.sendMessage("&cNo configs found!");
                    return;
                }

                // 支持分页，每页显示7个
                int page = 1;
                int perPage = 7;
                try {
                    if (args.length > 1) {
                        int input = Integer.parseInt(args[1]);
                        if (input > 0) page = input;
                    }
                } catch (NumberFormatException ignored) {}

                int maxPage = (int) Math.ceil((double) files.length / perPage);
                if (page > maxPage) page = maxPage;

                int start = (page - 1) * perPage;
                int end = Math.min(start + perPage, files.length);

                ChatUtil.sendMessage("&8Config List (Page " + page + "/" + maxPage + "):");
                for (int i = start; i < end; i++) {
                    ChatUtil.sendMessage("&7- " + files[i].replace(".json", ""));
                }
                ChatUtil.sendMessage("&8Use \"" + commandPrefix + " config list <page>\" to view other pages.");
                break;

            default:
                sendUsage();
                break;
        }
    }

    @Override
    public String getUsage() {
        return commandPrefix + "config <save|load|delete> <name> OR " +
                commandPrefix + "config <rename> <oldname> <newname> OR " +
                commandPrefix + "config <list> [page]";
    }
}
