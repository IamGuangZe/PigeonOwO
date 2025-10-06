package owo.pigeon.injections.mixins;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.*;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import owo.pigeon.events.networkevent.PacketReceiveEvent;
import owo.pigeon.events.networkevent.S08PacketPlayerPosLookEvent;
import owo.pigeon.events.networkevent.S08PacketPlayerPosLookEventEnd;
import owo.pigeon.events.networkevent.addToSendQueueEvent;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {

    @Inject(method = "addToSendQueue" , at = @At("HEAD"))
    public void addToSendQueue(Packet p_147297_1_, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new addToSendQueueEvent(p_147297_1_));
    }

    @Inject(method = "handleJoinGame" , at = @At("HEAD"))
    public void PacketReceive (S01PacketJoinGame p_handleJoinGame_1_, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new PacketReceiveEvent(p_handleJoinGame_1_));
    }

    @Inject(method = "handleStatistics" , at = @At("HEAD"))
    public void PacketReceive(S37PacketStatistics p_handleStatistics_1_, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new PacketReceiveEvent(p_handleStatistics_1_));
    }

    @Inject(method = "handleBlockChange" , at = @At("HEAD"))
    public void PacketReceive (S23PacketBlockChange packetIn, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new PacketReceiveEvent(packetIn));
    }

    @Inject(method = "handleMultiBlockChange" , at = @At("HEAD"))
    public void PacketReceive (S22PacketMultiBlockChange packetIn, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new PacketReceiveEvent(packetIn));
    }

    @Inject(method = "handleMaps" , at = @At("HEAD"))
    public void PacketReceive(S34PacketMaps packetIn, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new PacketReceiveEvent(packetIn));
    }

    @Inject(method = "handleSpawnGlobalEntity" , at = @At("HEAD"))
    public void PacketReceive(S2CPacketSpawnGlobalEntity packetIn, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new PacketReceiveEvent(packetIn));
    }

    @Inject(method = "handlePlayerPosLook" , at = @At("HEAD"))
    public void PacketReceive(S08PacketPlayerPosLook packetIn, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new PacketReceiveEvent(packetIn));
        MinecraftForge.EVENT_BUS.post(new S08PacketPlayerPosLookEvent(packetIn.getX(), packetIn.getY(), packetIn.getZ(), packetIn.getYaw(), packetIn.getPitch()));
    }

    @Inject(method = "handlePlayerPosLook" , at = @At("RETURN"))
    public void PacketReceiveEnd(S08PacketPlayerPosLook packetIn, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new S08PacketPlayerPosLookEventEnd(packetIn.getX(), packetIn.getY(), packetIn.getZ(), packetIn.getYaw(), packetIn.getPitch()));
    }


}
