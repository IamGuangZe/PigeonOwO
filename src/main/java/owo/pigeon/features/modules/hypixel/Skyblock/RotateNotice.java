package owo.pigeon.features.modules.hypixel.Skyblock;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import owo.pigeon.events.networkevent.S08PacketPlayerPosLookEvent;
import owo.pigeon.events.networkevent.S08PacketPlayerPosLookEventEnd;
import owo.pigeon.events.playerevent.SetRotateEvent;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.settings.EnableSetting;

import static owo.pigeon.utils.WorldUtil.isNotNull;

public class RotateNotice extends Module {
    public RotateNotice() {
        super("RotateNotice", Category.HYPIXEL, -1);
    }

    public EnableSetting disableMacro = setting("autodisable",true,"",v->true);

    private int tick = 0;
    private float yaw,pitch;
    private boolean isRotate = false;

    @SubscribeEvent
    public void S08PacketPlayerPosLookPacketReceive (S08PacketPlayerPosLookEvent event) {
        if (isNotNull()) {
            yaw = mc.thePlayer.rotationYaw;
            pitch = mc.thePlayer.rotationPitch;
        }
    }

    @SubscribeEvent
    public void S08PacketPlayerPosLookPacketReceive (S08PacketPlayerPosLookEventEnd event) {
        if (isNotNull()) {
            if (yaw != event.getYaw() || pitch != event.getPitch()) {
            /*ChatUtil.sendMessage(yaw + " | " + pitch);
            ChatUtil.sendMessage(event.getYaw() + " | " + event.getPitch());*/
                isRotate = true;
                MinecraftForge.EVENT_BUS.post(new SetRotateEvent());
            }
        }
    }

    @Override
    public void onUpdate() {
        if (isRotate) {
            tick++;
            if (tick < 60) {
                // mc.theWorld.playSoundAtPos(mc.thePlayer.playerLocation, "note.pling", 1,5,false);

                Minecraft.getMinecraft().getSoundHandler().playSound(new PositionedSound(new ResourceLocation("note.pling")) {{
                    volume = 0.5F;
                    pitch = 1F;
                    repeat = false;
                    repeatDelay = 0;
                    attenuationType =AttenuationType.NONE;
                }});
            } else {
                tick = 0;
                isRotate = false;
            }
        }
    }
}
