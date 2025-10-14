package owo.pigeon.features.modules.hypixel.Skyblock.Dungeon;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import owo.pigeon.events.clickevent.LeftClickEvent;
import owo.pigeon.events.clickevent.RightClickEvent;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.settings.BlockSetting;
import owo.pigeon.utils.ChatUtil;

import static owo.pigeon.utils.WorldUtil.setBlock;

public class DungeonBrush extends Module {

    public DungeonBrush() {
        super("[WIP]DungeonBrush", Category.HYPIXEL, -1);
    }

    public BlockSetting block = setting("block", Blocks.stained_glass, "" ,v->false);

    private boolean isFirstUse = true;

    @Override
    public void onEnable() {
        if (isFirstUse) {
            usageMessage();
            isFirstUse = false;
        }
    }

    @SubscribeEvent
    public void onLeftClick(LeftClickEvent event) {
        if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() == Items.stick) {
            if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                if (mc.thePlayer.isSneaking()) {
                    BlockPos blockPos = mc.objectMouseOver.getBlockPos();
                    mc.theWorld.markBlockRangeForRenderUpdate(blockPos, blockPos);
                } else {
                    BlockPos blockPos = mc.objectMouseOver.getBlockPos();
                    setBlock(blockPos, Blocks.air);
                }
            }
        }
    }

    @SubscribeEvent
    public void onRightClick(RightClickEvent event) {
        if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() == Items.stick) {
            if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                if (mc.thePlayer.isSneaking()) {
                    BlockPos blockPos = mc.objectMouseOver.getBlockPos();
                    setBlock(blockPos, block.getValue());
                } else {
                    BlockPos blockPos = mc.objectMouseOver.getBlockPos().offset(mc.objectMouseOver.sideHit);
                    setBlock(blockPos, block.getValue());
                }
            }
        }
    }

    public static void usageMessage() {
        ChatUtil.sendCustomPrefixMessage("DungeonBrush","&b===Brush===");
        ChatUtil.sendCustomPrefixMessage("DungeonBrush","&b.brush -> Toggle brush.");
        ChatUtil.sendCustomPrefixMessage("DungeonBrush","&b.brush help -> Show this Message.");
        ChatUtil.sendCustomPrefixMessage("DungeonBrush","&b.brush setblock <Block> -> Switch the created block.");
        ChatUtil.sendCustomPrefixMessage("DungeonBrush","&b==How to use===");
        ChatUtil.sendCustomPrefixMessage("DungeonBrush","&bHolding a stick");
        ChatUtil.sendCustomPrefixMessage("DungeonBrush","&bLeft Click -> Remove block(set air)");
        ChatUtil.sendCustomPrefixMessage("DungeonBrush","&bRight Click -> Create Block");
        ChatUtil.sendCustomPrefixMessage("DungeonBrush","&bSneak + Right Click -> Replace Block");
    }
}
