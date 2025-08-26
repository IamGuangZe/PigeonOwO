package owo.pigeon.features.modules.player;

import owo.pigeon.features.Category;
import owo.pigeon.features.Module;
import owo.pigeon.settings.EnableSetting;

public class DelayRemover extends Module {
    public DelayRemover() {
        super("DelayRemover", Category.PLAYER,-1);
    }

    public EnableSetting noClickDelay = setting("NoClickDealy",true,"Remove leftClickCounter",v->true);
    public EnableSetting noRightClickDelay = setting("NoRightClickDealy",true,"Remove rightClickDelayTimer",v->true);
    public EnableSetting noJumpDelay = setting("NoJumpDealy",true,"Remove leftClickCounter",v->true);
}
