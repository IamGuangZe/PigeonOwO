package owo.pigeon.features.modules.render;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import owo.pigeon.features.Category;
import owo.pigeon.features.Module;
import owo.pigeon.settings.IntSetting;
import owo.pigeon.utils.RenderUtil;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class BedESP extends Module {
    public BedESP() {
        super("BedEsp", Category.RENDER, -1);
    }

    public IntSetting range = setting("Range", 64, 0, 64, "", v -> true);
    public Color color = new Color(255, 0, 0);

    private final Set<BlockPos> beds = new HashSet<>();
    private Thread scanThread;  // 扫描线程

    @Override
    public void onEnable() {
        super.onEnable();
        synchronized (beds) {
            beds.clear(); // 开启时清空缓存
        }

        scanThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted() && this.isEnable()) {
                rescan();
                try {
                    Thread.sleep(500); // 每 500ms 扫描一次
                } catch (InterruptedException e) {
                    break; // 被打断时退出
                }
            }
        }, "BedEsp-Scanner");
        scanThread.start();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (scanThread != null && scanThread.isAlive()) {
            scanThread.interrupt(); // 停止线程
            scanThread = null;
        }
        synchronized (beds) {
            beds.clear(); // 关闭时清空缓存
        }
    }

    private void rescan() {
        Set<BlockPos> newBeds = new HashSet<>();
        BlockPos playerLocation = mc.thePlayer.getPosition();
        BlockPos minPos = playerLocation.add(-range.getValue(), -range.getValue(), -range.getValue());
        BlockPos maxPos = playerLocation.add(range.getValue(), range.getValue(), range.getValue());

        for (int x = minPos.getX(); x <= maxPos.getX(); x++) {
            for (int y = minPos.getY(); y <= maxPos.getY(); y++) {
                for (int z = minPos.getZ(); z <= maxPos.getZ(); z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    Block block = mc.theWorld.getBlockState(pos).getBlock();
                    if (block instanceof BlockBed) {
                        newBeds.add(pos);
                    }
                }
            }
        }

        synchronized (beds) {
            beds.clear();
            beds.addAll(newBeds);
        }
    }

    @Override
    public void onRender3D() {
        synchronized (beds) {
            for (BlockPos pos : beds) {
                RenderUtil.drawOutlinedBoxEsp(pos, color);
            }
        }
    }

    /*@Override
    public void onRender3D() {
        BlockPos playerLocation = mc.thePlayer.getPosition();
        BlockPos minPos = playerLocation.add(-range.getValue(), -range.getValue(), -range.getValue());
        BlockPos maxPos = playerLocation.add(range.getValue(), range.getValue(), range.getValue());

        for (int x = minPos.getX(); x <= maxPos.getX(); x++) {
            for (int y = minPos.getY(); y <= maxPos.getY(); y++) {
                for (int z = minPos.getZ(); z <= maxPos.getZ(); z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    Block block = mc.theWorld.getBlockState(pos).getBlock();

                    if (block instanceof BlockBed) {
                        RenderUtil.drawOutlinedBoxEsp(pos,color);
                    }
                }
            }
        }
    }*/
}
