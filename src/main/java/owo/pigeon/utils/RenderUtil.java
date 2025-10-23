package owo.pigeon.utils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;
import owo.pigeon.injections.mixins.IAccessorMinecraft;

import java.awt.*;

import static owo.pigeon.features.modules.Module.mc;

public class RenderUtil {
    static RenderManager renderManager = mc.getRenderManager();

    private static void wallrender(boolean enable) {
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

    private static void wallrender(boolean enable, int alpha) {
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
        worldrenderer.pos(box.minX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.maxX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.minX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.minX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        tessellator.draw();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(box.minX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.minX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        tessellator.draw();
        worldrenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(box.minX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.minX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.maxX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.minX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();
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
        wallrender(false, c.getAlpha());
    }

    public static void drawOutlinedBoxEsp(Entity entity, Color c) {
        AxisAlignedBB boxa = getAxisAlignedBBWithOffset(entity);
        wallrender(true, c.getAlpha());
        drawOutlinedBox(boxa, c);
        wallrender(false, c.getAlpha());
    }

    public static void drawOutlinedBoxEsp(BlockPos blockPos, Color c) {
        AxisAlignedBB boxa = getAxisAlignedBBWithOffset(blockPos);
        wallrender(true, c.getAlpha());
        drawOutlinedBox(boxa, c);
        wallrender(false, c.getAlpha());
    }

    public static void drawFullBox(AxisAlignedBB box, Color c) {
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        int a = c.getAlpha();

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, a).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, a).endVertex();
        tessellator.draw();
    }

    public static void drawFullBoxA(AxisAlignedBB box, Color c) {
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
        drawFullBox(boxa, c);
        wallrender(false, c.getAlpha());
    }

    public static void drawFullBoxEsp(Entity entity, Color c) {
        AxisAlignedBB boxa = getAxisAlignedBBWithOffset(entity);
        wallrender(true, c.getAlpha());
        drawFullBox(boxa, c);
        wallrender(false, c.getAlpha());
    }

    public static void drawFullBoxEsp(BlockPos blockPos, Color c) {
        AxisAlignedBB box = getAxisAlignedBBWithOffset(blockPos);
        wallrender(true, c.getAlpha());
        drawFullBox(box, c);
        wallrender(false, c.getAlpha());
    }

    public static AxisAlignedBB getInterpolatedBoundingBox(Entity entity) {
        return getInterpolatedBoundingBox(entity, 0.0);
    }

    public static AxisAlignedBB getInterpolatedBoundingBox(Entity entity, double expand) {
        net.minecraft.util.Timer timer = ((IAccessorMinecraft) mc).getTimer();
        float renderPartialTicks = timer.renderPartialTicks;

        // 位置插值计算
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * renderPartialTicks;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * renderPartialTicks;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * renderPartialTicks;

        AxisAlignedBB box = entity.getEntityBoundingBox();

        // 计算插值后的边界框
        return new AxisAlignedBB(
                box.minX - entity.posX + x - expand,
                box.minY - entity.posY + y,
                box.minZ - entity.posZ + z - expand,
                box.maxX - entity.posX + x + expand,
                box.maxY - entity.posY + y,
                box.maxZ - entity.posZ + z + expand
        ).offset(-renderManager.viewerPosX, -renderManager.viewerPosY, -renderManager.viewerPosZ);
    }

    // 使用Minecraft内置渲染方法的新方法
    public static void drawEntityBox(Entity entity, Color color, float lineWidth, boolean expand) {
        drawEntityBox(entity, color, lineWidth, expand ? entity.width / 3 : 0.0);
    }

    public static void drawEntityBox(Entity entity, Color color, float lineWidth, double expand) {
        AxisAlignedBB box = getInterpolatedBoundingBox(entity, expand);
        drawSelectionBoundingBox(box, color, lineWidth);
    }

    public static void drawSelectionBoundingBox(AxisAlignedBB box, Color color, float lineWidth) {
        GlStateManager.pushMatrix();

        // 设置渲染状态
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.blendFunc(770, 771);

        // 设置颜色
        float alpha = (float) color.getAlpha() / 255.0F;
        float red = (float) color.getRed() / 255.0F;
        float green = (float) color.getGreen() / 255.0F;
        float blue = (float) color.getBlue() / 255.0F;
        GlStateManager.color(red, green, blue, alpha);

        // 设置线宽
        GL11.glLineWidth(lineWidth);

        // 使用Minecraft内置方法绘制
        RenderGlobal.drawSelectionBoundingBox(box);

        // 恢复状态
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();

        GlStateManager.popMatrix();
    }

    public static void drawSmoothOutlinedBoxEsp(Entity entity, Color color) {
        AxisAlignedBB box = getInterpolatedBoundingBox(entity);
        wallrender(true, color.getAlpha());
        drawOutlinedBox(box, color);
        wallrender(false, color.getAlpha());
    }

    public static void drawSmoothOutlinedBoxEsp(AxisAlignedBB box, Color c, Entity entity) {
        if (entity == null) {
            drawOutlinedBoxEsp(box, c);
            return;
        }

        AxisAlignedBB interpolatedBox = getInterpolatedBoundingBox(entity);

        double width = box.maxX - box.minX;
        double height = box.maxY - box.minY;
        double depth = box.maxZ - box.minZ;

        AxisAlignedBB finalBox = new AxisAlignedBB(
                interpolatedBox.minX, interpolatedBox.minY, interpolatedBox.minZ,
                interpolatedBox.minX + width, interpolatedBox.minY + height, interpolatedBox.minZ + depth
        );

        wallrender(true, c.getAlpha());
        drawOutlinedBox(finalBox, c);
        wallrender(false, c.getAlpha());
    }

    public static void drawSmoothFullBoxEsp(Entity entity, Color color) {
        AxisAlignedBB box = getInterpolatedBoundingBox(entity);
        wallrender(true, color.getAlpha());
        drawFullBox(box, color);
        wallrender(false, color.getAlpha());
    }

    public static void drawSmoothCustomBoxEsp(Entity entity, AxisAlignedBB customBox, Color color) {
        // 使用访问器获取 timer
        net.minecraft.util.Timer timer = ((IAccessorMinecraft) mc).getTimer();
        float renderPartialTicks = timer.renderPartialTicks;

        // 位置插值计算
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * renderPartialTicks;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * renderPartialTicks;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * renderPartialTicks;

        // 直接计算偏移量并应用到插值位置
        double minXOffset = customBox.minX - entity.posX;
        double minYOffset = customBox.minY - entity.posY;
        double minZOffset = customBox.minZ - entity.posZ;
        double maxXOffset = customBox.maxX - entity.posX;
        double maxYOffset = customBox.maxY - entity.posY;
        double maxZOffset = customBox.maxZ - entity.posZ;

        // 创建插值后的边界框
        AxisAlignedBB interpolatedBox = new AxisAlignedBB(
                x + minXOffset, y + minYOffset, z + minZOffset,
                x + maxXOffset, y + maxYOffset, z + maxZOffset
        );

        // 应用视角偏移
        AxisAlignedBB finalBox = interpolatedBox.offset(
                -renderManager.viewerPosX,
                -renderManager.viewerPosY,
                -renderManager.viewerPosZ
        );

        wallrender(true, color.getAlpha());
        drawOutlinedBox(finalBox, color);
        wallrender(false, color.getAlpha());
    }
}
