package owo.pigeon.features.modules.render;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.utils.RenderUtil;
import owo.pigeon.utils.hypixel.HypixelUtil;

import java.awt.*;

public class PlayerESP extends Module {
    public PlayerESP() {
        super("PlayerESP", Category.RENDER, -1);
    }

    @Override
    public void onRender3D() {
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityPlayer && !(entity instanceof EntityPlayerSP)) {
                if (!HypixelUtil.isNPC(entity)) {
                    RenderUtil.drawSmoothFullBoxEsp(entity,new Color(255,255,255, 95));
                }
            }
        }
    }
}
