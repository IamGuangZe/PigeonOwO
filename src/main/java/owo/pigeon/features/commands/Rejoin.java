package owo.pigeon.features.commands;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static owo.pigeon.features.modules.Module.mc;

public class Rejoin {
    private static boolean isRejoin = false;

    public static void rejoin() {
        mc.thePlayer.sendChatMessage("/l");
        isRejoin = true;
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event){
        if (isRejoin) {
            mc.thePlayer.sendChatMessage("/rej");
            isRejoin = false;
        }
    }
}
