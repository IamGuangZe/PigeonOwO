package owo.pigeon.configs;

import org.lwjgl.input.Keyboard;
import owo.pigeon.Pigeon;
import owo.pigeon.features.modules.client.ClickGui;
import owo.pigeon.gui.panels.CategoryPanel;
import owo.pigeon.settings.AbstractSetting;
import owo.pigeon.settings.ModeSetting;
import owo.pigeon.utils.ModuleUtil;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ClickguiConfig {

    public static void save() {
        Map<String, Object> config_m = new HashMap<>();

        try {
            for (CategoryPanel panel : Pigeon.clickGuiScreen.categoryPanels) {
                Map<String, Object> panel_m = new HashMap<>();
                panel_m.put("x", panel.x);
                panel_m.put("y", panel.y);
                panel_m.put("display", panel.getDisplayModule());
                config_m.put(panel.getCategory().name(), panel_m);
            }

            Map<String, Object> clickGui_m = new HashMap<>();
            ClickGui clickGui = (ClickGui) ModuleUtil.getModule(ClickGui.class);

            clickGui_m.put("key", clickGui.Key);

            for (AbstractSetting<?> setting : clickGui.getSettings()) {
                clickGui_m.put(setting.getName(), setting.getValue());
            }

            config_m.put(clickGui.name, clickGui_m);

            try (FileWriter writer = new FileWriter(ConfigManager.clickgui_f)) {
                ConfigManager.gson.toJson(config_m, writer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        if (!ConfigManager.clickgui_f.exists()) {
            return;
        }

        try (FileReader reader = new FileReader(ConfigManager.clickgui_f)) {
            Map<?, ?> config_m = ConfigManager.gson.fromJson(reader, Map.class);

            if (config_m == null) {
                return;
            }

            for (CategoryPanel panel : Pigeon.clickGuiScreen.categoryPanels) {
                Object category_obj = config_m.get(panel.getCategory().name());
                if (category_obj instanceof Map) {
                    Map<?, ?> panel_m = (Map<?, ?>) category_obj;

                    Object x_obj = panel_m.get("x");
                    Object y_obj = panel_m.get("y");
                    Object display_obj = panel_m.get("display");

                    if (x_obj instanceof Number) {
                        panel.x = ((Number) x_obj).intValue();
                    }

                    if (y_obj instanceof Number) {
                        panel.y = ((Number) y_obj).intValue();
                    }

                    if (display_obj instanceof Boolean) {
                        panel.setDisplayModule((Boolean) display_obj);
                    }
                }
            }

            Object clickGui_obj = config_m.get("ClickGui");
            if (clickGui_obj instanceof Map) {
                Map<?, ?> clickGui_m = (Map<?, ?>) clickGui_obj;

                ClickGui clickGui = (ClickGui) ModuleUtil.getModule(ClickGui.class);

                Object key_obj = clickGui_m.get("key");
                if (key_obj instanceof Number) {
                    clickGui.setKey(((Number) key_obj).intValue());
                } else {
                    clickGui.setKey(Keyboard.KEY_RSHIFT);
                }

                for (AbstractSetting<?> setting : clickGui.getSettings()) {
                    if (clickGui_m.containsKey(setting.getName())) {
                        Object value = clickGui_m.get(setting.getName());

                        if (setting instanceof ModeSetting) {
                            try {
                                ModeSetting<?> modeSetting = (ModeSetting<?>) setting;
                                Enum<?> e = Enum.valueOf((Class<Enum>) modeSetting.getValue().getClass(), value.toString().toUpperCase());
                                ((ModeSetting) setting).setValue(e);
                            } catch (Exception ex) {
                                setting.resetValue();
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
