package owo.pigeon.commands.impl;

import owo.pigeon.commands.Command;
import owo.pigeon.configs.ConfigManager;
import owo.pigeon.configs.SettingConfig;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.CommandUtil;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static owo.pigeon.commands.CommandManager.commandPrefix;

public class ConfigCommand extends Command {
    public ConfigCommand() {
        super("config");
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

        String action = args[0].toLowerCase();

        switch (action) {
            case "save":
                if (args.length < 2) {
                    CommandUtil.sendCommandError(CommandUtil.errorReason.UnknownOrIncompleteCommand,
                            this.getCommand(),
                            args,
                            args.length
                    );
                    return;
                }
                SettingConfig.save(args[1]);
                break;

            case "load":
                if (args.length < 2) {
                    CommandUtil.sendCommandError(CommandUtil.errorReason.UnknownOrIncompleteCommand,
                            this.getCommand(),
                            args,
                            args.length
                    );
                    return;
                }
                SettingConfig.load(args[1]);
                break;

            case "rename":
                if (args.length < 3) {
                    CommandUtil.sendCommandError(CommandUtil.errorReason.UnknownOrIncompleteCommand,
                            this.getCommand(),
                            args,
                            args.length
                    );
                    return;
                }
                File oldFile = new File(ConfigManager.settingDir, args[1] + ".json");
                File newFile = new File(ConfigManager.settingDir, args[2] + ".json");

                if (!oldFile.exists()) {
                    this.sendCommandError("Unknown config &o" + args[1]);
                    return;
                }

                if (newFile.exists()) {
                    this.sendCommandError("Unknown config &o" + args[2]);
                    return;
                }

                if (oldFile.renameTo(newFile)) {
                    ChatUtil.sendMessage("&aConfig &o" + args[1] + ".json &ahas been renamed to &o" + args[2] + ".json");
                } else {
                    this.sendCommandError("Failed to rename config!");
                }
                break;

            case "delete":
                if (args.length < 2) {
                    CommandUtil.sendCommandError(CommandUtil.errorReason.UnknownOrIncompleteCommand,
                            this.getCommand(),
                            args,
                            args.length
                    );
                    return;
                }
                File delFile = new File(ConfigManager.settingDir, args[1] + ".json");
                if (!delFile.exists()) {
                    this.sendCommandError("Unknown config &o" + args[1]);
                    return;
                }

                if (delFile.delete()) {
                    ChatUtil.sendMessage("&aConfig &o" + args[1] + ".json &r&ahas been deleted.");
                } else {
                    this.sendCommandError("Failed to delete config!");
                }
                break;

            case "list":
                if (!ConfigManager.settingDir.exists() || !ConfigManager.settingDir.isDirectory()) {
                    this.sendCommandError("No configs found!");
                    return;
                }

                String[] files = ConfigManager.settingDir.list((d, name) -> name.endsWith(".json"));
                if (files == null || files.length == 0) {
                    this.sendCommandError("No configs found!");
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
                } catch (NumberFormatException ignored) {
                }

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

            case "dir":
                if (!ConfigManager.settingDir.exists()) {
                    ConfigManager.settingDir.mkdirs();
                }

                try {
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(ConfigManager.settingDir);
                        ChatUtil.sendMessage("&aOpened config folder: &7" + ConfigManager.settingDir.getAbsolutePath());
                    } else {
                        this.sendCommandError("Desktop is not supported on this system!");
                    }
                } catch (IOException e) {
                    this.sendCommandError("Failed to open config folder: " + e.getMessage());
                }
                break;

            default:
                CommandUtil.sendCommandError(CommandUtil.errorReason.IncorrectArgument,
                        this.getCommand(),
                        args,
                        0
                );
                break;
        }
    }

    @Override
    public String getUsage() {
        return commandPrefix + "config (save|load|delete) <name>\n" +
                commandPrefix + "config rename <oldname> <newname>\n" +
                commandPrefix + "config list [<page>]\n" +
                commandPrefix + "config dir";
    }
}
