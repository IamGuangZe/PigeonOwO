package owo.pigeon.features.modules.world;

import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import owo.pigeon.events.networkevent.PacketReceiveEvent;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.utils.ChatUtil;

public class LightningTracker extends Module {
    public LightningTracker() {
        super("LightningTracker", Category.WORLD, -1);
    }

    @SubscribeEvent
    public void onPacket(PacketReceiveEvent event) {
        if (event.getPacket() instanceof S2CPacketSpawnGlobalEntity) {
            S2CPacketSpawnGlobalEntity packet = (S2CPacketSpawnGlobalEntity) event.getPacket();

            if (packet.func_149053_g() == 1) {
                double x = (double) packet.func_149051_d() / 32.0;
                double y = (double) packet.func_149050_e() / 32.0;
                double z = (double) packet.func_149049_f() / 32.0;
                double distance = mc.thePlayer.getDistance(x, y, z);

                ChatUtil.sendCustomPrefixMessage(this.name,"&r&7X: &6&l" + (int)x + " &r&7Y: &6&l" + (int)y + " &r&7Z: &6&l" + (int)z + " &r&7Distance: &6&l" + (int)distance);
            }
        }
    }
}