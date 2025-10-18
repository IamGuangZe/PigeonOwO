package owo.pigeon.features.modules.movement;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import owo.pigeon.events.playerevent.StopSprintEvent;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.utils.WorldUtil;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", Category.MOVEMENT, -1);
    }

    public int sprintToggleTimer;

    @Override
    public void onTick() {
        if (WorldUtil.isNotNull()){
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);

            /*            if (sprintToggleTimer > 0) {
                --sprintToggleTimer;
            }
            if (mc.thePlayer.isUsingItem() && !mc.thePlayer.isRiding()) {
                sprintToggleTimer = 0;
            }
            boolean sneak = mc.thePlayer.movementInput.sneak || mc.thePlayer.isSneaking();
            float f = 0.8F;
            boolean flag2 = mc.thePlayer.movementInput.moveForward >= f;
            boolean flag3 = (float)mc.thePlayer.getFoodStats().getFoodLevel() > 6.0F || mc.thePlayer.capabilities.allowFlying;
            if (mc.thePlayer.onGround && !sneak && !flag2 && mc.thePlayer.movementInput.moveForward >= f && !mc.thePlayer.isSprinting() && flag3 && !mc.thePlayer.isUsingItem() && !mc.thePlayer.isPotionActive(Potion.blindness)) {
                if (sprintToggleTimer <= 0) {
                    sprintToggleTimer = 7;
                } else {
                    mc.thePlayer.setSprinting(true);
                }
            }

            if (!mc.thePlayer.isSprinting() && mc.thePlayer.movementInput.moveForward >= f && flag3 && !mc.thePlayer.isUsingItem() && !mc.thePlayer.isPotionActive(Potion.blindness)) {
                mc.thePlayer.setSprinting(true);
            }

            if (mc.thePlayer.isSprinting() && (mc.thePlayer.movementInput.moveForward < f || mc.thePlayer.isCollidedHorizontally || !flag3)) {
                mc.thePlayer.setSprinting(false);
            }*/

            /*if (mc.thePlayer.moveForward > 0 &&
                    (
                     mc.thePlayer.getFoodStats().getFoodLevel() > 6.0F ||
                     mc.thePlayer.capabilities.allowFlying
                    ) &&
                    !mc.thePlayer.isSneaking() &&
                    !mc.thePlayer.isBlocking() &&
                    !mc.thePlayer.isUsingItem() &&
                    !mc.thePlayer.isCollidedHorizontally &&
                    !mc.thePlayer.isPotionActive(Potion.blindness)
            ) {
                mc.thePlayer.setSprinting(true);
            } else {
                mc.thePlayer.setSprinting(false);
            }*/
        }
    }

    @SubscribeEvent
    public void stopSprint(StopSprintEvent event) {
        mc.thePlayer.setSprinting(false);
    }

    @Override
    public void onDisable() {
        if (!Keyboard.isKeyDown(mc.gameSettings.keyBindSprint.getKeyCode())){
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
        }
    }
}

