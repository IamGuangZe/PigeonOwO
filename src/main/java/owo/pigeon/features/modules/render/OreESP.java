package owo.pigeon.features.modules.render;

import net.minecraft.block.Block;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import owo.pigeon.events.networkevent.PacketReceiveEvent;
import owo.pigeon.features.Category;
import owo.pigeon.features.Module;
import owo.pigeon.settings.EnableSetting;
import owo.pigeon.settings.IntSetting;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.RenderUtil;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class OreESP extends Module {
    public OreESP() {
        super("OreESP", Category.RENDER, -1);
    }

    public EnableSetting uhcMode = setting("UHC Mode",true,"",v->true);
    public IntSetting range = setting("Range", 16, 0, 64, "Display range (X/Z)", v -> true);

    public Color coalColor = new Color(0, 0, 0);
    public Color ironColor = new Color(228, 163, 134);
    public Color goldColor = new Color(228, 228, 43);
    public Color diamondColor = new Color(22, 223, 223);
    public Color redstoneColor = new Color(184, 16, 16);
    public Color lapisColor = new Color(33, 33, 188);

    private final Set<BlockPos> coal = new HashSet<>();
    private final Set<BlockPos> iron = new HashSet<>();
    private final Set<BlockPos> gold = new HashSet<>();
    private final Set<BlockPos> diamond = new HashSet<>();
    private final Set<BlockPos> redstone = new HashSet<>();
    private final Set<BlockPos> lapis = new HashSet<>();

    @Override
    public void onWorldLoad() {
        clearAll();
        ChatUtil.sendCustomPrefixMessage(this.name,"World loaded, cleared ore cache.");
    }

    @SubscribeEvent
    public void onPacket(PacketReceiveEvent event) {
        Object packet = event.getPacket();

        if (packet instanceof S23PacketBlockChange) {
            handleS23Packet((S23PacketBlockChange) packet);
        } else if (packet instanceof S22PacketMultiBlockChange) {
            handleS22Packet((S22PacketMultiBlockChange) packet);
        }
    }

    private void handleS23Packet(S23PacketBlockChange packet) {
        BlockPos pos = packet.getBlockPosition();
        Block block = packet.getBlockState().getBlock();

        if (pos.getY() < 0 || pos.getY() > 64) return;

        // ChatUtil.sendCustomPrefixMessage(this.name,"§7[S23] " + pos + " -> " + Block.blockRegistry.getNameForObject(block));

        addBlock(block, pos);
    }

    private void handleS22Packet(S22PacketMultiBlockChange packet) {
        for (S22PacketMultiBlockChange.BlockUpdateData data : packet.getChangedBlocks()) {
            BlockPos pos = data.getPos();
            Block block = data.getBlockState().getBlock();

            if (pos.getY() < 0 || pos.getY() > 64) continue;

            // ChatUtil.sendCustomPrefixMessage(this.name,"§7[S22] " + pos + " -> " + Block.blockRegistry.getNameForObject(block));

            addBlock(block, pos);
        }
    }

    private void addBlock(Block block, BlockPos pos) {
        if (block.isAir(mc.theWorld, pos)) {
            removeFromAll(pos);
            return;
        }

        if (block == Block.getBlockFromName("coal_ore")) addTo(coal, pos);
        else if (block == Block.getBlockFromName("iron_ore")) addTo(iron, pos);
        else if (block == Block.getBlockFromName("gold_ore")) addTo(gold, pos);
        else if (block == Block.getBlockFromName("diamond_ore")) addTo(diamond, pos);
        else if (block == Block.getBlockFromName("redstone_ore")) addTo(redstone, pos);
        else if (block == Block.getBlockFromName("lapis_ore")) addTo(lapis, pos);
    }

    private void addTo(Set<BlockPos> set, BlockPos pos) {
        synchronized (set) {
            set.add(pos);
        }
    }

    private void removeFromAll(BlockPos pos) {
        synchronized (coal) { coal.remove(pos); }
        synchronized (iron) { iron.remove(pos); }
        synchronized (gold) { gold.remove(pos); }
        synchronized (diamond) { diamond.remove(pos); }
        synchronized (redstone) { redstone.remove(pos); }
        synchronized (lapis) { lapis.remove(pos); }
    }

    private void clearAll() {
        synchronized (coal) { coal.clear(); }
        synchronized (iron) { iron.clear(); }
        synchronized (gold) { gold.clear(); }
        synchronized (diamond) { diamond.clear(); }
        synchronized (redstone) { redstone.clear(); }
        synchronized (lapis) { lapis.clear(); }
    }

    @Override
    public void onRender3D() {
        if (mc.thePlayer == null) return;

        BlockPos playerPos = mc.thePlayer.getPosition();
        int r = range.getValue();

        synchronized (coal) { for (BlockPos pos : coal) if (isInRange(playerPos, pos, r)) RenderUtil.drawOutlinedBoxEsp(pos, coalColor); }
        synchronized (iron) { for (BlockPos pos : iron) if (isInRange(playerPos, pos, r)) RenderUtil.drawOutlinedBoxEsp(pos, ironColor); }
        synchronized (gold) { for (BlockPos pos : gold) if (isInRange(playerPos, pos, r)) RenderUtil.drawOutlinedBoxEsp(pos, goldColor); }
        synchronized (diamond) { for (BlockPos pos : diamond) if (isInRange(playerPos, pos, r)) RenderUtil.drawOutlinedBoxEsp(pos, diamondColor); }
        synchronized (redstone) { for (BlockPos pos : redstone) if (isInRange(playerPos, pos, r)) RenderUtil.drawOutlinedBoxEsp(pos, redstoneColor); }
        synchronized (lapis) { for (BlockPos pos : lapis) if (isInRange(playerPos, pos, r)) RenderUtil.drawOutlinedBoxEsp(pos, lapisColor); }
    }

    // 判断XZ平面是否在范围内
    private boolean isInRange(BlockPos playerPos, BlockPos orePos, int r) {
        int dx = Math.abs(playerPos.getX() - orePos.getX());
        int dz = Math.abs(playerPos.getZ() - orePos.getZ());
        return dx <= r && dz <= r;
    }
}
