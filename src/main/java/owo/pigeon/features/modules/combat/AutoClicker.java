package owo.pigeon.features.modules.combat;

import net.minecraft.world.WorldSettings;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import owo.pigeon.features.Category;
import owo.pigeon.features.Module;
import owo.pigeon.settings.EnableSetting;
import owo.pigeon.settings.IntSetting;
import owo.pigeon.utils.PlayerUtil;

public class AutoClicker extends Module {

    public AutoClicker() {
        super("AutoClicker", Category.COMBAT, Keyboard.KEY_R);
    }

    // 写个AutoClicker能写两天也是神人了 - 2024.11.22
    public EnableSetting leftClick = setting("LeftClick",true,"Enable Left Click", v -> true);
    public EnableSetting rightClick = setting("RifhtClick",true,"Enable Right Click", v -> true);
    public IntSetting cps = setting("CPS",15,1,20,"click per second", v -> true);

    public boolean firstLeftClick = true;
    public boolean firstRightClick = true;
    public long lastLeftClickTime = 0;  // 上次点击时间
    public long lastRightClickTime = 0;

    @Override
    public void onUpdate() {

        long clickInterval = 1000 / cps.getValue(); // 每次点击间隔时间（毫秒）
        long currentTime = System.currentTimeMillis(); // 当前时间

        if (Mouse.isButtonDown(0) && leftClick.getValue()) {
            if (mc.currentScreen == null &&
                !mc.thePlayer.isBlocking() &&
                !mc.thePlayer.isUsingItem() &&
                !PlayerUtil.isBreakingBlock() &&
                currentTime - lastLeftClickTime >= clickInterval
            ) {
                if (firstLeftClick) {
                    firstLeftClick = false;
                } else {
                    PlayerUtil.leftClick();
                }
                lastLeftClickTime = currentTime;
            }
        } else {
            firstLeftClick = true;
        }

        if (Mouse.isButtonDown(1) && rightClick.getValue()) {
            if (mc.currentScreen == null &&
                    !mc.thePlayer.isBlocking() &&
                    !mc.thePlayer.isUsingItem() &&
                    currentTime - lastRightClickTime >= clickInterval
            ) {
                if (firstRightClick) {
                    firstRightClick = false;
                } else {
                    PlayerUtil.rightClick();
                }
                lastRightClickTime = currentTime;
            }
        }else {
            firstRightClick = true;
        }
    }
}

