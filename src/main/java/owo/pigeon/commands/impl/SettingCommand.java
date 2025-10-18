package owo.pigeon.commands.impl;

import net.minecraft.block.Block;
import org.lwjgl.input.Keyboard;
import owo.pigeon.commands.Command;
import owo.pigeon.features.modules.Module;
import owo.pigeon.settings.*;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.CommandUtil;
import owo.pigeon.utils.ModuleUtil;

import static owo.pigeon.commands.CommandManager.commandPrefix;

public class SettingCommand extends Command {
    public SettingCommand() {
        super("setting");
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 3) {
            CommandUtil.sendCommandError(CommandUtil.errorReason.ExpectedInteger,
                    this.getCommand(),
                    args,
                    args.length
            );
            return;
        }

        String modulename = args[0];
        String settingname = args[1];
        String value = args[2];

        if (!ModuleUtil.isModuleExist(modulename)) {
            CommandUtil.sendCommandError(CommandUtil.errorReason.UnknownMoudle,
                    this.getCommand(),
                    args,
                    0
            );
            return;
        }

        Module module = ModuleUtil.getModule(modulename);
        modulename = module.name;

        boolean found = false;
        for (AbstractSetting<?> setting : module.getSettings()) {
            if (setting.getName().equalsIgnoreCase(settingname)) {
                found = true;
                settingname = setting.getName();
                if (setting instanceof BlockSetting) {
                    Block block = Block.getBlockFromName(value);
                    if (block == null) {
                        CommandUtil.sendCommandError(CommandUtil.errorReason.UnknownBlock,
                                this.getCommand(),
                                args,
                                2
                        );
                        return;
                    }
                    ((BlockSetting)setting).setValue(block);
                    value = block.getLocalizedName() + "(" + block.getRegistryName() + ")";
                } else if (setting instanceof EnableSetting) {
                    boolean b;
                    if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("enable")) {
                        b = true;
                    } else if (value.equalsIgnoreCase("false") || value.equalsIgnoreCase("disable")) {
                        b = false;
                    } else {
                        CommandUtil.sendCommandError(CommandUtil.errorReason.InvalidBoolean,
                                this.getCommand(),
                                args,
                                2
                        );
                        return;
                    }
                    ((EnableSetting)setting).setValue(b);
                    value = String.valueOf(b);
                } else if (setting instanceof FloatSetting) {
                    try {
                        FloatSetting floatSetting = (FloatSetting) setting;
                        float f = Float.parseFloat(value);

                        if (f < floatSetting.getMinValue()) {
                            f = floatSetting.getMinValue();
                        } else if (f > floatSetting.getMaxValue()) {
                            f = floatSetting.getMaxValue();
                        }

                        ((FloatSetting)setting).setValue(f);
                        value = String.valueOf(f);
                    } catch (NumberFormatException e) {
                        CommandUtil.sendCommandError(CommandUtil.errorReason.ExpectedFloat,
                                this.getCommand(),
                                args,
                                2
                        );
                        return;
                    }
                } else if (setting instanceof IntSetting) {
                    try {
                        IntSetting intSetting = (IntSetting)setting;
                        Integer i = Integer.parseInt(value);

                        if (i < intSetting.getMinValue()) {
                            i = intSetting.getMinValue();
                        } else if (i > intSetting.getMaxValue()) {
                            i = intSetting.getMaxValue();
                        }

                        ((IntSetting)setting).setValue(i);
                        value = String.valueOf(i);
                    } catch (NumberFormatException e) {
                        CommandUtil.sendCommandError(CommandUtil.errorReason.ExpectedInteger,
                                this.getCommand(),
                                args,
                                2
                        );
                        return;
                    }
                } else if (setting instanceof KeySetting) {
                    Integer k = Keyboard.getKeyIndex(value.toUpperCase());

                    if (k == 0) {
                        k = -1;
                    }

                    ((KeySetting) setting).setValue(k);
                    value = Keyboard.getKeyName(k) + "(keycode: " + k + ")";
                } else if (setting instanceof ModeSetting) {
                    try {
                        ModeSetting<?> modeSetting = (ModeSetting<?>) setting;
                        Enum<?> e = Enum.valueOf((Class<Enum>) modeSetting.getValue().getClass(), value.toUpperCase());
                        ((ModeSetting)setting).setValue(e);
                        value = e.toString().toUpperCase();
                    } catch (IllegalArgumentException e) {
                        CommandUtil.sendCommandError(CommandUtil.errorReason.IncorrectArgument,
                                this.getCommand(),
                                args,
                                2
                        );
                        return;
                    }
                } else if (setting instanceof StringSetting) {
                    ((StringSetting)setting).setValue(value);
                } else {
                    this.sendCommandError("Unknown setting type!");
                    return;
                }

                ChatUtil.sendMessage("&aThe &7&l" + settingname + "&r&a of &7&l" + modulename + "&r&a has been changed to &7&l" + value + "&r&a.");
            }
        }
        if (!found) {
            CommandUtil.sendCommandError(CommandUtil.errorReason.UnknownSetting,
                    this.getCommand(),
                    args,
                    1
            );
        }
    }

    @Override
    public String getUsage() {
        return commandPrefix + "setting <module> <setting> <value>";
    }
}
