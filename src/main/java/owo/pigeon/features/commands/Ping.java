package owo.pigeon.features.commands;

import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S37PacketStatistics;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import owo.pigeon.events.networkevent.PacketReceiveEvent;
import owo.pigeon.utils.ChatUtil;

import static owo.pigeon.features.Module.mc;

public class Ping {

    private static long lastPingAt = -1L;
    private static boolean invokedCommand = false;

    public static void setInvokedCommand(boolean invokedCommand) {
        Ping.invokedCommand = invokedCommand;
    }

    public static void sengPing() {
        if (lastPingAt > 0) {
            ChatUtil.sendMessage("&cAlready pinging!");
            return;
        }
        mc.thePlayer.sendQueue.getNetworkManager().sendPacket(
                new C16PacketClientStatus(C16PacketClientStatus.EnumState.REQUEST_STATS),
                future -> lastPingAt = System.nanoTime()
        );
    }

    @SubscribeEvent
    public void onPacket(PacketReceiveEvent event) {
        if (lastPingAt > 0) {
            Object packet = event.getPacket();
            if (packet instanceof S01PacketJoinGame) {
                lastPingAt = -1L;
                invokedCommand = false;
            } else if (packet instanceof S37PacketStatistics) {
                double diff = Math.abs(System.nanoTime() - lastPingAt) / 1_000_000.0;
                lastPingAt *= -1;
                if (invokedCommand) {
                    invokedCommand = false;
                    String color = getPingColorCode(diff);
                    ChatUtil.sendMessage(color + (int)diff + " &7ms");
                }
            }
        }
    }

    private static String getPingColorCode(double diff) {
        if (diff < 50) {
            return "&a";
        } else if (diff < 100) {
            return "&2";
        } else if (diff < 150) {
            return "&e";
        } else if (diff < 250) {
            return "&6";
        } else {
            return "&c";
        }
    }
}
