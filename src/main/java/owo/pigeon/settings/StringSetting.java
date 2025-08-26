package owo.pigeon.settings;

import java.util.function.Predicate;

public class StringSetting extends AbstractSetting<String> {
    protected StringSetting(String name,String defaultValue, String description, Predicate<Boolean> visible) {
        super(name, defaultValue, description, visible);
    }
}
