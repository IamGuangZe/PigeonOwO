package owo.pigeon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import owo.pigeon.features.Category;
import owo.pigeon.features.Module;
import owo.pigeon.settings.*;
import owo.pigeon.utils.EnumConverter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class ConfigManager {
    private final File CONFIG_DIR = new File("config/pigeonowo/");
    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    public void init() {
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
    }
}
