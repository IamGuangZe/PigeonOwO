package owo.pigeon.settings;

import java.util.function.Predicate;

public class KeySetting extends AbstractSetting<Integer>{
    protected KeySetting(String name, Integer defaultValue, String description, Predicate<Boolean> visible) {
        super(name, defaultValue, description, visible);
    }
}
