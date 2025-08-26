package owo.pigeon.features.modules.render;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import owo.pigeon.features.Category;
import owo.pigeon.features.Module;
import owo.pigeon.utils.RenderUtil;

import java.awt.*;

public class ESP extends Module {
    public ESP() {
        super("ESP", Category.RENDER,-1);
    }

    @Override
    public void onRender3D() {
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (!(entity instanceof EntityPlayerSP)) {
                if (entity instanceof EntityArmorStand) {
                    RenderUtil.drawOutlinedBoxEsp(entity,new Color(255, 60,0));
                } else {
                    RenderUtil.drawFullBoxEsp(entity,new Color(255,255,255, 95));
                }
            }
        }
    }
}
