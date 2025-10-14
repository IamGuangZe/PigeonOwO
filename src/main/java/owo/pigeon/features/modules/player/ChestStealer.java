package owo.pigeon.features.modules.player;

import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import owo.pigeon.events.playerevent.WindowClickEvent;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.settings.EnableSetting;
import owo.pigeon.settings.IntSetting;
import owo.pigeon.utils.ChatUtil;

import java.util.ArrayList;
import java.util.List;

import static owo.pigeon.utils.ChatUtil.sendCustomPrefixMessage;
import static owo.pigeon.utils.OtherUtil.intRandom;
import static owo.pigeon.utils.PlayerUtil.*;
import static owo.pigeon.utils.WorldUtil.isNotNull;

public class ChestStealer extends Module {
    public ChestStealer() {
        super("ChestStealer", Category.PLAYER, -1);
    }

    public IntSetting startDelay = setting("startdelay",1,0,20,"",v->true);
    public IntSetting minDelay = setting("mindelay",2,0,20,"", v -> true);
    public IntSetting maxDelay = setting("maxdelay",3,0,20,"", v -> true);
    public EnableSetting checkTitle = setting("checktitle", true, "", v -> true);
    public EnableSetting smartPick = setting("smartpick",true,"",v->true);

    private int s_delay = 0;
    private int p_delay = 0;
    private boolean isFetched = false;
    private boolean fullWarning = false;
    private List<Integer> slotList = new ArrayList<Integer>();

    private int debug_action = 0;

    @Override
    public void onUpdate() {
        if (isNotNull()) {
            if (!(mc.thePlayer.openContainer instanceof ContainerChest)) {
                isFetched = false;
                fullWarning = false;
                s_delay = startDelay.getValue() + 1;
                p_delay = intRandom(minDelay.getValue() + 1, maxDelay.getValue() + 1);
                slotList.clear();
                return;
            }

            if (s_delay > 0) {
                s_delay--;
            }
            if (p_delay > 0) {
                p_delay--;
            }

            ContainerChest chest = (ContainerChest) mc.thePlayer.openContainer;
            String title = chest.getLowerChestInventory().getDisplayName().getUnformattedText();
            int chestSize = chest.getLowerChestInventory().getSizeInventory();

            if (checkTitle.getValue() && (!title.equalsIgnoreCase("Chest") && !title.equalsIgnoreCase("Large Chest"))) {
                return;
            }

            if (mc.thePlayer.inventory.getFirstEmptyStack() == -1) {
                if (!fullWarning) {
                    sendCustomPrefixMessage(this.name,"&cYour inventory is full!");
                    fullWarning = true;
                }
                return;
            } else {
                fullWarning = false;
            }

            if (!isFetched) {
                if (smartPick.getValue()) {
                    int bestWeaponSlot = -1;
                    double bestWeaponDamage = getItemDamage(getBestWeapon());

                    int bestHelmetSlot = -1;
                    double bestHelmetValue = getTotalArmorReduction(getBestHelmet());

                    int bestChestSlot = -1;
                    double bestChestValue = getTotalArmorReduction(getBestChestplate());

                    int bestLeggingsSlot = -1;
                    double bestLeggingsValue = getTotalArmorReduction(getBestLeggings());

                    int bestBootsSlot = -1;
                    double bestBootsValue = getArmorBaseReduction(getBestBoots());

                    for (int i = 0; i < chestSize; i++) {
                        ItemStack itemStack = chest.getLowerChestInventory().getStackInSlot(i);
                        if (itemStack == null) continue;

                        Item item = itemStack.getItem();
                        if (item instanceof ItemSword) {
                            double dmg = getItemDamage(itemStack);
                            if (dmg > bestWeaponDamage) {
                                bestWeaponDamage = dmg;
                                bestWeaponSlot = i;
                            }
                        } else if (item instanceof ItemArmor) {
                            ItemArmor armor = (ItemArmor) item;
                            double value = getTotalArmorReduction(itemStack);
                            switch (armor.armorType) {
                                case 0:
                                    if (value > bestHelmetValue) {
                                        bestHelmetValue = value;
                                        bestHelmetSlot = i;
                                    }
                                    break;
                                case 1:
                                    if (value > bestChestValue) {
                                        bestChestValue = value;
                                        bestChestSlot = i;
                                    }
                                    break;
                                case 2:
                                    if (value > bestLeggingsValue) {
                                        bestLeggingsValue = value;
                                        bestLeggingsSlot = i;
                                    }
                                    break;
                                case 3:
                                    if (value > bestBootsValue) {
                                        bestBootsValue = value;
                                        bestBootsSlot = i;
                                    }
                                    break;
                            }
                        } else {
                            slotList.add(i); // 非装备直接加入
                        }
                    }

                    // 最优装备加入slotList
                    if (bestWeaponSlot != -1) slotList.add(bestWeaponSlot);
                    if (bestHelmetSlot != -1) slotList.add(bestHelmetSlot);
                    if (bestChestSlot != -1) slotList.add(bestChestSlot);
                    if (bestLeggingsSlot != -1) slotList.add(bestLeggingsSlot);
                    if (bestBootsSlot != -1) slotList.add(bestBootsSlot);

                } else {
                    for (int i = 0; i < chestSize; i++) {
                        ItemStack itemStack = chest.getLowerChestInventory().getStackInSlot(i);
                        if (itemStack != null) {
                            slotList.add(i);
                        }
                    }
                }

                sendCustomPrefixMessage("DEBUG " + this.name,"Slots Ready");
                s_delay++;
                isFetched = true;
            } else {

                ChatUtil.sendCustomPrefixMessage("DEBUG " + this.name,"Action - " + ++debug_action + "; s_delay - " + s_delay + "; p_delay - " + p_delay);

                if (!slotList.isEmpty() && s_delay <= 0 && p_delay <= 0) {
                    int randomIndex = intRandom(0, slotList.size() - 1);
                    int windowId = chest.windowId;
                    int slotid = slotList.get(randomIndex);
                    shiftClick(windowId, slotid);

                    ChatUtil.sendCustomPrefixMessage("DEBUG " + this.name,"Action - " + debug_action + "; Click");

                    slotList.remove(randomIndex);
                }
            }
        }
    }

    private void shiftClick(int windowId, int slotId) {
        mc.playerController.windowClick(windowId, slotId, 0, 1, mc.thePlayer);
    }

    @SubscribeEvent
    public void onWindowClick(WindowClickEvent event) {
        p_delay = intRandom(minDelay.getValue() + 1, maxDelay.getValue() + 1);
    }
}
