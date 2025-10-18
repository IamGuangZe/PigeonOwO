package owo.pigeon.features.modules.client.test;

import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.utils.ChatUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import static owo.pigeon.utils.WorldUtil.isNotNull;

public class TimeDisplay extends Module {
    public TimeDisplay() {
        super("TimeDisplay", Category.CLIENT, -1);
    }

    private int tick = 20;
    private long lastTime = -1; // 上一tick时间

    @Override
    public void onTick() {

        if (tick == 0) {
            this.disable();
            return;
        }

        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        String timeString = sdf.format(new Date(currentTimeMillis));

        if (isNotNull()) {
            if (lastTime != -1) {
                double deltaSeconds = (currentTimeMillis - lastTime) / 1000.0;
                ChatUtil.sendMessage(timeString + " Tick: " + tick + " Δ: " + deltaSeconds + "s");
            } else {
                ChatUtil.sendMessage(timeString + " Tick: " + tick + " Δ: N/A");
            }
            lastTime = currentTimeMillis;
            tick--;
        }
    }

    @Override
    public void onEnable() {
        tick = 20;
        lastTime = -1;
    }
}
