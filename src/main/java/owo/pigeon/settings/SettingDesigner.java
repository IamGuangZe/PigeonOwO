package owo.pigeon.settings;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.function.Predicate;

public abstract class SettingDesigner {
    private final ArrayList<AbstractSetting<?>> settings = new ArrayList<>();

    public BlockSetting setting(String name, Block value, String descriptions, Predicate<Boolean> visible) {
        BlockSetting setting = new BlockSetting(name, value, descriptions, visible);
        settings.add(setting);
        return setting;
    }

    public EnableSetting setting(String name, Boolean value, String descriptions, Predicate<Boolean> visible) {
        EnableSetting setting = new EnableSetting(name, value, descriptions, visible);
        settings.add(setting);
        return setting;
    }

    public FloatSetting setting(String name, Float value, Float minValue, Float maxValue, String description, Predicate<Boolean> visible) {
        FloatSetting setting = new FloatSetting(name, value, minValue, maxValue, description, visible);
        settings.add(setting);
        return setting;
    }

    public IntSetting setting(String name, Integer value, Integer minValue, Integer maxValue, String description, Predicate<Boolean> visible) {
        IntSetting setting = new IntSetting(name, value, minValue, maxValue, description, visible);
        settings.add(setting);
        return setting;
    }

    /*public IntSetting setting(String name, Integer value, String descriptions, Predicate<Boolean> visible) {
        IntSetting setting = new IntSetting(name, value, Integer.MIN_VALUE, Integer.MAX_VALUE, descriptions, visible);
        settings.add(setting);
        return setting;
    }*/

    public KeySetting setting(String name, Integer value, String descriptions, Predicate<Boolean> visible) {
        KeySetting setting = new KeySetting(name, value, descriptions, visible);
        settings.add(setting);
        return setting;
    }

    public StringSetting setting(String name, String value, String descriptions, Predicate<Boolean> visible) {
        StringSetting setting = new StringSetting(name, value, descriptions, visible);
        settings.add(setting);
        return setting;
    }

    public <T extends Enum<T>> ModeSetting<T> setting(String name, T value, String descriptions, Predicate<Boolean> visible) {
        ModeSetting<T> setting = new ModeSetting<T>(name, value,descriptions,visible);
        settings.add(setting);
        return setting;
    }

    public ArrayList<AbstractSetting<?>> getSettings() {
        return settings;
    }
}
