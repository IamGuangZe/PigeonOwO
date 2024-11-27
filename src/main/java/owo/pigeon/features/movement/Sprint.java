package owo.pigeon.features.movement;

import org.lwjgl.input.Keyboard;
import owo.pigeon.modules.Module;
import owo.pigeon.utils.CheckUtil;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", Keyboard.KEY_I);
    }

    public void onUpdate() {
        if (CheckUtil.NotnullCheck()){
            if (mc.thePlayer.moveForward > 0 &&
                    (
                     mc.thePlayer.getFoodStats().getFoodLevel() > 6 ||
                     mc.thePlayer.capabilities.isCreativeMode ||
                     mc.thePlayer.capabilities.isFlying //没找到有是否为观察者模式的值
                    ) &&
                    !mc.thePlayer.isSneaking() &&
                    !mc.thePlayer.isBlocking() &&
                    !mc.thePlayer.isUsingItem() &&
                    !mc.thePlayer.isCollidedHorizontally
            ) {
                mc.thePlayer.setSprinting(true);
            }
        }
    }
}
