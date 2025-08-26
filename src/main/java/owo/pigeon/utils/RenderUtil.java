package owo.pigeon.utils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import java.awt.*;

import static owo.pigeon.features.Module.mc;

public class RenderUtil {
    static RenderManager renderManager = mc.getRenderManager();

    private static void wallrender (boolean enable) {
        if (enable) {
            GlStateManager.disableDepth();
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
        } else {
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
        }
    }
    private static void wallrender (boolean enable, int alpha) {
        if (enable) {
            GlStateManager.disableDepth();
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            if (alpha < 255) {
                GlStateManager.enableBlend();
            }
        } else {
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
        }
    }

    public static AxisAlignedBB getAxisAlignedBBWithOffset(AxisAlignedBB axisAlignedBB) {
        return axisAlignedBB.offset(-renderManager.viewerPosX, -renderManager.viewerPosY, -renderManager.viewerPosZ);
    }
    public static AxisAlignedBB getAxisAlignedBBWithOffset(Entity entity) {
        return entity.getEntityBoundingBox().offset(-renderManager.viewerPosX, -renderManager.viewerPosY, -renderManager.viewerPosZ);
    }
    public static AxisAlignedBB getAxisAlignedBBWithOffset(BlockPos blockPos) {
        return mc.theWorld.getBlockState(blockPos).getBlock().getSelectedBoundingBox(mc.theWorld, blockPos).offset(-renderManager.viewerPosX, -renderManager.viewerPosY, -renderManager.viewerPosZ).expand(0.0020000000949949026, 0.0020000000949949026, 0.0020000000949949026);
    }


    public static void drawOutlinedBox(AxisAlignedBB box, Color c) {
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        int a = c.getAlpha();

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(box.minX, box.minY, box.minZ).color(r,g,b,a).endVertex();
        worldrenderer.pos(box.maxX, box.minY, box.minZ).color(r,g,b,a).endVertex();
        worldrenderer.pos(box.maxX, box.minY, box.maxZ).color(r,g,b,a).endVertex();
        worldrenderer.pos(box.minX, box.minY, box.maxZ).color(r,g,b,a).endVertex();
        worldrenderer.pos(box.minX, box.minY, box.minZ).color(r,g,b,a).endVertex();
        tessellator.draw();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(box.minX, box.maxY, box.minZ).color(r,g,b,a).endVertex();
        worldrenderer.pos(box.maxX, box.maxY, box.minZ).color(r,g,b,a).endVertex();
        worldrenderer.pos(box.maxX, box.maxY, box.maxZ).color(r,g,b,a).endVertex();
        worldrenderer.pos(box.minX, box.maxY, box.maxZ).color(r,g,b,a).endVertex();
        worldrenderer.pos(box.minX, box.maxY, box.minZ).color(r,g,b,a).endVertex();
        tessellator.draw();
        worldrenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(box.minX, box.minY, box.minZ).color(r,g,b,a).endVertex();
        worldrenderer.pos(box.minX, box.maxY, box.minZ).color(r,g,b,a).endVertex();
        worldrenderer.pos(box.maxX, box.minY, box.minZ).color(r,g,b,a).endVertex();
        worldrenderer.pos(box.maxX, box.maxY, box.minZ).color(r,g,b,a).endVertex();
        worldrenderer.pos(box.maxX, box.minY, box.maxZ).color(r,g,b,a).endVertex();
        worldrenderer.pos(box.maxX, box.maxY, box.maxZ).color(r,g,b,a).endVertex();
        worldrenderer.pos(box.minX, box.minY, box.maxZ).color(r,g,b,a).endVertex();
        worldrenderer.pos(box.minX, box.maxY, box.maxZ).color(r,g,b,a).endVertex();
        tessellator.draw();
    }
    public static void drawOutlinedBoxA(AxisAlignedBB box, Color c) {
        GlStateManager.color((float) c.getRed() / 255.0F, (float) c.getGreen() / 255.0F, (float) c.getBlue() / 255.0F, (float) c.getAlpha() / 255.0F);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION);
        worldrenderer.pos(box.minX, box.minY, box.minZ).endVertex();
        worldrenderer.pos(box.maxX, box.minY, box.minZ).endVertex();
        worldrenderer.pos(box.maxX, box.minY, box.maxZ).endVertex();
        worldrenderer.pos(box.minX, box.minY, box.maxZ).endVertex();
        worldrenderer.pos(box.minX, box.minY, box.minZ).endVertex();
        tessellator.draw();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION);
        worldrenderer.pos(box.minX, box.maxY, box.minZ).endVertex();
        worldrenderer.pos(box.maxX, box.maxY, box.minZ).endVertex();
        worldrenderer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
        worldrenderer.pos(box.minX, box.maxY, box.maxZ).endVertex();
        worldrenderer.pos(box.minX, box.maxY, box.minZ).endVertex();
        tessellator.draw();
        worldrenderer.begin(1, DefaultVertexFormats.POSITION);
        worldrenderer.pos(box.minX, box.minY, box.minZ).endVertex();
        worldrenderer.pos(box.minX, box.maxY, box.minZ).endVertex();
        worldrenderer.pos(box.maxX, box.minY, box.minZ).endVertex();
        worldrenderer.pos(box.maxX, box.maxY, box.minZ).endVertex();
        worldrenderer.pos(box.maxX, box.minY, box.maxZ).endVertex();
        worldrenderer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
        worldrenderer.pos(box.minX, box.minY, box.maxZ).endVertex();
        worldrenderer.pos(box.minX, box.maxY, box.maxZ).endVertex();
        tessellator.draw();
    }
    public static void drawOutlinedBoxEsp(AxisAlignedBB box, Color c) {
        AxisAlignedBB boxa = getAxisAlignedBBWithOffset(box);
        wallrender(true, c.getAlpha());
        drawOutlinedBox(boxa, c);
        wallrender(false,c.getAlpha());
    }
    public static void drawOutlinedBoxEsp(Entity entity, Color c) {
        AxisAlignedBB boxa = getAxisAlignedBBWithOffset(entity);
        wallrender(true, c.getAlpha());
        drawOutlinedBox(boxa, c);
        wallrender(false,c.getAlpha());
    }
    public static void drawOutlinedBoxEsp(BlockPos blockPos, Color c) {
        AxisAlignedBB boxa = getAxisAlignedBBWithOffset(blockPos);
        wallrender(true, c.getAlpha());
        drawOutlinedBox(boxa, c);
        wallrender(false, c.getAlpha());
    }


    public static void drawFullBox(AxisAlignedBB box,Color c) {
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        int a = c.getAlpha();

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(r,g,b,a).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(r,g,b,a).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(r,g,b,a).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(r,g,b,a).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(r,g,b,a).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(r,g,b,a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(r,g,b,a).endVertex();
        tessellator.draw();
    }
    public static void drawFullBoxA(AxisAlignedBB box,Color c) {
        GlStateManager.color((float) c.getRed() / 255.0F, (float) c.getGreen() / 255.0F, (float) c.getBlue() / 255.0F, (float) c.getAlpha() / 255.0F);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(box.minX, box.minY, box.minZ).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.minZ).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(box.minX, box.maxY, box.minZ).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(box.minX, box.minY, box.minZ).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.minZ).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(box.minX, box.minY, box.minZ).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.minZ).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).endVertex();
        tessellator.draw();
    }
    public static void drawFullBoxEsp(AxisAlignedBB box, Color c) {
        AxisAlignedBB boxa = getAxisAlignedBBWithOffset(box);
        wallrender(true, c.getAlpha());
        drawFullBox(boxa,c);
        wallrender(false, c.getAlpha());
    }
    public static void drawFullBoxEsp(Entity entity, Color c) {
        AxisAlignedBB boxa = getAxisAlignedBBWithOffset(entity);
        wallrender(true, c.getAlpha());
        drawFullBox(boxa, c);
        wallrender(false,c.getAlpha());
    }
    public static void drawFullBoxEsp(BlockPos blockPos, Color c){
        AxisAlignedBB box = getAxisAlignedBBWithOffset(blockPos);
        wallrender(true, c.getAlpha());
        drawFullBox(box, c);
        wallrender(false,c.getAlpha());
    }
}
