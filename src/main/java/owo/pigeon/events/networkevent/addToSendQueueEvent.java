package owo.pigeon.events.networkevent;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Event;

public class addToSendQueueEvent extends Event {
    private final Packet<?> packet;

    public addToSendQueueEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }
}
