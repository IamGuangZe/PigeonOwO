package owo.pigeon.features.modules.player;

import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.*;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.settings.EnableSetting;
import owo.pigeon.settings.IntSetting;
import owo.pigeon.utils.PlayerUtil;

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

    public IntSetting startDelay = setting("start-delay", 1, 0, 20, v -> true);
    public IntSetting minDelay = setting("min-delay", 2, 0, 20, v -> true);
    public IntSetting maxDelay = setting("max-delay", 3, 0, 20, v -> true);
    public EnableSetting checkTitle = setting("check-title", true, v -> true);
    public EnableSetting smartPick = setting("smart-pick", true, v -> true);

    private int s_delay = 0;
    private int p_delay = 0;
    private boolean isFetched = false;
    private boolean fullWarning = false;
    private List<Integer> slotList = new ArrayList<Integer>();

    private int debug_action = 0;

    @Override
    public void onTick() {
        if (isNotNull()) {
            if (!(mc.thePlayer.openContainer instanceof ContainerChest)) {
                isFetched = false;
                fullWarning = false;
                s_delay = startDelay.getValue();
                p_delay = intRandom(minDelay.getValue(), maxDelay.getValue());
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
                    sendCustomPrefixMessage(this.name, "&cYour inventory is full!");
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
                    double bestBootsValue = getTotalArmorReduction(getBestBoots());

                    int bestPickaxeSlot = -1;
                    double bestPickaxeSpeed = getToolMiningSpeed(getBestTool(ToolType.PICKAXE), Blocks.stone);

                    int bestAxeSlot = -1;
                    double bestAxeSpeed = getToolMiningSpeed(getBestTool(ToolType.AXE), Blocks.planks);

                    int bestShovelSlot = -1;
                    double bestShovelSpeed = getToolMiningSpeed(getBestTool(ToolType.SHOVEL), Blocks.dirt);

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
                        } else if (item instanceof ItemPickaxe) {
                            double speed = getToolMiningSpeed(itemStack, Blocks.stone);
                            if (speed > bestPickaxeSpeed) {
                                bestPickaxeSpeed = speed;
                                bestPickaxeSlot = i;
                            }
                        } else if (item instanceof ItemAxe) {
                            double speed = getToolMiningSpeed(itemStack, Blocks.planks);
                            if (speed > bestAxeSpeed) {
                                bestAxeSpeed = speed;
                                bestAxeSlot = i;
                            }
                        } else if (item instanceof ItemSpade) {
                            double speed = getToolMiningSpeed(itemStack, Blocks.dirt);
                            if (speed > bestShovelSpeed) {
                                bestShovelSpeed = speed;
                                bestShovelSlot = i;
                            }
                        } else {
                            slotList.add(i);
                        }
                    }

                    if (bestWeaponSlot != -1) slotList.add(bestWeaponSlot);
                    if (bestHelmetSlot != -1) slotList.add(bestHelmetSlot);
                    if (bestChestSlot != -1) slotList.add(bestChestSlot);
                    if (bestLeggingsSlot != -1) slotList.add(bestLeggingsSlot);
                    if (bestBootsSlot != -1) slotList.add(bestBootsSlot);
                    if (bestPickaxeSlot != -1) slotList.add(bestPickaxeSlot);
                    if (bestAxeSlot != -1) slotList.add(bestAxeSlot);
                    if (bestShovelSlot != -1) slotList.add(bestShovelSlot);

                } else {
                    for (int i = 0; i < chestSize; i++) {
                        ItemStack itemStack = chest.getLowerChestInventory().getStackInSlot(i);
                        if (itemStack != null) {
                            slotList.add(i);
                        }
                    }
                }

                // sendCustomPrefixMessage("DEBUG " + this.name,"Slots Ready");
                isFetched = true;
            } else {

                // sendCustomPrefixMessage("DEBUG " + this.name,"Action - " + ++debug_action + "; s_delay - " + s_delay + "; p_delay - " + p_delay);

                if (!slotList.isEmpty() && s_delay <= 0 && p_delay <= 0) {
                    int randomIndex = intRandom(0, slotList.size() - 1);
                    int windowId = chest.windowId;
                    int slotid = slotList.get(randomIndex);

                    PlayerUtil.ClickWindow(windowId, slotid, 0, 1);
                    p_delay = intRandom(minDelay.getValue(), maxDelay.getValue());

                    // sendCustomPrefixMessage("DEBUG " + this.name,"Action - " + debug_action + "; Click");

                    slotList.remove(randomIndex);
                }
            }
        }
    }
}
