package owo.pigeon.features.client.test;

import org.lwjgl.input.Keyboard;
import owo.pigeon.modules.Module;
import owo.pigeon.utils.ChatUtil;

import static owo.pigeon.utils.CheckUtil.NotnullCheck;

public class ClickEvent extends Module {
    public ClickEvent() {
        super("ClickEvent", Keyboard.KEY_L);
    }

    @Override
    public void onRightClick() {
        if (NotnullCheck()) {
            ChatUtil.sendMessage("Right click event triggered!");
        }
    }
}
