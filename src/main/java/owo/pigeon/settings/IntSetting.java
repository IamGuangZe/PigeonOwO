package owo.pigeon.settings;

import java.util.function.Predicate;

public class IntSetting extends AbstractNumberSetting<Integer> {
    protected IntSetting(String name, Integer defaultValue, Integer minValue, Integer maxValue, String description, Predicate<Boolean> visible) {
        super(name, defaultValue, minValue, maxValue, description, visible);
    }
}
