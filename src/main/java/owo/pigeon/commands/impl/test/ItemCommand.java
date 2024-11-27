package owo.pigeon.commands.impl.test;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import owo.pigeon.commands.Command;
import owo.pigeon.utils.ChatUtil;

import static owo.pigeon.modules.Module.mc;

public class ItemCommand extends Command {
    public ItemCommand() {
        super("Item");
    }

    @Override
    public void execute(String[] args) {
        int solt;
        if(args.length == 0) {
            solt = mc.thePlayer.inventory.currentItem;
        } else {
            solt = Integer.parseInt(args[0]);
        }
        if (solt < 0 || solt > 8) {
            ChatUtil.sendMessage("&cSlot must between 0 and 8!");
            return;
        }
        ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(solt);
        if (itemStack == null) {
            ChatUtil.sendMessage("There is no item in this slot!");
            return;
        }
        ChatUtil.sendMessage(String.valueOf(itemStack));
        ChatUtil.sendMessage(String.valueOf(itemStack.getItem()));
        ChatUtil.sendMessage(String.valueOf(itemStack.getDisplayName()));
        ChatUtil.sendMessage(String.valueOf(EnchantmentHelper.getEnchantments(itemStack)));
    }
}
