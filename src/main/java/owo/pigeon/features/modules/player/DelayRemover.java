package owo.pigeon.features.modules.player;

import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.settings.EnableSetting;

public class DelayRemover extends Module {
    public DelayRemover() {
        super("DelayRemover", Category.PLAYER, -1);
    }

    public EnableSetting noClickDelay = setting("no-clickdealy", true, v -> true);
    public EnableSetting noRightClickDelay = setting("no-rightclickdealy", true, v -> true);
    public EnableSetting noJumpDelay = setting("no-jumpdealy", true, v -> true);
}
