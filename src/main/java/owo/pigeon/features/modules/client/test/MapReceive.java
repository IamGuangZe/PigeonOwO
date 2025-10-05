package owo.pigeon.features.modules.client.test;

import net.minecraft.item.ItemMap;
import net.minecraft.network.play.server.S34PacketMaps;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import owo.pigeon.events.networkevent.PacketReceiveEvent;
import owo.pigeon.features.Category;
import owo.pigeon.features.Module;
import owo.pigeon.utils.ChatUtil;

public class MapReceive extends Module {

    public MapReceive() {
        super("MapReceive", Category.CLIENT, -1);
    }

    @SubscribeEvent
    public void onPacket(PacketReceiveEvent event) {
        if (!(event.getPacket() instanceof S34PacketMaps))
            return;

        S34PacketMaps packet = (S34PacketMaps) event.getPacket();
        int mapId = packet.getMapId();

        // 获取 MapData
        MapData mapData = ItemMap.loadMapData(mapId, mc.theWorld);

        // 将 packet 数据写入 MapData
        packet.setMapdataTo(mapData);

        // 读取 MapData 信息
        byte[] colors = mapData.colors;
        int scale = mapData.scale;
        int playerCount = mapData.mapDecorations.size();

        ChatUtil.sendMessage("§a[Map Info]");
        ChatUtil.sendMessage("§7  ID: §f" + mapId);
        ChatUtil.sendMessage("§7  Scale: §f" + scale);
        ChatUtil.sendMessage("§7  Players Tracked: §f" + playerCount);
        ChatUtil.sendMessage("§7  Colors: §f" + (colors != null ? colors.length : 0));

        // 预览前几个像素
        if (colors != null && colors.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < Math.min(8, colors.length); i++) {
                sb.append(String.format("%02X ", colors[i]));
            }
            ChatUtil.sendMessage("§7  Data Preview: §f" + sb.toString().trim() + (colors.length > 8 ? " ..." : ""));
        }
    }
}
