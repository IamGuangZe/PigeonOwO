package owo.pigeon.features.combat;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import owo.pigeon.modules.Module;
import owo.pigeon.utils.CheckUtil;

public class AutoClicker extends Module {

    public AutoClicker() {
        super("AutoClicker", Keyboard.KEY_R);
    }

    // 写个AutoClicker能写两天也是神人了 - 2024.11.22

    private long lastLeftClickTime = 0;  // 上次点击时间
    private long lastRightClickTime = 0;
    private int Lcps = 10;
    private int Rcps = 15;
    boolean FirstLeftClick = true;
    boolean FirstRightClick = true;
    boolean LeftClick = false;
    boolean RightClick = true;

    @Override
    public void onUpdate() {

        long LeftclickInterval = 1000 / Lcps; // 每次点击间隔时间（毫秒）
        long RightclickInterval = 1000 / Rcps;
        long LeftcurrentTime = System.currentTimeMillis(); // 当前时间
        long RightcurrentTime = System.currentTimeMillis();

        if (Mouse.isButtonDown(0) && LeftClick) {
            if (mc.currentScreen == null &&
                !mc.thePlayer.isBlocking() &&
                !mc.thePlayer.isUsingItem() &&
                !CheckUtil.isBreakingBlock() &&
                LeftcurrentTime - lastLeftClickTime >= LeftclickInterval
            ) {
                if (FirstLeftClick) {
                    FirstLeftClick = false;
                } else {
                    LeftClick();
                }
                lastLeftClickTime = LeftcurrentTime;
            }
        } else {
            FirstLeftClick = true;
        }

        if (Mouse.isButtonDown(1) && RightClick) {
            if (mc.currentScreen == null &&
                    !mc.thePlayer.isBlocking() &&
                    !mc.thePlayer.isUsingItem() &&
                    RightcurrentTime - lastRightClickTime >= RightclickInterval
            ) {
                if (FirstRightClick) {
                    FirstRightClick = false;
                } else {
                    RightClick();
                }
                lastRightClickTime = RightcurrentTime;
            }
        }else {
            FirstRightClick = true;
        }
    }

    public void LeftClick() {
//        KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());

        mc.thePlayer.swingItem();
        if (mc.objectMouseOver.entityHit != null) {
            mc.playerController.attackEntity(mc.thePlayer, mc.objectMouseOver.entityHit);
        }

    }

    public void RightClick() {
        KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
    }
}

