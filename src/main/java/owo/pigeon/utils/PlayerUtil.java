package owo.pigeon.utils;

import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.WorldSettings;
import org.lwjgl.input.Mouse;

import java.util.Objects;

import static owo.pigeon.features.Module.mc;

public class PlayerUtil {

    // 切换到指定的物品栏槽（index 是 0 到 8 之间的值 , 超过该值会被服务器踢出）
    public static void switchItemSlot(int index) {
        if (index < 0 || index > 8) {
            return;
        }
        mc.thePlayer.inventory.currentItem = index;
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

    public static void switchUseItem(int slot) {
        switchItemSlot(slot);
        rightClick();
    }

    // 快速切换至指定槽位并且使用
    public static void instantUseItem(int slot) {
        int rawslot = mc.thePlayer.inventory.currentItem;
        mc.thePlayer.inventory.currentItem = slot;
        mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, getItemStackfromSlot(slot));
        mc.thePlayer.inventory.currentItem = rawslot;
    }

    // 按键模拟点击
    public static void leftClick() {
        KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());

    }
    public static void rightClick() {
        KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());

    }

    // 获取玩家快捷栏中挖掘速度最快的工具
    public static ItemStack getBestTool(Block block) {
        float fastestSpeed = 1;
        ItemStack besttool = null;
        // 遍历玩家快捷栏（包含1~9格物品栏）
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack == null || itemStack.getItem() == null) continue;
            float speed = getToolMiningSpeed(itemStack,block);

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
    // 获取工具的基础挖掘速度
    public static float getToolBaseMiningSpeed(ItemStack itemStack , Block block) {
        return itemStack.getStrVsBlock(block);
    }
    // 获取工具的挖掘速度
    public static float getToolMiningSpeed(ItemStack itemStack , Block block) {
        float basespeed = getToolBaseMiningSpeed(itemStack,block);

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

    public static boolean isBreakingBlock(){
        return mc.thePlayer.isSwingInProgress &&
                mc.objectMouseOver != null &&
                mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK &&
                mc.playerController.getCurrentGameType() != WorldSettings.GameType.ADVENTURE &&
                Mouse.isButtonDown(0);
    }

    public static void aimblock(BlockPos blockPos) {
        if (blockPos == null || mc.thePlayer == null) return;

        // 计算方块中心位置
        double blockX = blockPos.getX() + 0.5;
        double blockY = blockPos.getY() + 0.5;
        double blockZ = blockPos.getZ() + 0.5;

        // 计算玩家位置
        double playerX = mc.thePlayer.posX;
        double playerY = mc.thePlayer.posY + mc.thePlayer.getEyeHeight(); // 玩家视线高度
        double playerZ = mc.thePlayer.posZ;

        // 计算方向向量
        double deltaX = blockX - playerX;
        double deltaY = blockY - playerY;
        double deltaZ = blockZ - playerZ;

        // 计算偏航角（水平旋转）
        double distanceXZ = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ); // XZ 平面距离
        float yaw = (float) (Math.atan2(deltaZ, deltaX) * (180 / Math.PI)) - 90;

        // 计算俯仰角（垂直旋转）
        float pitch = (float) -(Math.atan2(deltaY, distanceXZ) * (180 / Math.PI));

        // 设置玩家视角
        mc.thePlayer.rotationYaw = yaw;
        mc.thePlayer.rotationPitch = pitch;
    }

    /**
     * @param mouseButton   鼠标按钮：0 = 左键点击，1 = 右键点击
     * @param mode          操作模式：
     *                       0 = PICKUP（普通点击）
     *                       1 = QUICK_MOVE（Shift+点击）
     *                       2 = SWAP（使用热键栏交换）
     *                       3 = CLONE（创造模式中复制）
     *                       4 = THROW（丢弃）
     *                       5 = QUICK_CRAFT（快速合成）
     *                       6 = PICKUP_ALL（拾取全部同类）
     */
    public static void packetClickWindow(int windowId, int slotNumber, int mouseButton, int mode, ItemStack clickedStack, short actionNumber) {
        mc.getNetHandler().addToSendQueue(new C0EPacketClickWindow(
                windowId,
                slotNumber,
                mouseButton,
                mode,
                clickedStack,
                actionNumber
        ));
    }
}
