package owo.pigeon.events.networkevent;

import net.minecraftforge.fml.common.eventhandler.Event;

public class S08PacketPlayerPosLookEvent extends Event {
    private final double posX;
    private final double posY;
    private final double posZ;
    private final float yaw;
    private final float pitch;

    public S08PacketPlayerPosLookEvent(double posX, double posY, double posZ, float yaw, float pitch) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.pitch = pitch;
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
}
