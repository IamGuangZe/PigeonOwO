package owo.pigeon.features.modules.player;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.settings.EnableSetting;
import owo.pigeon.utils.WorldUtil;

public class Eagle extends Module {
    public Eagle() {
        super("Eagle", Category.PLAYER,-1);
    }

    public EnableSetting onlyBackward = setting("onlybackward",true,"Only trigger when moving backward.",v->true);

    @Override
    public void onUpdate() {
        if (WorldUtil.isNotNull()) {
            if (!Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
                if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ)).getBlock() == Blocks.air &&
                        mc.thePlayer.onGround &&
                        mc.currentScreen == null &&
                        (onlyBackward.getValue() && Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode()))
                ) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                } else {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
                }
            }
        }
    }

    @Override
    public void onDisable() {
        if (!Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())){
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
        }
    }
}
