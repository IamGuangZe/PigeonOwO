package owo.pigeon.settings;

import java.util.function.Predicate;

public class ModeSetting<P extends Enum<P>> extends AbstractSetting<P>{
    protected ModeSetting(String name, P defaultValue, String description, Predicate<Boolean> visible) {
        super(name, defaultValue, description, visible);
    }
}
