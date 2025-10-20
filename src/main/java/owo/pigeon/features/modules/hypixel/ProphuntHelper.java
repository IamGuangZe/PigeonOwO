package owo.pigeon.features.modules.hypixel;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.utils.OtherUtil;
import owo.pigeon.utils.RenderUtil;
import owo.pigeon.utils.WorldUtil;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ProphuntHelper extends Module {
    public ProphuntHelper() {
        super("ProphuntHelper", Category.HYPIXEL, -1);
    }

    private static class StandState {
        Vec3 pos;
        float yaw;
        float pitch;

        StandState(Vec3 pos, float yaw, float pitch) {
            this.pos = pos;
            this.yaw = yaw;
            this.pitch = pitch;
        }
    }

    private final Map<Integer, StandState> trackedStands = new HashMap<>();
    private final Set<Integer> movedStands = new HashSet<>();
    private final Color standColor = new Color(0, 255, 0);
    private boolean tracking = false;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatReceive(ClientChatReceivedEvent event) {
        if (this.isEnable()) {
            String message = OtherUtil.removeColor(event.message.getFormattedText());
            if (OtherUtil.isContainsRegex("The Seeker will be released in (?:[1-9]|10)s!", message)) {
                tracking = true;
            }
        }
    }

    @Override
    public void onTick() {
        if (tracking && WorldUtil.isNotNull()) {
            for (Entity entity : mc.theWorld.loadedEntityList) {
                if (entity instanceof EntityArmorStand) {
                    int id = entity.getEntityId();

                    if (!movedStands.contains(id)) {
                        StandState startState = trackedStands.get(id);

                        if (startState == null) {
                            // 记录初始位置和角度
                            trackedStands.put(id, new StandState(
                                    new Vec3(entity.posX, entity.posY, entity.posZ),
                                    entity.rotationYaw,
                                    entity.rotationPitch
                            ));
                        } else if (hasMovedOrRotated(startState, entity)) {
                            movedStands.add(id);
                            trackedStands.remove(id);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onRender3D() {
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityArmorStand && movedStands.contains(entity.getEntityId())) {
                RenderUtil.drawSmoothOutlinedBoxEsp(entity, standColor);
            }
        }
    }

    @Override
    public void onWorldLoad() {
        trackedStands.clear();
        movedStands.clear();
        tracking = false;
    }

    private boolean hasMovedOrRotated(StandState startState, Entity entity) {
        // 判断位置变化
        if (entity.posX != startState.pos.xCoord ||
                entity.posY != startState.pos.yCoord ||
                entity.posZ != startState.pos.zCoord) {
            return true;
        }

        // 判断角度变化
        if (entity.rotationYaw != startState.yaw || entity.rotationPitch != startState.pitch) {
            return true;
        }

        return false;
    }
}
