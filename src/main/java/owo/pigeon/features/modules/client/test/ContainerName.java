package owo.pigeon.features.modules.client.test;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import owo.pigeon.features.Category;
import owo.pigeon.features.Module;
import owo.pigeon.utils.ChatUtil;

public class ContainerName extends Module {
    private short nextActionNumber = 0; // 事务号计数器

    public ContainerName() {
        super("ContainerName", Category.CLIENT, -1);
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (!this.isEnable()) return;

        if (event.gui instanceof GuiContainer) {
            GuiContainer guiContainer = (GuiContainer) event.gui;
            Container container = guiContainer.inventorySlots;

            String title = null;

            if (container instanceof ContainerChest) {
                title = ((ContainerChest) container).getLowerChestInventory().getDisplayName().getUnformattedText();
            }

            ChatUtil.sendMessage("Container opened: " + title);

            if ("Chest #Paper".equalsIgnoreCase(title)) {
                pickUpLastPaper(container);
            }
        }
    }

    private void pickUpLastPaper(Container container) {
        Slot lastPaperSlot = null;

        for (int i = container.inventorySlots.size() - 1; i >= 0; i--) {
            Slot slot = container.getSlot(i);
            ItemStack stack = slot.getStack();

            if (stack==null) continue;
            ChatUtil.sendMessage(slot.getStack().toString());
            if (stack.getItem() == Items.paper) {
                lastPaperSlot = slot;
                break;
            }
        }

        if (lastPaperSlot != null) {
            mc.thePlayer.sendQueue.addToSendQueue(
                    new C0EPacketClickWindow(
                            container.windowId,
                            lastPaperSlot.slotNumber,
                            0,
                            0,
                            lastPaperSlot.getStack(),
                            nextActionNumber++
                    )
            );
            ChatUtil.sendMessage("Clicked last paper in slot " + lastPaperSlot.slotNumber);
        } else {
            ChatUtil.sendMessage("No paper found in container.");
        }
    }

}
