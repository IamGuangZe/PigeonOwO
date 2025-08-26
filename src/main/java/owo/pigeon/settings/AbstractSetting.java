package owo.pigeon.settings;

import java.util.function.Predicate;


public abstract class AbstractSetting<T> {
    public final T defaultValue;
    protected T value;
    protected final String name;
    protected final String description;
    protected final Predicate<Boolean> visible;

    protected AbstractSetting(String name, T defaultValue, String description, Predicate<Boolean> visible) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.description = description;
        this.visible = visible;
    }


    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
