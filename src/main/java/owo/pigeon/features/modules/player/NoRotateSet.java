package owo.pigeon.features.modules.player;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import owo.pigeon.events.networkevent.S08PacketPlayerPosLookEvent;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.settings.ModeSetting;

public class NoRotateSet extends Module {
    public NoRotateSet() {
        super("NoRotateSet", Category.PLAYER, -1);
    }

    public enum modeEnum {
        RESET, CANCEL
    }

    public ModeSetting<modeEnum> mode = setting("mode", modeEnum.RESET, v -> true);

    private float yaw, pitch;

    @SubscribeEvent
    public void S08PacketPlayerPosLookPacketReceive(S08PacketPlayerPosLookEvent event) {
        if (event.getPacketPhase() == S08PacketPlayerPosLookEvent.Phase.START) {
            yaw = mc.thePlayer.rotationYaw;
            pitch = mc.thePlayer.rotationPitch;
        }
        if (event.getPacketPhase() == S08PacketPlayerPosLookEvent.Phase.END) {
            mc.thePlayer.rotationYaw = yaw;
            mc.thePlayer.rotationPitch = pitch;
        }
    }
}
