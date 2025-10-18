package owo.pigeon.features.modules.client.test;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.PlayerUtil;

public class ContainerClick extends Module {
    private short nextActionNumber = 0; // 事务号计数器
    private boolean clickedGoldenApple = false;
    private boolean clickedStone = false;

    public ContainerClick() {
        super("ContainerClick", Category.CLIENT, -1);
    }

    @Override
    public void onTick() {
        if (mc.thePlayer == null) return;
        if (!(mc.thePlayer.openContainer instanceof ContainerChest)) {
            // 容器关闭时重置标志
            clickedGoldenApple = false;
            clickedStone = false;
            return;
        }

        ContainerChest chest = (ContainerChest) mc.thePlayer.openContainer;
        String title = chest.getLowerChestInventory().getDisplayName().getUnformattedText();
        int chestSize = chest.getLowerChestInventory().getSizeInventory();

        // ---------- Chest #Paper ----------
        if ("Chest #Paper".equalsIgnoreCase(title)) {
            Slot lastPaperSlot = null;
            for (int i = 0; i < chestSize; i++) {
                Slot slot = chest.getSlot(i);
                ItemStack stack = slot.getStack();
                if (stack != null && stack.getItem() == Items.paper) {
                    lastPaperSlot = slot;
                }
            }
            if (lastPaperSlot != null) {
                short actionNumber = nextActionNumber++;
                PlayerUtil.packetClickWindow(
                        chest.windowId,
                        lastPaperSlot.slotNumber,
                        0,
                        0,
                        lastPaperSlot.getStack(),
                        actionNumber
                );

                ChatUtil.sendMessage("Picked up paper from slot " + lastPaperSlot.slotNumber);
            }
        }

        // ---------- Game Menu: 点击金苹果（点击一次） ----------
        if ("Game Menu".equalsIgnoreCase(title) && !clickedGoldenApple) {
            for (int i = 0; i < chestSize; i++) {
                Slot slot = chest.getSlot(i);
                ItemStack stack = slot.getStack();
                if (stack != null && stack.getItem() == Items.golden_apple) {
                    short actionNumber = nextActionNumber++;
                    mc.getNetHandler().addToSendQueue(new C0EPacketClickWindow(
                            chest.windowId,
                            slot.slotNumber,
                            0,
                            0,
                            stack,
                            actionNumber
                    ));
                    ChatUtil.sendMessage("Clicked golden apple in slot " + slot.slotNumber);
                    clickedGoldenApple = true; // 标记已经点击
                    break;
                }
            }
        }

        // ---------- Chest: 点击石头（点击一次，不关闭GUI） ----------
        if ("Chest".equalsIgnoreCase(title) && !clickedStone) {
            for (int i = 0; i < chestSize; i++) {
                Slot slot = chest.getSlot(i);
                ItemStack stack = slot.getStack();
                if (stack != null && stack.getItem() == Item.getItemFromBlock(Blocks.stone)) {
                    short actionNumber = nextActionNumber++;
                    mc.getNetHandler().addToSendQueue(new C0EPacketClickWindow(
                            chest.windowId,
                            slot.slotNumber,
                            0,
                            0,
                            stack,
                            actionNumber
                    ));
                    ChatUtil.sendMessage("Clicked stone in slot " + slot.slotNumber);
                    clickedStone = true; // 标记已经点击
                    break;
                }
            }
        }
    }
}
