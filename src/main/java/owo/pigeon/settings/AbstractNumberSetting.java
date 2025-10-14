package owo.pigeon.settings;


import java.util.function.Predicate;

public class AbstractNumberSetting<N extends Number> extends AbstractSetting<N> {
    protected final N minValue;
    protected final N maxValue;

    protected AbstractNumberSetting(String name, N defaultValue, N minValue, N maxValue, String description, Predicate<Boolean> visible) {
        super(name, defaultValue, description, visible);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public N getMinValue() {
        return minValue;
    }

    public N getMaxValue() {
        return maxValue;
    }
}
