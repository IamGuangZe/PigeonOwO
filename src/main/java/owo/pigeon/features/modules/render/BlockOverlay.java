package owo.pigeon.features.modules.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import owo.pigeon.features.Category;
import owo.pigeon.features.Module;
import owo.pigeon.utils.RenderUtil;

import java.awt.*;

public class BlockOverlay extends Module {
    public BlockOverlay() {
        super("BlockOverlay", Category.RENDER, -1);
    }

    public Color color = new Color(0, 0, 0, 66);

    @SubscribeEvent
    public void onRenderBlockOverlay(DrawBlockHighlightEvent event) {
        if (event.target.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
            return;
        }

        event.setCanceled(true);

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);

        RenderUtil.drawFullBoxA(RenderUtil.getAxisAlignedBBWithOffset(event.target.getBlockPos()), color);

        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
