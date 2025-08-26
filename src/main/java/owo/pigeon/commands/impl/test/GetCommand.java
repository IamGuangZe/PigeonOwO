package owo.pigeon.commands.impl.test;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import owo.pigeon.commands.Command;
import owo.pigeon.utils.*;
import owo.pigeon.utils.hypixel.HypixelUtil;
import owo.pigeon.utils.hypixel.skyblock.SkyblockUtil;

import java.util.ArrayList;
import java.util.List;

import static owo.pigeon.features.Module.mc;

public class GetCommand extends Command {
    public GetCommand() {
        super("Get");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            ChatUtil.sendMessage("&cPlease enter the type!");
            return;
        }

        switch (args[0].toLowerCase()) {
            case "area" :
                ChatUtil.sendMessage(String.valueOf(SkyblockUtil.getIsland()));

                break;

            case "allarmorstandname":
            case "aasn":
                for (Entity entity : mc.theWorld.loadedEntityList) {
                    if (entity instanceof EntityArmorStand && entity.hasCustomName()) {
                        EntityArmorStand e = (EntityArmorStand) entity;
                        if (e.hasCustomName()) {
                            ChatUtil.sendMessageWithoutColor(OtherUtil.removeColorA(entity.getName()));
                        }
                    }
                }

                break;

            case "floor":
            case "f":
                ChatUtil.sendMessage(String.valueOf(SkyblockUtil.getFloor()));

                break;

            case "scoreboard":
            case "s":
                Scoreboard scoreboard = mc.theWorld.getScoreboard();
                ScoreObjective sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1);
                if (args.length == 1) {
                    ChatUtil.sendMessage(scoreboard.toString());

                    if (sidebarObjective != null) {
                        String objectiveName = OtherUtil.removeColor(sidebarObjective.getDisplayName());
                        ChatUtil.sendMessage(sidebarObjective.toString());
                        ChatUtil.sendMessage(objectiveName);

                        List<String> sidebarLines = WorldUtil.getSidebarLines();
                        for (String s : sidebarLines) {
                            ChatUtil.sendMessage(s);
                        }

                    }
                } else {
                    ChatUtil.sendMessage(WorldUtil.getSidebarLine(Integer.parseInt(args[1])));
                }

                break;

            case "tool":
            case "t":
                ItemStack tool = PlayerUtil.getBestTool(mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock());
                ChatUtil.sendMessage("BestToolAt : " + PlayerUtil.getSlotfromItemStack(tool));

                break;

            case "item":
            case "i":
                int solt;

                if(args.length == 1) {
                    solt = mc.thePlayer.inventory.currentItem;
                } else {
                    solt = Integer.parseInt(args[1]);
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

                ChatUtil.sendMessage("ItemStack : " + itemStack);
                ChatUtil.sendMessage("Item : " + itemStack.getItem());
                ChatUtil.sendMessage("DisplayName : " + itemStack.getDisplayName());

                if (itemStack.isItemEnchanted()) {
                    ChatUtil.sendMessage("Enchantments : " + (EnchantmentHelper.getEnchantments(itemStack)));
                }

                if (itemStack.getItem() instanceof ItemBlock) {
                    ItemBlock itemBlock = (ItemBlock) itemStack.getItem();
                    Block block = itemBlock.getBlock();
                    IBlockState state = block.getStateFromMeta(itemStack.getMetadata());
                    String color = WorldUtil.getBlockColor(state);
                    if (color != null) {
                        ChatUtil.sendMessage("Color : " + color);
                    }
                }

                break;

            case "player":
            case "p":
                List<String> playerlist = new ArrayList<>();

                for (Entity entity : mc.theWorld.loadedEntityList) {
                    if (entity instanceof EntityPlayer && !(entity instanceof EntityPlayerSP)) {
                        if (!HypixelUtil.isNPC(entity)) {
                            String playername = entity.getName();
                            playerlist.add(playername);
                        }
                    }
                }

                ChatUtil.sendRawMessage("Players : " + String.join(", ",playerlist));

                break;

            case "block":
            case "b":
                IBlockState state = mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos());
                Block block = state.getBlock();

                ChatUtil.sendMessage(String.valueOf(block));
                ChatUtil.sendMessage("Localized Name : " + block.getLocalizedName());
                ChatUtil.sendMessage("Registry Name : " + block.getRegistryName());

                String color = WorldUtil.getBlockColor(state);
                if (color != null) {
                    ChatUtil.sendMessage("Color : " + color);
                }

                break;

            case "wither":
            case "w":
                for (Entity entity : mc.theWorld.loadedEntityList) {
                    if (entity instanceof EntityWither) {
                        ChatUtil.sendMessage("Wither Name : ");
                    }
                }

                break;

            case "bossbar":
            case "bb":
                if (BossStatus.statusBarTime > 0) {
                    ChatUtil.sendMessage("Boss Name : " + BossStatus.bossName);
                    ChatUtil.sendMessage("Boss Health : " + BossStatus.healthScale);
                } else {
                    ChatUtil.sendMessage("&cBossbar Not Found!");
                }

                break;

            default:
                ChatUtil.sendMessage("&cType Not Found!");
        }
    }
}
