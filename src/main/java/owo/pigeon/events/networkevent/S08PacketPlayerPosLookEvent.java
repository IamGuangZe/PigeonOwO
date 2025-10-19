package owo.pigeon.events.networkevent;

import net.minecraftforge.fml.common.eventhandler.Event;

public class S08PacketPlayerPosLookEvent extends Event {

    public enum Phase {
        START, END;
    }

    private final double posX;
    private final double posY;
    private final double posZ;
    private final float yaw;
    private final float pitch;
    private final Phase phase;

    public S08PacketPlayerPosLookEvent(double posX, double posY, double posZ, float yaw, float pitch, Phase phase) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.phase = phase;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getPosZ() {
        return posZ;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public Phase getPacketPhase() {
        return phase;
    }
}
