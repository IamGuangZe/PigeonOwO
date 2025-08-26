package owo.pigeon.features.modules.hypixel.Skyblock;

import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import owo.pigeon.events.networkevent.PacketReceiveEvent;
import owo.pigeon.events.playerevent.SetRotateEvent;
import owo.pigeon.features.Category;
import owo.pigeon.features.Module;
import owo.pigeon.settings.EnableSetting;
import owo.pigeon.utils.ModuleUtil;

public class AutoDisable extends Module {
    public AutoDisable() {
        super("AutoDisable", Category.HYPIXEL, -1);
    }

    public EnableSetting onWorldLoad = setting("World load",true,"",v->true);
    public EnableSetting onS01PacketJoinGame = setting("S01PacketJoinGame",true,"",v->true);
    public EnableSetting onS08PacketPlayerPosLook = setting("S08PacketPlayerPosLook",false,"",v->true);
    public EnableSetting onRotate = setting("Rotate",true,"",v->true);

    @Override
    public void onWorldLoad() {
        if (onWorldLoad.getValue()) {
            ModuleUtil.macroDisable();
        }
    }

    @SubscribeEvent
    public void onPacketReceive(PacketReceiveEvent event){
        if ((event.getPacket() instanceof S01PacketJoinGame && onS01PacketJoinGame.getValue()) ||
                (event.getPacket() instanceof S08PacketPlayerPosLook && onS08PacketPlayerPosLook.getValue())
        ) {
            ModuleUtil.macroDisable();
        }
    }

    @SubscribeEvent
    public void onRotate(SetRotateEvent event) {
        if (onRotate.getValue()) {
            ModuleUtil.macroDisable();
        }
    }
}
