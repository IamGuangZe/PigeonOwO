package owo.pigeon.configs;

import net.minecraft.block.Block;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.features.modules.client.ClickGui;
import owo.pigeon.settings.*;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.ModuleUtil;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SettingConfig {
    public static void save(String name) {
        File config_F = new File(ConfigManager.settingDir, name + ".json");
        Map<String, Object> config_m = new HashMap<>();

        for (Category category : Category.values()) {
            Map<String, Object> category_m = new HashMap<>();

            for (Module module : ModuleUtil.getAllModulesFromCategory(category)) {
                if (module.getClass() == ClickGui.class) {
                    continue;
                }

                Map<String, Object> module_m = new HashMap<>();

                module_m.put("enable", module.isEnable());
                module_m.put("key", module.Key);

                for (AbstractSetting<?> setting : module.getSettings()) {
                    if (setting instanceof BlockSetting) {
                        module_m.put(setting.getName(), Block.getIdFromBlock(((BlockSetting)setting).getValue()));
                    } else {
                        module_m.put(setting.getName(), setting.getValue());
                    }
                }
                category_m.put(module.name, module_m);
            }
            config_m.put(category.name(), category_m);
        }

        try (FileWriter fileWriter = new FileWriter(config_F)) {
            ConfigManager.gson.toJson(config_m, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ChatUtil.sendSafeMessage("&aConfig &o" + name + ".json &r&ahas been saved");
        // PigeonowoConfig.setCurrentConfig(name);
    }

    public static void load(String name) {
        File config_F = new File(ConfigManager.settingDir, name + ".json");

        if (!config_F.exists()) {
            if (name.equalsIgnoreCase("default")) {
                for (Module module : ModuleUtil.getAllModules()) {

                    if (module.getClass() == ClickGui.class) {
                        continue;
                    }

                    ModuleUtil.moduleDisable(module.getClass());
                    module.setKey(-1);
                    for (AbstractSetting<?> setting : module.getSettings()) {
                        setting.resetValue();
                    }
                }
                ChatUtil.sendSafeMessage("&aDefault config has been loaded.");
                return;
            } else {
                ChatUtil.sendSafeMessage("&cConfig &o" + name + ".json &r&cnot found!");
                return;
            }
        }

        /*if (!name.equalsIgnoreCase(ConfigManager.currentConfig)) {
            save(ConfigManager.currentConfig);
        }*/

        try (FileReader reader = new FileReader(config_F)) {
            Map<?, ?> config_m = ConfigManager.gson.fromJson(reader, Map.class);

            if (config_m == null) {
                ChatUtil.sendSafeMessage("&cFailed to read config!");
                return;
            }

            for (Category category : Category.values()) {
                Object category_obj = config_m.get(category.name());

                if (category_obj instanceof Map) {
                    Map<?, ?> category_m = (Map<?, ?>) category_obj;

                    for (Module module : ModuleUtil.getAllModulesFromCategory(category)) {
                        if (module.getClass() == ClickGui.class) {
                            continue;
                        }

                        Object module_obj = category_m.get(module.name);

                        if (module_obj instanceof Map) {
                            Map<?, ?> module_m = (Map<?, ?>) module_obj;

                            Object enable_obj = module_m.get("enable");
                            Object key_obj = module_m.get("key");

                            if (enable_obj instanceof Boolean) {
                                boolean enable_b = (Boolean) enable_obj;

                                if (enable_b != module.isEnable()) {
                                    if (enable_b) {
                                        ModuleUtil.moduleEnable(module.name);
                                    } else {
                                        ModuleUtil.moduleDisable(module.name);
                                    }
                                }
                            } else {
                                ModuleUtil.moduleDisable(module.name);
                            }

                            if (key_obj instanceof Number) {
                                module.setKey(((Number) key_obj).intValue());
                            } else {
                                module.setKey(-1);
                            }

                            for (AbstractSetting<?> setting : module.getSettings()) {
                                if (module_m.containsKey(setting.getName())) {
                                    Object value = module_m.get(setting.getName());

                                    try {
                                        if (setting instanceof BlockSetting) {
                                            if (value instanceof Number) {
                                                int id = ((Number) value).intValue();
                                                ((BlockSetting) setting).setValue(Block.getBlockById(id));
                                            } else {
                                                throw new IllegalArgumentException("Expected Number");
                                            }
                                        } else if (setting instanceof EnableSetting) {
                                            if (value instanceof Boolean) {
                                                ((EnableSetting) setting).setValue((Boolean) value);
                                            } else {
                                                throw new IllegalArgumentException("Expected Boolean");
                                            }
                                        } else if (setting instanceof FloatSetting) {
                                            if (value instanceof Number) {
                                                FloatSetting floatSetting = (FloatSetting) setting;
                                                float f = ((Number) value).floatValue();

                                                if (f < floatSetting.getMinValue()) {
                                                    f = floatSetting.getMinValue();
                                                }

                                                if (f > floatSetting.getMaxValue()) {
                                                    f = floatSetting.getMaxValue();
                                                }

                                                ((FloatSetting) setting).setValue(f);
                                            } else {
                                                throw new IllegalArgumentException("Expected Number");
                                            }
                                        } else if (setting instanceof IntSetting) {
                                            if (value instanceof Number) {
                                                IntSetting intSetting = (IntSetting) setting;
                                                int i = ((Number) value).intValue();

                                                if (i < intSetting.getMinValue()) {
                                                    i = intSetting.getMinValue();
                                                }

                                                if (i > intSetting.getMaxValue()) {
                                                    i = intSetting.getMaxValue();
                                                }

                                                ((IntSetting) setting).setValue(i);
                                            } else {
                                                throw new IllegalArgumentException("Expected Number");
                                            }
                                        } else if (setting instanceof KeySetting) {
                                            if (value instanceof Number) {
                                                ((KeySetting) setting).setValue(((Number) value).intValue());
                                            } else {
                                                throw new IllegalArgumentException("Expected Number");
                                            }
                                        } else if (setting instanceof ModeSetting) {
                                            try {
                                                ModeSetting<?> modeSetting = (ModeSetting<?>) setting;
                                                Enum<?> e = Enum.valueOf((Class<Enum>) modeSetting.getValue().getClass(), value.toString().toUpperCase());
                                                ((ModeSetting) setting).setValue(e);
                                            } catch (Exception ex) {
                                                throw new IllegalArgumentException("Invalid mode value: " + value);
                                            }
                                        } else if (setting instanceof StringSetting) {
                                            if (value instanceof String) {
                                                ((StringSetting) setting).setValue((String) value);
                                            } else {
                                                throw new IllegalArgumentException("Expected String");
                                            }
                                        } else {
                                            throw new IllegalArgumentException("Unknown setting type");
                                        }

                                    } catch (Exception ex) {
                                        setting.resetValue();
                                        ChatUtil.sendSafeMessage("&cThe value type of &l" + setting.getName() + "&r&c in &l" + module.name + "&r&c is incorrect and has been reset to the default value!");
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // PigeonowoConfig.setCurrentConfig(name);
            ChatUtil.sendSafeMessage("&aConfig &o" + name + ".json &r&ahas been loaded.");

        } catch (IOException e) {
            ChatUtil.sendSafeMessage("&cFailed to load config: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
