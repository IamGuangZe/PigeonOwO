package owo.pigeon.commands.impl.test;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import owo.pigeon.commands.Command;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.OtherUtil;
import owo.pigeon.utils.PlayerUtil;
import owo.pigeon.utils.WorldUtil;
import owo.pigeon.utils.hypixel.HypixelUtil;

import java.util.ArrayList;
import java.util.List;

import static owo.pigeon.features.modules.Module.mc;

public class GetCommand extends Command {
    public GetCommand() {
        super("Get");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            ChatUtil.sendMessage("&cPlease enter the type!");
            ChatUtil.sendMessage("&cType : allarmorstandname(aasn), scoreboard(s), tool(t), weapon(w), armor(a), item(i), player(p), block(b), bossbar(bb)");
            return;
        }

        switch (args[0].toLowerCase()) {
            /*case "area" :
                ChatUtil.sendMessage(String.valueOf(SkyblockUtil.getIsland()));

                break;*/

            /*case "floor":
            case "f":
                ChatUtil.sendMessage(String.valueOf(SkyblockUtil.getFloor()));

                break;*/

            case "allarmorstandname":
            case "aasn":
                for (Entity entity : mc.theWorld.loadedEntityList) {
                    if (entity instanceof EntityArmorStand && entity.hasCustomName()) {
                        EntityArmorStand e = (EntityArmorStand) entity;
                        if (e.hasCustomName()) {
                            ChatUtil.sendUncoloredMessage(OtherUtil.removeColorA(entity.getName()));
                        }
                    }
                }

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
                    ChatUtil.sendMessage(WorldUtil.getSidebarLineBottonUp(Integer.parseInt(args[1])));
                }

                break;

            case "tool":
            case "t":
                ItemStack tool = PlayerUtil.getBestToolFromHotbar(mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock());
                ChatUtil.sendMessage("BestToolAt : " + PlayerUtil.getSlotfromItemStackEx(tool,true));

                break;

            case "weapon":
            case "w":
                ItemStack weapon = PlayerUtil.getBestWeapon();
                ChatUtil.sendMessage("BestWeaponAt : " + PlayerUtil.getSlotfromItemStackEx(weapon,false));

                break;

            case "armor":
            case "a":
                ItemStack Helmet = PlayerUtil.getBestHelmet();
                ItemStack Chestplate = PlayerUtil.getBestChestplate();
                ItemStack Leggings = PlayerUtil.getBestLeggings();
                ItemStack Boots = PlayerUtil.getBestBoots();
                ChatUtil.sendMessage("BestArmorAT : " +
                        PlayerUtil.getSlotfromItemStackEx(Helmet,false) + "; " +
                        PlayerUtil.getSlotfromItemStackEx(Chestplate,false) + "; " +
                        PlayerUtil.getSlotfromItemStackEx(Leggings,false) + "; " +
                        PlayerUtil.getSlotfromItemStackEx(Boots,false)
                );

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

                // 基础
                ChatUtil.sendMessage("ItemStack : " + itemStack);
                ChatUtil.sendMessage("Item : " + itemStack.getItem());
                ChatUtil.sendMessage("DisplayName : " + itemStack.getDisplayName());

                // 附魔
                if (itemStack.isItemEnchanted()) {
                    ChatUtil.sendMessage("Enchantments : " + (EnchantmentHelper.getEnchantments(itemStack)));
                }

                // 颜色
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
                    if (entity instanceof EntityPlayer) {
                        if (!HypixelUtil.isNPC(entity)) {
                            String playername = entity.getName();
                            playerlist.add(playername);
                        }
                    }
                }

                ChatUtil.sendMessage("Players : " + String.join("&r, ",playerlist));

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
                ChatUtil.sendMessage("&cType : allarmorstandname(aasn), scoreboard(s), tool(t), weapon(w), armor(a), item(i), player(p), block(b), bossbar(bb)");
        }
    }
}
