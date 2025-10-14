package owo.pigeon.features.modules.hypixel;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.utils.RenderUtil;
import owo.pigeon.utils.WorldUtil;
import owo.pigeon.utils.hypixel.HypixelGames;
import owo.pigeon.utils.hypixel.HypixelUtil;

import java.awt.*;
import java.util.Objects;

public class PixelHelper extends Module {
    public PixelHelper() {
        super("PixelHelper", Category.HYPIXEL, -1);
    }

    String color;
    public Color espColor = new Color(255, 255, 255);

    @Override
    public void onUpdate() {
        if (WorldUtil.isNotNull()) {
            if (HypixelUtil.isInGame(HypixelGames.PIXELPARTY)) {
                ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(8);

                if (itemStack == null) {
                    color = null;
                    return;
                }

                if (itemStack.getItem() instanceof ItemBlock) {
                    ItemBlock itemBlock = (ItemBlock) itemStack.getItem();
                    Block block = itemBlock.getBlock();
                    IBlockState state = block.getStateFromMeta(itemStack.getMetadata());
                    color = WorldUtil.getBlockColor(state);
                }
            }
        }
    }

    @Override
    public void onRender3D() {
        if (HypixelUtil.isInGame(HypixelGames.PIXELPARTY)) {
            if (color != null) {
                for (int x = -32; x <= 32; x++) {
                    for (int z = -32; z<=32; z++) {
                        BlockPos pos = new BlockPos(x,0,z);
                        IBlockState state = mc.theWorld.getBlockState(pos);
                        if (Objects.equals(WorldUtil.getBlockColor(state), color)) {
                            RenderUtil.drawOutlinedBoxEsp(pos,espColor);
                        }
                    }
                }
            }
        }
    }
}
