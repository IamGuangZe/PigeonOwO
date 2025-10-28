package owo.pigeon.features.modules.client.test;

import org.lwjgl.input.Keyboard;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.ItemUtil;
import owo.pigeon.utils.PlayerUtil;

import static owo.pigeon.utils.WorldUtil.isNotNull;

public class InstantUseItem extends Module {
    public InstantUseItem() {
        super("InstantUseItem", Category.CLIENT, -1);
    }

    int rawsolt;
    String itemname = "Void";
    int key = Keyboard.KEY_L;
    boolean iskeyup = true;

    @Override
    public void onTick() {
        if (isNotNull()) {
            if (Keyboard.isKeyDown(key)) {
                if (iskeyup) {
                    int slot = ItemUtil.getSlotfromItemname(itemname);
                    if (slot == -1) {
                        ChatUtil.sendCustomPrefixMessage(this.name, "Item not found");
                    } else {
                        PlayerUtil.instantUseItem(slot);
                    }
                    iskeyup = false;
                }
            } else {
                iskeyup = true;
            }
        }
    }
}
