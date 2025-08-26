package owo.pigeon.settings;

import java.util.function.Predicate;

public class EnableSetting extends AbstractSetting<Boolean> {
    protected EnableSetting(String name, Boolean defaultValue, String description, Predicate<Boolean> visible) {
        super(name, defaultValue, description, visible);
    }
}
