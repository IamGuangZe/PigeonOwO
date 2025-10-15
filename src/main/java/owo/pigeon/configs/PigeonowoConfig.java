package owo.pigeon.configs;

import owo.pigeon.commands.CommandManager;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PigeonowoConfig {
    public static Map<String, Object> read() {
        if (!ConfigManager.pigeonowo_f.exists()) return null;

        try (FileReader reader = new FileReader(ConfigManager.pigeonowo_f)) {
            Map<?, ?> rawMap = ConfigManager.gson.fromJson(reader, Map.class);
            if (rawMap != null) {
                Map<String, Object> map = new HashMap<>();
                for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
                    map.put(entry.getKey().toString(), entry.getValue());
                }
                return map;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void write(Map<String, Object> map) {
        try (FileWriter writer = new FileWriter(ConfigManager.pigeonowo_f)) {
            ConfigManager.gson.toJson(map, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCurrentConfig() {
        Map<String, Object> map = read();
        if (map != null && map.containsKey("config")) {
            return map.get("config").toString();
        }
        return "default";
    }

    public static void setCurrentConfig(String name) {
        Map<String, Object> map = read();
        if (map == null) map = new HashMap<>();
        map.put("config", name);
        write(map);
        ConfigManager.currentConfig = name;
    }

    public static char getPrefix() {
        Map<String, Object> map = read();
        if (map != null && map.containsKey("prefix")) {
            String p = map.get("prefix").toString();
            if (!p.isEmpty()) return p.charAt(0);
        }
        return '>';
    }

    public static void setPrefix(char prefix) {
        Map<String, Object> map = read();
        if (map == null) map = new HashMap<>();
        map.put("prefix", String.valueOf(prefix));
        write(map);

        CommandManager.chatPrefix = prefix;
    }
}
