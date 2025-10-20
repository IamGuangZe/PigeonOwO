package owo.pigeon.features.modules.render;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.settings.ModeSetting;
import owo.pigeon.utils.RenderUtil;

import java.awt.*;

public class ESP extends Module {
    public ESP() {
        super("ESP", Category.RENDER,-1);
    }

    public enum modeEnum {
        BOX, OUTLINE, MINECRAFT
    }

    public ModeSetting<modeEnum> mode = setting("mode",modeEnum.OUTLINE,"",v->true);

    public Color color = new Color(255,255,255,95);

    @Override
    public void onRender3D() {
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (!(entity instanceof EntityPlayerSP)) {
                switch (mode.getValue()) {
                    case BOX:
                        RenderUtil.drawSmoothFullBoxEsp(entity, color);
                        break;

                    case OUTLINE:
                        RenderUtil.drawSmoothOutlinedBoxEsp(entity,color);
                        break;

                    case MINECRAFT:
                        RenderUtil.drawEntityBox(entity,color,2.0F,false);
                        break;
                }
            }
        }
    }
}
