package owo.pigeon.utils;

import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;

import java.util.Iterator;
import java.util.Objects;

import static owo.pigeon.features.modules.Module.mc;

public class ItemUtil {
    public enum ToolType {
        PICKAXE, AXE, SHOVEL
    }

    // !![Armor/Old](https://minecraft.wiki/w/Armor/Old)
    public enum ArmorType {
        HELMET, CHESTPLATE, LEGGINGS, BOOTS
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

    public static int getArmorSlotFromItemStack(ItemStack targetItemStack, boolean addHundred) {
        if (mc.thePlayer == null || mc.thePlayer.inventory == null || targetItemStack == null) return -1;

        ItemStack[] armorInventory = mc.thePlayer.inventory.armorInventory;

        for (int i = 0; i < armorInventory.length; i++) {
            ItemStack itemStack = armorInventory[i];
            if (itemStack != null &&
                    targetItemStack == itemStack &&
                    Objects.equals(itemStack.getDisplayName(), targetItemStack.getDisplayName()) &&
                    EnchantmentHelper.getEnchantments(itemStack).equals(EnchantmentHelper.getEnchantments(targetItemStack))) {
                if (addHundred) {
                    return 103 - i;
                } else {
                    return 3 - i;
                }
            }
        }

        return -1;
    }

    public static int getSlotfromItemStackEx(ItemStack targetItemStack, boolean onlyHotbar) {
        if (mc.thePlayer == null || mc.thePlayer.inventory == null || targetItemStack == null) {
            return -1;
        }

        int range;
        if (onlyHotbar) {
            range = 9;
        } else {
            range = mc.thePlayer.inventory.mainInventory.length;
        }

        for (int i = 0; i < range; i++) {
            ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);

            if (targetItemStack == itemStack &&
                    Objects.equals(itemStack.getDisplayName(), targetItemStack.getDisplayName()) &&
                    EnchantmentHelper.getEnchantments(itemStack).equals(EnchantmentHelper.getEnchantments(targetItemStack))) {
                return i;
            }
        }

        if (targetItemStack.getItem() instanceof ItemArmor && !onlyHotbar) {
            return getArmorSlotFromItemStack(targetItemStack, true);
        }

        return -1;
    }

    public static int getSlotfromItemname(String itemname) {
        if (mc.thePlayer == null || mc.thePlayer.inventory == null) return -1;

        // 遍历快捷栏的槽位（0 到 8）
        for (int i = 0; i < 9; i++) {
            // 获取当前快捷栏槽位的物品
            ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack == null) {
                continue;
            }

            // 判断物品是否匹配
            if (OtherUtil.removeColor(itemStack.getDisplayName()).toLowerCase().contains(itemname.toLowerCase())) {
                return i;  // 返回快捷栏槽位的索引
            }
        }
        return -1;  // 如果没有找到匹配的物品，返回 -1
    }

    public static ItemStack getItemStackfromSlot(int slot) {
        return mc.thePlayer.inventory.getStackInSlot(slot);
    }

    // 获取物品上的锋利等级
    public static int getSharpnessLevel(ItemStack itemStack) {
        if (itemStack == null) return 0;
        return EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack);
    }

    // 获取锋利增加的伤害
    public static double getSharpnessDamage(int level) {
        return level * 1.25;
    }

    // 获取物品的基础伤害
    public static double getItemBaseDamage(ItemStack itemStack) {
        if (itemStack == null) return 0;

        Multimap<String, AttributeModifier> multimap = itemStack.getAttributeModifiers();
        for (String attributeName : multimap.keySet()) {
            if (!attributeName.equals("generic.attackDamage")) continue;

            Iterator<AttributeModifier> iterator = multimap.get(attributeName).iterator();
            if (!iterator.hasNext()) break;

            return iterator.next().getAmount();
        }

        return 0;
    }

    // 获取物品的攻击伤害（基础伤害 + 锋利加成）
    public static double getItemDamage(ItemStack itemStack) {
        if (itemStack == null) return 0;

        double baseDamage = getItemBaseDamage(itemStack);
        int sharpnessLevel = getSharpnessLevel(itemStack);
        double sharpnessBonus = getSharpnessDamage(sharpnessLevel);

        return baseDamage + sharpnessBonus;
    }

    // 获取玩家整个背包中攻击力最高的物品 (外加火焰附加加成)
    public static ItemStack getBestWeapon() {
        float highestDamage = 0;
        ItemStack bestWeapon = null;

        for (int i = 0; i < mc.thePlayer.inventory.mainInventory.length; i++) {
            ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack == null || itemStack.getItem() == null) continue;

            double damage = getItemDamage(itemStack) + (double) EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack);
            if (damage > highestDamage) {
                highestDamage = (float) damage;
                bestWeapon = itemStack;
            }
        }

        return bestWeapon;
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

    // 获取工具的基础挖掘速度
    public static float getToolBaseMiningSpeed(ItemStack itemStack, Block block) {
        if (itemStack == null) return 1.0F;
        return itemStack.getStrVsBlock(block);
    }

    // 获取工具的挖掘速度
    public static float getToolMiningSpeed(ItemStack itemStack, Block block) {
        float basespeed = getToolBaseMiningSpeed(itemStack, block);

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

    // 获取玩家快捷栏中挖掘速度最快的工具
    public static ItemStack getBestToolFromHotbar(Block block) {
        float fastestSpeed = 1;
        ItemStack besttool = null;
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack == null || itemStack.getItem() == null) continue;
            float speed = getToolMiningSpeed(itemStack, block);

            if (speed > fastestSpeed) {
                fastestSpeed = speed;
                besttool = itemStack;
            }
        }
//        ChatUtil.sendMessage(String.valueOf(besttool));
        return besttool;
    }

    // 获取指定挖掘速度最好的工具
    public static ItemStack getBestTool(ToolType toolType) {
        float fastestSpeed = 1;
        ItemStack bestTool = null;

        for (int i = 0; i < mc.thePlayer.inventory.mainInventory.length; i++) {
            ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack == null || itemStack.getItem() == null) continue;

            // 根据工具类型筛选
            boolean isTargetTool = false;
            Block block = null;
            switch (toolType) {
                case PICKAXE:
                    isTargetTool = itemStack.getItem() instanceof ItemPickaxe;
                    block = Blocks.stone;
                    break;
                case AXE:
                    isTargetTool = itemStack.getItem() instanceof ItemAxe;
                    block = Blocks.planks;
                    break;
                case SHOVEL:
                    isTargetTool = itemStack.getItem() instanceof ItemSpade;
                    block = Blocks.dirt;
                    break;
            }

            if (!isTargetTool) continue;

            float speed = getToolMiningSpeed(itemStack, block);
            if (speed > fastestSpeed) {
                fastestSpeed = speed;
                bestTool = itemStack;
            }
        }
        return bestTool;
    }

    // 获取物品的基础护甲减伤
    public static double getArmorBaseReduction(ItemStack itemStack) {
        if (itemStack == null || !(itemStack.getItem() instanceof ItemArmor)) return 0;
        ItemArmor armor = (ItemArmor) itemStack.getItem();
        return armor.damageReduceAmount * 0.04; // 每点护甲减4%
    }

    // 获取物品上的保护等级
    public static int getProtectionLevel(ItemStack itemStack) {
        if (itemStack == null) return 0;
        return EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, itemStack);
    }

    // 根据保护等级获取EPF
    public static int getProtectionEPF(int level) {
        switch (level) {
            case 1:
                return 1; // I
            case 2:
                return 2; // II
            case 3:
                return 3; // III
            case 4:
                return 5; // IV
            default:
                return 0;
        }
    }

    // 获取防具的魔咒减伤
    public static double getProtectionReduction(ItemStack itemStack) {
        if (itemStack == null) return 0;

        int protLevel = getProtectionLevel(itemStack);
        int epf = getProtectionEPF(protLevel);

        // 假设每次保护都能达到最大效果
        int effectiveEPF = Math.min(20, (int) Math.ceil(Math.min(epf, 25) * 1.0));
        return effectiveEPF * 0.04; // 每点有效EPF减少4%伤害
    }

    // 获取防具总减伤
    public static double getTotalArmorReduction(ItemStack itemStack) {
        double armorReduction = getArmorBaseReduction(itemStack);       // 基础护甲减伤
        double protectionReduction = getProtectionReduction(itemStack); // 魔咒减伤

        // 魔咒作用在护甲减伤后的剩余伤害上
        return 1 - (1 - armorReduction) * (1 - protectionReduction);
    }

    // 获取玩家背包 + 对应装备栏中减伤最高的头盔
    public static ItemStack getBestHelmet() {
        double maxReduction = 0;
        ItemStack bestHelmet = null;

        for (int i = 0; i < 36; i++) {
            ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack == null || !(itemStack.getItem() instanceof ItemArmor)) continue;

            ItemArmor armor = (ItemArmor) itemStack.getItem();
            if (armor.armorType != 0) continue;

            double reduction = getTotalArmorReduction(itemStack);
            if (reduction > maxReduction) {
                maxReduction = reduction;
                bestHelmet = itemStack;
            }
        }

        ItemStack armorSlot = mc.thePlayer.inventory.armorInventory[3];
        if (armorSlot != null && armorSlot.getItem() instanceof ItemArmor) {
            double reduction = getTotalArmorReduction(armorSlot);
            if (reduction > maxReduction) bestHelmet = armorSlot;
        }

        return bestHelmet;
    }

    // 获取玩家背包 + 对应装备栏中减伤最高的胸甲
    public static ItemStack getBestChestplate() {
        double maxReduction = 0;
        ItemStack bestChest = null;

        for (int i = 0; i < 36; i++) {
            ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack == null || !(itemStack.getItem() instanceof ItemArmor)) continue;

            ItemArmor armor = (ItemArmor) itemStack.getItem();
            if (armor.armorType != 1) continue;

            double reduction = getTotalArmorReduction(itemStack);
            if (reduction > maxReduction) {
                maxReduction = reduction;
                bestChest = itemStack;
            }
        }

        ItemStack armorSlot = mc.thePlayer.inventory.armorInventory[2];
        if (armorSlot != null && armorSlot.getItem() instanceof ItemArmor) {
            double reduction = getTotalArmorReduction(armorSlot);
            if (reduction > maxReduction) bestChest = armorSlot;
        }

        return bestChest;
    }

    // 获取玩家背包 + 对应装备栏中减伤最高的护腿
    public static ItemStack getBestLeggings() {
        double maxReduction = 0;
        ItemStack bestLegs = null;

        for (int i = 0; i < 36; i++) {
            ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack == null || !(itemStack.getItem() instanceof ItemArmor)) continue;

            ItemArmor armor = (ItemArmor) itemStack.getItem();
            if (armor.armorType != 2) continue;

            double reduction = getTotalArmorReduction(itemStack);
            if (reduction > maxReduction) {
                maxReduction = reduction;
                bestLegs = itemStack;
            }
        }

        ItemStack armorSlot = mc.thePlayer.inventory.armorInventory[1];
        if (armorSlot != null && armorSlot.getItem() instanceof ItemArmor) {
            double reduction = getTotalArmorReduction(armorSlot);
            if (reduction > maxReduction) bestLegs = armorSlot;
        }

        return bestLegs;
    }

    // 获取玩家背包 + 对应装备栏中减伤最高的鞋子
    public static ItemStack getBestBoots() {
        double maxReduction = 0;
        ItemStack bestBoots = null;

        for (int i = 0; i < 36; i++) {
            ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack == null || !(itemStack.getItem() instanceof ItemArmor)) continue;

            ItemArmor armor = (ItemArmor) itemStack.getItem();
            if (armor.armorType != 3) continue;

            double reduction = getTotalArmorReduction(itemStack);
            if (reduction > maxReduction) {
                maxReduction = reduction;
                bestBoots = itemStack;
            }
        }

        ItemStack armorSlot = mc.thePlayer.inventory.armorInventory[0];
        if (armorSlot != null && armorSlot.getItem() instanceof ItemArmor) {
            double reduction = getTotalArmorReduction(armorSlot);
            if (reduction > maxReduction) bestBoots = armorSlot;
        }

        return bestBoots;
    }

    public static int getTotalItemCount(Item item) {
        int totalCount = 0;
        for (int i = 0; i < mc.thePlayer.inventory.mainInventory.length; i++) {
            ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack != null && itemStack.getItem() == item) {
                totalCount += itemStack.stackSize;
            }
        }
        return totalCount;
    }

    public static boolean isGoodFood(Item item) {
        if (!(item instanceof ItemFood)) return false;
        return item != Items.rotten_flesh
                && item != Items.poisonous_potato
                && item != Items.spider_eye;
    }
}
