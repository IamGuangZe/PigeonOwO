package owo.pigeon.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import owo.pigeon.commands.CommandManager;

import java.io.File;

public class ConfigManager {

    // 超级史山代码 -- 2025.10.15
    // 将超级史山拆分了 -- 2025.10.15

    public static String currentConfig = "default";

    public static final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public static final File baseDir = new File("config/pigeonowo/");
    public static final File settingDir = new File(baseDir, "setting/");

    public static final File pigeonowo_f = new File(baseDir, "pigeonowo.json");

    public void init() {
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }
        if (!settingDir.exists()) {
            settingDir.mkdirs();
        }
        if (!pigeonowo_f.exists()) {
            // PigeonowoConfig.setCurrentConfig("default");
            PigeonowoConfig.setPrefix('>');
        }

        // currentConfig = PigeonowoConfig.getCurrentConfig();
        CommandManager.commandPrefix = PigeonowoConfig.getPrefix();

        // PigeonowoConfig.setCurrentConfig(currentConfig);
        PigeonowoConfig.setPrefix(CommandManager.commandPrefix);

        SettingConfig.load("default");
    }
}
    /*public void init() {
            if (!CONFIG_DIR.exists()) CONFIG_DIR.mkdirs();
            for (Category category : Category.values()) {
                File categoryDir = new File(CONFIG_DIR, category.name());
                if (!categoryDir.exists()) categoryDir.mkdirs();
                Pigeon.modulemanager.getAllModules()
                        .stream().filter(it -> it.category == category)
                        .forEach(it -> {
                            try {
                                File config = new File(categoryDir,it.name + ".json");
                                if (!config.exists()) {
                                    config.createNewFile();
                                    updateConfig(it,config);
                                    return;
                                }
                                try {
                                    JsonObject json = gson.fromJson(new String(Files.readAllBytes(config.toPath()), StandardCharsets.UTF_8), JsonObject.class);
                                    applyConfig(it,json);
                                } catch (Exception e) {
                                    updateConfig(it,config);
                                }
                            }catch (IOException exception) {
                                exception.fillInStackTrace();
                            }
                        });
            }
    }

    private void updateConfig(Module module,File config) throws IOException {
        JsonObject parent = new JsonObject();
        for (AbstractSetting<?> setting : module.getSettings()) {
            parent.addProperty(setting.getName(),setting.getValue().toString());
        }
        Files.write(config.toPath(),parent.toString().getBytes(StandardCharsets.UTF_8));
    }

    private void applyConfig(Module module,JsonObject json) {
        for (AbstractSetting<?> setting : module.getSettings()) {
            if (!json.has(setting.getName())) continue;
            if (setting instanceof EnableSetting) {
                ((EnableSetting) setting).setValue(json.get(setting.getName()).getAsBoolean());
            }
            if (setting instanceof StringSetting) {
                ((StringSetting) setting).setValue(json.get(setting.getName()).getAsString());
            }
            if (setting instanceof KeySetting) {
                ((KeySetting) setting).setValue(json.get(setting.getName()).getAsInt());
            }
            if (setting instanceof ModeSetting) {
                try {
                    EnumConverter converter = new EnumConverter(((Enum) setting.getValue()).getClass());
                    Enum value = converter.doBackward(json.get(setting.getName()));
                    ((ModeSetting) setting).setValue((value == null) ? setting.defaultValue : value);
                } catch (Exception exception) {
                }
                return;
            }
            if (setting instanceof AbstractNumberSetting) {
                ((AbstractSetting) setting).setValue(json.get(setting.getName()).getAsDouble());
            }
            if (setting instanceof BlockSetting) {

            }
        }
    }*/