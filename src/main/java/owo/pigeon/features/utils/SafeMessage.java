package owo.pigeon.features.utils;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import owo.pigeon.utils.ChatUtil;

import java.util.ArrayList;
import java.util.List;

import static owo.pigeon.features.modules.Module.mc;

public class SafeMessage {
    public static List<String> messages = new ArrayList<>();

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.ingameGUI == null || mc.ingameGUI.getChatGUI() == null) {
            return;
        }

        for (String message : messages) {
            ChatUtil.sendMessage(message);
        }
        messages.clear();
    }
}
