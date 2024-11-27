package owo.pigeon.commands.impl.test;

import net.minecraft.item.ItemStack;
import owo.pigeon.commands.Command;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.PlayerUtil;

import static owo.pigeon.modules.Module.mc;

public class GetToolCommand extends Command {
    public GetToolCommand() {
        super("GetTool");
    }

    @Override
    public void execute(String[] args) {
        ItemStack tool = PlayerUtil.getBestToolA(mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock());
        ChatUtil.sendMessage("BestToolAt : " + PlayerUtil.getSlotfromItemStack(tool));
    }
}
