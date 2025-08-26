package owo.pigeon.settings;

import java.util.function.Predicate;

public class FloatSetting extends AbstractNumberSetting<Float> {
    protected FloatSetting(String name, Float defaultValue, Float minValue, Float maxValue, String description, Predicate<Boolean> visible) {
        super(name, defaultValue, minValue, maxValue, description, visible);
    }
}
