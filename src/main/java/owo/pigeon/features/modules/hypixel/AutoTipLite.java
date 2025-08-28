package owo.pigeon.features.modules.hypixel;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import owo.pigeon.features.Category;
import owo.pigeon.features.Module;
import owo.pigeon.settings.IntSetting;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.WorldUtil;
import owo.pigeon.utils.hypixel.HypixelUtil;

import java.util.Timer;
import java.util.TimerTask;

public class AutoTipLite extends Module {

    public AutoTipLite() {
        super("Autotip", Category.HYPIXEL, -1);
    }

    public IntSetting delay = setting("Delay", 15, 1, 60, "The time interval between each tip (Minutes).", v -> true);

    private Timer timer;

    @Override
    public void onEnable() {
        if (WorldUtil.isNotNull() && HypixelUtil.isInHypixel()) {
            if (timer == null) startTimer();
        }
    }

    @Override
    public void onDisable() {
        stopTimer();
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (WorldUtil.isNotNull() && HypixelUtil.isInHypixel()) {
            if (timer == null) startTimer();
        } else {
            stopTimer();
        }
    }

    private void startTimer() {
        if (timer != null) stopTimer();

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (WorldUtil.isNotNull()) {
                    mc.thePlayer.sendChatMessage("/tip all");
                    ChatUtil.sendCustomPrefixMessage("AutoTip","All recipients have been tipped!");
                }
            }
        }, 0, delay.getValue() * 60 * 1000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
