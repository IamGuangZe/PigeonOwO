package owo.pigeon.features.modules.player;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.*;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.settings.EnableSetting;
import owo.pigeon.settings.IntSetting;
import owo.pigeon.settings.ModeSetting;
import owo.pigeon.utils.ItemUtil;
import owo.pigeon.utils.PlayerUtil;

import java.util.ArrayList;
import java.util.List;

import static owo.pigeon.utils.ChatUtil.sendCustomPrefixMessage;
import static owo.pigeon.utils.OtherUtil.intRandom;
import static owo.pigeon.utils.WorldUtil.isNotNull;

public class ChestStealer extends Module {
    public ChestStealer() {
        super("ChestStealer", Category.PLAYER, -1);
    }

    public enum smartModeEnum {
        SKYWARS, THEPIT
    }

    public IntSetting startDelay = setting("start-delay", 1, 0, 20, v -> true);
    public IntSetting minDelay = setting("min-delay", 2, 0, 20, v -> true);
    public IntSetting maxDelay = setting("max-delay", 3, 0, 20, v -> true);
    public EnableSetting checkTitle = setting("check-title", true, v -> true);
    public EnableSetting randomOrder = setting("random-order", true, v -> true);

    public EnableSetting smartPick = setting("smart-pick", true, v -> true);
    public ModeSetting<smartModeEnum> smartMode = setting("smart-mode", smartModeEnum.SKYWARS, v -> true);

    public EnableSetting pickSword = setting("sword", true, v -> true);
    public EnableSetting pickArmor = setting("armor", true, v -> true);
    public EnableSetting pickPickaxe = setting("pickaxe", true, v -> true);
    public EnableSetting pickAxe = setting("axe", true, v -> true);
    public EnableSetting pickShovel = setting("shovel", true, v -> true);
    public EnableSetting pickRod = setting("fishrod", true, v -> true);
    public EnableSetting pickSpawnegg = setting("spawnegg", true, v -> true);
    public EnableSetting pickWater = setting("water", true, v -> true);
    public IntSetting waterBuckets = setting("water-buckets", 2, 1, 18, v -> true);
    public EnableSetting pickLava = setting("lava", true, v -> true);
    public IntSetting lavaBuckets = setting("lava-buckets", 2, 1, 18, v -> true);


    private int s_delay = 0;
    private int p_delay = 0;
    private boolean isFetched = false;
    private boolean fullWarning = false;
    private List<Integer> slotList = new ArrayList<>();

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
                    double bestWeaponDamage = ItemUtil.getItemDamage(ItemUtil.getBestWeapon());

                    int bestHelmetSlot = -1;
                    double bestHelmetValue = ItemUtil.getTotalArmorReduction(ItemUtil.getBestHelmet());

                    int bestChestSlot = -1;
                    double bestChestValue = ItemUtil.getTotalArmorReduction(ItemUtil.getBestChestplate());

                    int bestLeggingsSlot = -1;
                    double bestLeggingsValue = ItemUtil.getTotalArmorReduction(ItemUtil.getBestLeggings());

                    int bestBootsSlot = -1;
                    double bestBootsValue = ItemUtil.getTotalArmorReduction(ItemUtil.getBestBoots());

                    int bestPickaxeSlot = -1;
                    double bestPickaxeSpeed = ItemUtil.getToolMiningSpeed(ItemUtil.getBestTool(ItemUtil.ToolType.PICKAXE), Blocks.stone);

                    int bestAxeSlot = -1;
                    double bestAxeSpeed = ItemUtil.getToolMiningSpeed(ItemUtil.getBestTool(ItemUtil.ToolType.AXE), Blocks.planks);

                    int bestShovelSlot = -1;
                    double bestShovelSpeed = ItemUtil.getToolMiningSpeed(ItemUtil.getBestTool(ItemUtil.ToolType.SHOVEL), Blocks.dirt);

                    int waterCount = ItemUtil.getTotalItemCount(Items.water_bucket);
                    int lavaCount = ItemUtil.getTotalItemCount(Items.lava_bucket);
                    int rodCount = ItemUtil.getTotalItemCount(Items.fishing_rod);

                    for (int i = 0; i < chestSize; i++) {
                        ItemStack itemStack = chest.getLowerChestInventory().getStackInSlot(i);
                        if (itemStack == null) continue;

                        Item item = itemStack.getItem();
                        if (item instanceof ItemSword && pickSword.getValue()) {
                            double dmg = ItemUtil.getItemDamage(itemStack);
                            if (dmg > bestWeaponDamage) {
                                bestWeaponDamage = dmg;
                                bestWeaponSlot = i;
                            }
                        } else if (item instanceof ItemArmor && pickArmor.getValue()) {
                            ItemArmor armor = (ItemArmor) item;
                            double value = ItemUtil.getTotalArmorReduction(itemStack);
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
                        } else if (item instanceof ItemPickaxe && pickPickaxe.getValue()) {
                            double speed = ItemUtil.getToolMiningSpeed(itemStack, Blocks.stone);
                            if (speed > bestPickaxeSpeed) {
                                bestPickaxeSpeed = speed;
                                bestPickaxeSlot = i;
                            }
                        } else if (item instanceof ItemAxe && pickAxe.getValue()) {
                            double speed = ItemUtil.getToolMiningSpeed(itemStack, Blocks.planks);
                            if (speed > bestAxeSpeed) {
                                bestAxeSpeed = speed;
                                bestAxeSlot = i;
                            }
                        } else if (item instanceof ItemSpade && pickShovel.getValue()) {
                            double speed = ItemUtil.getToolMiningSpeed(itemStack, Blocks.dirt);
                            if (speed > bestShovelSpeed) {
                                bestShovelSpeed = speed;
                                bestShovelSlot = i;
                            }
                        } else if (item instanceof ItemFishingRod && pickRod.getValue() && rodCount < 1) {
                            slotList.add(i);
                            rodCount++;
                        } else if (item == Items.water_bucket && pickWater.getValue() && waterCount < waterBuckets.getValue()) {
                            slotList.add(i);
                            waterCount++;
                        } else if (item == Items.lava_bucket && pickLava.getValue() && lavaCount < lavaBuckets.getValue()) {
                            slotList.add(i);
                            lavaCount++;
                        } else if (item instanceof ItemEnderPearl ||
                                item instanceof ItemPotion ||
                                (item instanceof ItemMonsterPlacer && pickSpawnegg.getValue()) ||
                                (item instanceof ItemFood && ItemUtil.isGoodFood(item)) ||
                                (item instanceof ItemBlock && ((ItemBlock) item).getBlock().getMaterial().isSolid())
                        ) {
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
                    int windowId = chest.windowId;

                    while (!slotList.isEmpty()) {
                        int index = randomOrder.getValue() ? intRandom(0, slotList.size() - 1) : 0;
                        int slotid = slotList.get(index);

                        PlayerUtil.ClickWindow(windowId, slotid, 0, 1);
                        p_delay = intRandom(minDelay.getValue(), maxDelay.getValue());
                        slotList.remove(index);
                        if (p_delay > 0) {
                            return;
                        }

                        // sendCustomPrefixMessage("DEBUG " + this.name,"Action - " + debug_action + "; Click");
                    }
                }
            }
        }
    }
}
