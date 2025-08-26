package owo.pigeon.features.modules.player;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import owo.pigeon.events.networkevent.S08PacketPlayerPosLookEvent;
import owo.pigeon.events.networkevent.S08PacketPlayerPosLookEventEnd;
import net.minecraft.entity.player.EntityPlayer;
import owo.pigeon.features.Category;
import owo.pigeon.features.Module;

public class NoRotateSet extends Module {
    public NoRotateSet() {
        super("NoRotateSet",Category.PLAYER,-1);
    }

    private float yaw,pitch;

    @SubscribeEvent
    public void S08PacketPlayerPosLookPacketReceive (S08PacketPlayerPosLookEvent event) {
        yaw = mc.thePlayer.rotationYaw;
        pitch = mc.thePlayer.rotationPitch;
    }

    @SubscribeEvent
    public void S08PacketPlayerPosLookPacketReceive (S08PacketPlayerPosLookEventEnd event) {
        mc.thePlayer.rotationYaw = yaw;
        mc.thePlayer.rotationPitch = pitch;
    }
}
