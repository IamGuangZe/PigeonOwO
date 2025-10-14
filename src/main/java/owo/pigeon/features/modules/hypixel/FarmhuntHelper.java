package owo.pigeon.features.modules.hypixel;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.Vec3;
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

public class FarmhuntHelper extends Module {
    public FarmhuntHelper() {
        super("FarmHuntHelper", Category.HYPIXEL, -1);
    }

    private static class AnimalState {
        Vec3 pos;
        float yaw;
        float pitch;

        AnimalState(Vec3 pos, float yaw, float pitch) {
            this.pos = pos;
            this.yaw = yaw;
            this.pitch = pitch;
        }
    }

    private final Map<Integer, AnimalState> trackedAnimals = new HashMap<>();
    private final Set<Integer> movedAnimals = new HashSet<>();
    private final Color animalColor = new Color(255, 0, 0);
    private boolean tracking = false;

    @Override
    public void onUpdate() {
        if (WorldUtil.isNotNull()) {
            if (!tracking) {
                if (BossStatus.statusBarTime > 0) {
                    if (OtherUtil.isContainsRegex("Hunters releasing in (?:[0-9]|1[0-9]|2[0-5]) seconds", OtherUtil.removeColor(BossStatus.bossName)) ||
                            BossStatus.bossName.contains("Time remaining")) {
                        tracking = true;
                    }
                }
            } else {
                for (Entity entity : mc.theWorld.loadedEntityList) {
                    if (entity instanceof EntityAnimal) {
                        int id = entity.getEntityId();

                        if (!movedAnimals.contains(id)) {
                            AnimalState startState = trackedAnimals.get(id);

                            if (startState == null) {
                                // 记录初始位置和角度
                                trackedAnimals.put(id, new AnimalState(
                                        new Vec3(entity.posX, entity.posY, entity.posZ),
                                        entity.rotationYaw,
                                        entity.rotationPitch
                                ));
                            } else if (hasMovedOrRotated(startState, entity)) {
                                movedAnimals.add(id);
                                trackedAnimals.remove(id);
                            }
                        }
                    }
                }
            }
        }
    }


    @Override
    public void onRender3D() {
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityAnimal && movedAnimals.contains(entity.getEntityId())) {
                RenderUtil.drawOutlinedBoxEsp(entity, animalColor);
            }
        }
    }

    @Override
    public void onWorldLoad() {
        trackedAnimals.clear();
        movedAnimals.clear();
        tracking = false;
    }

    private boolean hasMovedOrRotated(AnimalState startState, Entity entity) {
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
