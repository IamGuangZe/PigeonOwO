package owo.pigeon.features.modules.render;

import net.minecraft.block.BlockSkull;
import net.minecraft.util.BlockPos;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.utils.RenderUtil;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArraySet;

public class SkullESP extends Module {
    public SkullESP() {
        super("SkullESP", Category.RENDER, -1);
    }

    public Color color = new Color(255, 85, 85,65);
    public static CopyOnWriteArraySet<BlockPos> giftskull = new CopyOnWriteArraySet<>();

    @Override
    public void onRender3D() {
        for (BlockPos pos : giftskull) {
            if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockSkull) {
                RenderUtil.drawFullBoxEsp(pos, color);
            } else {
                giftskull.remove(pos);
            }
        }
    }


}
