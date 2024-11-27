package owo.pigeon.features.hypixel;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import owo.pigeon.modules.Module;
import owo.pigeon.utils.CheckUtil;

public class GhostBlock extends Module {
    public GhostBlock() {
        super("GhostBlock", Keyboard.KEY_H);
    }

    public void onUpdate() {
        if (CheckUtil.NotnullCheck()) {
            if (mc.objectMouseOver.entityHit == null && mc.currentScreen == null &&
                    (
                            Keyboard.isKeyDown(Keyboard.KEY_G) ||
                                    Mouse.isButtonDown(1) &&
                                            mc.thePlayer.getHeldItem() != null &&
                                            mc.thePlayer.getHeldItem().getItem() instanceof ItemPickaxe
                    )
            ) {
                MovingObjectPosition position = mc.thePlayer.rayTrace(6.0D, 0.0F);
                if (mc.thePlayer.worldObj.getBlockState(position.getBlockPos()).getBlock() != Blocks.chest &&
                        mc.thePlayer.worldObj.getBlockState(position.getBlockPos()).getBlock() != Blocks.stone_button &&
                        mc.thePlayer.worldObj.getBlockState(position.getBlockPos()).getBlock() != Blocks.wooden_button &&
                        mc.thePlayer.worldObj.getBlockState(position.getBlockPos()).getBlock() != Blocks.skull &&
                        mc.thePlayer.worldObj.getBlockState(position.getBlockPos()).getBlock() != Blocks.lever &&
                        mc.thePlayer.worldObj.getBlockState(position.getBlockPos()).getBlock() != Blocks.command_block) {
                    mc.thePlayer.worldObj.setBlockToAir(position.getBlockPos());
                }
            }
        }
    }
}
