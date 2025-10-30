package owo.pigeon.features.modules.world;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.settings.EnableSetting;
import owo.pigeon.settings.KeySetting;
import owo.pigeon.utils.WorldUtil;
import owo.pigeon.utils.hypixel.HypixelGames;
import owo.pigeon.utils.hypixel.HypixelUtil;

import java.util.Set;

public class GhostBlock extends Module {
    public GhostBlock() {
        super("GhostBlock", Category.WORLD, -1);
    }

    public EnableSetting onlySkyblock = setting("only-skyblock", true, v -> true);
    public EnableSetting onlyDungeon = setting("only-dungeon", true, v -> onlySkyblock.getValue());

    public EnableSetting createWithPickaxe = setting("create-with-pickaxe", false, v -> true);
    public EnableSetting onlyHoldPickaxe = setting("only-hold-pickaxe", false, v -> true);

    public EnableSetting createWithKeyDown = setting("create-with-keydown", true, v -> true);
    public KeySetting key = setting("create-key", Keyboard.KEY_G, v -> createWithKeyDown.getValue());

    private final Set<Block> blackListBlock = Sets.newHashSet(
            Blocks.stone_button,
            Blocks.wooden_button,
            Blocks.lever,
            Blocks.chest,
            Blocks.command_block,
            Blocks.skull
    );

    public void onTick() {
        if (WorldUtil.isNotNull()) {
            if (cancreateghostblock()) {
                BlockPos targetblockpos = mc.objectMouseOver.getBlockPos();
                Block targetblock = mc.thePlayer.worldObj.getBlockState(targetblockpos).getBlock();
                if (!blackListBlock.contains(targetblock)) {
                    mc.thePlayer.worldObj.setBlockToAir(targetblockpos);
                }
            }
        }
    }

    public boolean cancreateghostblock() {
        if (mc.objectMouseOver == null) {
            return false;
        }

        if (mc.objectMouseOver.entityHit != null || mc.currentScreen != null) {
            return false;
        }
        boolean isPickaxe;
        if (mc.thePlayer.getHeldItem() != null) {
            isPickaxe = mc.thePlayer.getHeldItem().getItem() instanceof ItemPickaxe;
        } else {
            isPickaxe = false;
        }
        boolean condition = (createWithPickaxe.getValue() && Mouse.isButtonDown(1) && isPickaxe) || (createWithKeyDown.getValue() && Keyboard.isKeyDown(key.getValue()));
        boolean isInSkyblock = onlySkyblock.getValue() && HypixelUtil.isInGame(HypixelGames.SKYBLOCK);
        return (!onlyHoldPickaxe.getValue() || isPickaxe) && condition;
    }
}
