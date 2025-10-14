package owo.pigeon.features.modules.render;

import net.minecraft.block.BlockBed;
import net.minecraft.util.BlockPos;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.utils.RenderUtil;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArraySet;

public class BedESP extends Module {
    public BedESP() {
        super("BedEsp", Category.RENDER, -1);
    }

    public static CopyOnWriteArraySet<BlockPos> beds = new CopyOnWriteArraySet<>();
    public Color color = new Color(255, 255, 255,65);

    @Override
    public void onEnable() {
        if (mc.renderGlobal != null) {
            mc.renderGlobal.loadRenderers();
        }
    }

    @Override
    public void onRender3D() {
        for (BlockPos pos : beds) {
            if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockBed) {
                RenderUtil.drawFullBoxEsp(pos, color);
                // ChatUtil.sendCustomPrefixMessage("DEBUG " + this.name,"RENDER BED - X: " + pos.getX() + " Y: " + pos.getY() + " Z: " + pos.getY());
            } else {
                beds.remove(pos);
                // ChatUtil.sendCustomPrefixMessage("DEBUG " + this.name,"REMOVE BED - X: " + pos.getX() + " Y: " + pos.getY() + " Z: " + pos.getY());
            }
        }
    }
}
