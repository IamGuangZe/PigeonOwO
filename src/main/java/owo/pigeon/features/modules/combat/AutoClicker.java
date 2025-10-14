package owo.pigeon.features.modules.combat;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import org.lwjgl.input.Mouse;
import owo.pigeon.features.Category;
import owo.pigeon.features.Module;
import owo.pigeon.settings.EnableSetting;
import owo.pigeon.settings.IntSetting;
import owo.pigeon.utils.PlayerUtil;

import static owo.pigeon.utils.OtherUtil.intRandom;

public class AutoClicker extends Module {

    public AutoClicker() {
        super("AutoClicker", Category.COMBAT, -1);
    }

    // 写个AutoClicker能写2天也是神人了 - 2024.11.22
    // 历经11个月才发现有一处拼写错误 - 2025.10.9

    public IntSetting minCPS = setting("mincps",12,1,20,"",v -> true);
    public IntSetting maxCPS = setting("maxcps",18,1,20,"",v -> true);
    public EnableSetting leftClick = setting("leftclick",true,"Enable Left Click", v -> true);
    public EnableSetting rightClick = setting("rightclick",true,"Enable Right Click", v -> true);
    public EnableSetting onlySword = setting("onlysword",false,"",v->true);

    private boolean firstLeftClick = true;
    private boolean firstRightClick = true;
    private long lastLeftClickTime = 0;  // 上次点击时间
    private long lastRightClickTime = 0;

    @Override
    public void onUpdate() {

        long clickInterval = 1000 / intRandom(minCPS.getValue(),maxCPS.getValue()); // 每次点击间隔时间（毫秒）
        long currentTime = System.currentTimeMillis(); // 当前时间

        if (Mouse.isButtonDown(0) && leftClick.getValue()) {
            if (canClick() &&
                !PlayerUtil.isBreakingBlock() &&
                    onlySwordCheck() &&
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
            if (canClick() &&
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

    private boolean canClick() {
        return mc.currentScreen == null && !mc.thePlayer.isBlocking() && !mc.thePlayer.isUsingItem();
    }

    private boolean onlySwordCheck() {
        if (!onlySword.getValue()) {
            return true;
        }

        ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(mc.thePlayer.inventory.currentItem);
        if (itemStack == null) {
            return false;
        }

        return (itemStack.getItem() instanceof ItemSword);
    }
}

