package owo.pigeon.features.modules.client.test;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import owo.pigeon.events.networkevent.PacketReceiveEvent;
import owo.pigeon.events.networkevent.S08PacketPlayerPosLookEvent;
import owo.pigeon.events.networkevent.addToSendQueueEvent;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.utils.ChatUtil;

public class PacketReceive extends Module {
    public PacketReceive() {
        super("PacketReceive", Category.CLIENT, -1);
    }

    @SubscribeEvent
    public void onAddToSendQueue (addToSendQueueEvent event) {
        if (!(event.getPacket() instanceof C03PacketPlayer)) {
            ChatUtil.sendMessage("");
            ChatUtil.sendMessage("Send Packet : " + event.getPacket());
        }
    }

    @SubscribeEvent
    public void onPacketReceive (PacketReceiveEvent event) {
        ChatUtil.sendMessage("");
        ChatUtil.sendMessage("Get Packet : " + event.getPacket());
    }

    @SubscribeEvent
    public void onPacketReceived(FMLNetworkEvent.ClientCustomPacketEvent event) {
        ChatUtil.sendMessage("");
        ChatUtil.sendMessage("GetA Packet : " + event.packet);
    }

    @SubscribeEvent
    public void S08PacketPlayerPosLookPacketReceive (S08PacketPlayerPosLookEvent event) {
        ChatUtil.sendMessage("");
        ChatUtil.sendMessage("S08PacketPlayerPosLook : " + event.getPosX() + "||" + event.getPosY() + "||" + event.getPosZ() + "||" +event.getYaw() + "||" + event.getPitch());
    }
}
