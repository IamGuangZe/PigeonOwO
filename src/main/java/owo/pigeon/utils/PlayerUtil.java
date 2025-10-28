package owo.pigeon.utils;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.WorldSettings;
import org.lwjgl.input.Mouse;

import static owo.pigeon.features.modules.Module.mc;

public class PlayerUtil {

    // 切换到指定的物品栏槽（index 是 0 到 8 之间的值 , 超过该值会被服务器踢出）
    public static void switchItemSlot(int index) {
        if (index < 0 || index > 8) {
            return;
        }
        mc.thePlayer.inventory.currentItem = index;
    }

    public static void switchUseItem(int slot) {
        switchItemSlot(slot);
        rightClick();
    }

    // 快速切换至指定槽位并且使用
    public static void instantUseItem(int slot) {
        int rawslot = mc.thePlayer.inventory.currentItem;
        mc.thePlayer.inventory.currentItem = slot;
        mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, ItemUtil.getItemStackfromSlot(slot));
        mc.thePlayer.inventory.currentItem = rawslot;
    }

    // 按键模拟点击
    public static void leftClick() {
        KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());

    }

    public static void rightClick() {
        KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());

    }

    public static boolean isBreakingBlock() {
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
     * @param mouseButtonClicked 鼠标按钮：0 = 左键点击，1 = 右键点击
     * @param mode               操作模式：
     *                           0 = PICKUP（普通点击）
     *                           1 = QUICK_MOVE（Shift）
     *                           2 = SWAP（使用热键栏交换）
     *                           3 = CLONE（创造模式中复制）
     *                           4 = THROW（丢弃）
     *                           5 = QUICK_CRAFT（快速合成）
     *                           6 = PICKUP_ALL（拾取全部同类）
     */
    public static void packetClickWindow(int windowId, int slotId, int mouseButtonClicked, int mode, ItemStack clickedStack, short actionNumber) {
        mc.getNetHandler().addToSendQueue(new C0EPacketClickWindow(
                windowId,
                slotId,
                mouseButtonClicked,
                mode,
                clickedStack,
                actionNumber
        ));
    }

    public static void ClickWindow(int windowId, int slotId, int mouseButtonClicked, int mode) {
        mc.playerController.windowClick(windowId, slotId, mouseButtonClicked, mode, mc.thePlayer);
    }
}
