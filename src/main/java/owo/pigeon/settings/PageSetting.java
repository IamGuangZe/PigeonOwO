package owo.pigeon.settings;

import java.util.function.Predicate;

public class PageSetting<P extends Enum<P>> extends AbstractSetting<P>{
    protected PageSetting(String name, P defaultValue, String description, Predicate<Boolean> visible) {
        super(name, defaultValue, description, visible);
    }
}
