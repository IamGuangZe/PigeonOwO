package owo.pigeon.utils;

import jline.internal.Nullable;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;

import java.util.List;
import java.util.Objects;

import static owo.pigeon.modules.Module.mc;

public class PlayerUtil {

    // 切换到指定的物品栏槽（index 是 0 到 8 之间的值 , 超过该值会被服务器踢出）
    public static void switchItemSlot(int index, @Nullable ItemStack currentItem) {
        mc.thePlayer.inventory.currentItem = index;
//        mc.getNetHandler().addToSendQueue(new S2FPacketSetSlot(mc.thePlayer.inventoryContainer.windowId,index,currentItem));
//        mc.playerController.updateController();
    }

    // 获取玩家快捷栏中挖掘速度最快的工具
    public static ItemStack getBestTool(String tool) {
        int fastestSpeed = -1;
        ItemStack besttool = null;
        // 遍历玩家快捷栏（包含1~9格物品栏）
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack == null || itemStack.getItem() == null) continue;

            Item item = itemStack.getItem();
            int speed = getToolMiningSpeed(itemStack);

            switch (tool.toLowerCase()) {
                case "pickaxe": {
                    if (item instanceof ItemPickaxe && speed > fastestSpeed) {
                            fastestSpeed = speed;
                            besttool = itemStack;
                    }
                    continue;
                }
                case "axe": {
                    if (item instanceof ItemAxe && speed > fastestSpeed) {
                            fastestSpeed = speed;
                            besttool = itemStack;
                    }
                    continue;
                }
                default: throw new IllegalArgumentException("Inviad tool type");
            }
        }
        return besttool;
    }

    public static ItemStack getBestToolA(Block block) {
        float fastestSpeed = 1;
        ItemStack besttool = null;
        // 遍历玩家快捷栏（包含1~9格物品栏）
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack == null || itemStack.getItem() == null) continue;
            float speed = getToolMiningSpeedA(itemStack,block);

            if (speed > fastestSpeed) {
                fastestSpeed = speed;
                besttool = itemStack;
            }
        }
//        ChatUtil.sendMessage(String.valueOf(besttool));
        return besttool;
    }

    // 获取物品上的效率等级
    public static int getEfficiencyLevel(ItemStack itemStack) {
        if (itemStack == null) return 0;
        return EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);
    }

    // 获取效率增加的速度
    public static int getEfficiencySpeed(int level) {
        if (level == 0) {
            return 0;
        }
        return level * level + 1;
    }

    // 获取物品在快捷栏中的槽位（0 到 8）
    public static int getSlotfromItemStack(ItemStack targetItemStack) {
        // 检查 mc.thePlayer 和 mc.thePlayer.inventory 是否为 null，避免空指针
        if (mc.thePlayer == null || mc.thePlayer.inventory == null || targetItemStack == null) return -1;

        // 遍历快捷栏的槽位（0 到 8）
        for (int i = 0; i < 9; i++) {
            // 获取当前快捷栏槽位的物品
            ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);

            // 判断物品是否匹配
            if (targetItemStack == itemStack &&
                    Objects.equals(itemStack.getDisplayName(), targetItemStack.getDisplayName()) &&
                    EnchantmentHelper.getEnchantments(itemStack).equals(EnchantmentHelper.getEnchantments(targetItemStack))) {
                return i;  // 返回快捷栏槽位的索引
            }
        }
        return -1;  // 如果没有找到匹配的物品，返回 -1
    }

    public static ItemStack getItemStackfromSlot(int slot) {return mc.thePlayer.inventory.getStackInSlot(slot);}

    // 获取工具的基础挖掘速度
    public static int getToolBaseMiningSpeed(ItemStack itemStack) {
        ToolMaterial material;
        if (itemStack == null) {
            return 0;
        } else if (itemStack.getItem() instanceof ItemTool) {
            ItemTool pickaxe = (ItemTool) itemStack.getItem();
            material = pickaxe.getToolMaterial();
        } else {
            return 1;
        }

        if (material == ToolMaterial.WOOD) {
            return 2;  // 木
        } else if (material == ToolMaterial.STONE) {
            return 4;  // 石
        } else if (material == ToolMaterial.IRON) {
            return 6;  // 铁
        } else if (material == ToolMaterial.EMERALD) {
            return 8;  // 钻石
        } else if (material == ToolMaterial.GOLD) {
            return 12;  // 金
        }
        return 0;  // 默认返回 0，如果不是这些材质
    }

    public static float getToolBaseMiningSpeedA(ItemStack itemStack , Block block) {
        return itemStack.getStrVsBlock(block);
    }

    // 获取工具的挖掘速度
    public static int getToolMiningSpeed(ItemStack itemStack) {
        if (itemStack == null) return 0;
        int basespeed = getToolBaseMiningSpeed(itemStack);
        int efficiencyspeed = getEfficiencySpeed(getEfficiencyLevel(itemStack));

//        ChatUtil.sendMessage("solt : " + getItemStackSlotInHotbar(itemStack));
//        ChatUtil.sendMessage("BaseSpeed : " + basespeed);
//        ChatUtil.sendMessage("EfficiencyLevel : " + getEfficiencyLevel(itemStack));
//        ChatUtil.sendMessage("EfficiencySpeed : " + efficiencyspeed);
//        ChatUtil.sendMessage("Speed : " + (basespeed + efficiencyspeed));
//        ChatUtil.sendMessage("");

        return basespeed + efficiencyspeed;
    }

    public static float getToolMiningSpeedA(ItemStack itemStack , Block block) {
        float basespeed = getToolBaseMiningSpeedA(itemStack,block);

//        ChatUtil.sendMessage("solt : " + getItemStackSlotInHotbar(itemStack));
//        ChatUtil.sendMessage("BaseSpeed : " + basespeed);

        if (basespeed > 1) {
            float efficiencyspeed = getEfficiencySpeed(getEfficiencyLevel(itemStack));

//            ChatUtil.sendMessage("EfficiencyLevel : " + getEfficiencyLevel(itemStack));
//            ChatUtil.sendMessage("EfficiencySpeed : " + efficiencyspeed);
//            ChatUtil.sendMessage("Speed : " + (basespeed + efficiencyspeed));
//            ChatUtil.sendMessage("");

            return basespeed + efficiencyspeed;
        } else {
//            ChatUtil.sendMessage("");
            return basespeed;
        }
    }

    // 获取计分板的标题
    public static String getScoreboardDisplayName() {
        Scoreboard scoreboard = mc.theWorld.getScoreboard();
        ScoreObjective sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1);
        if (sidebarObjective != null) {
            return sidebarObjective.getDisplayName();
        }
        return null;
    }

    public static String getScoreboardDisplayNameWithoutColor() {
        if (getScoreboardDisplayName() == null){
            return null;
        }
        return ChatUtil.removeColor(getScoreboardDisplayName());
    }

    // 获取计分板对应行数的内容(服务器不可用)
    public static String getLineOfSidebar(int line) {
        Scoreboard scoreboard = mc.theWorld.getScoreboard();
        ScoreObjective sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1);  // 1 是侧边栏显示的位置

        if (sidebarObjective == null) {
            return null;  // 如果没有设置目标，返回空
        }

        List<Score> scores = (List<Score>) scoreboard.getSortedScores(sidebarObjective);
        ChatUtil.sendMessage(scores.toString());

        if (scores.size() >= line) {
            // 获取第三行的分数内容
            Score LineScore = scores.get(line - 1);  // 索引从 0 开始，第line行索引为 line - 1
            return LineScore.getPlayerName();  // 获取分数对应的玩家名称或显示的文本
        } else {
            return null;  // 如果少于line行，返回 null
        }
    }
}
