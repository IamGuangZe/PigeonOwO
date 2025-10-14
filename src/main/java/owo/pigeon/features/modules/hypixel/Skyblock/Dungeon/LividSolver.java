package owo.pigeon.features.modules.hypixel.Skyblock.Dungeon;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import owo.pigeon.features.Category;
import owo.pigeon.features.Module;
import owo.pigeon.settings.EnableSetting;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.OtherUtil;
import owo.pigeon.utils.RenderUtil;
import owo.pigeon.utils.hypixel.skyblock.Floor;
import owo.pigeon.utils.hypixel.skyblock.SkyblockUtil;

import java.awt.*;

import static owo.pigeon.utils.WorldUtil.isNotNull;

public class LividSolver extends Module {
    public LividSolver() {
        super("LividSolver", Category.HYPIXEL, -1);
    }

    public EnableSetting hideFakeLivid = setting("hidefake", true, "", v -> true);

    private boolean isBlindness = false;
    private boolean isGotLivid = false;
    private String lividName = "";
    private String lividColor = "";
    private int colorIndex = -1;

    @Override
    public void onUpdate() {
        if (isNotNull()) {
            if (SkyblockUtil.isFloor(Floor.F5) || SkyblockUtil.isFloor(Floor.M5)) {
                if (SkyblockUtil.isInBoss(5)) {

                    if (!isGotLivid) {
                        colorIndex = getColorIndex();

                        if (mc.thePlayer.isPotionActive(Potion.blindness)) {
                            isBlindness = true;

                        } else {
                            if (isBlindness) {
                                isBlindness = false;
                                isGotLivid = true;
                                lividName = getLividName();
                                lividColor = getLividColor().substring(0, 2);
                                ChatUtil.sendCustomPrefixMessage(this.name,"Livid Color was " + getLividColor());
                            }
                        }

                    /*if (mc.thePlayer.isPotionActive(Potion.blindness)) {
                        if (!isBlindness) {
                            setBlock(new BlockPos(5,108,25), Blocks.gold_block);
                            isBlindness = true;
                        } else {
                            if (getColorIndex() != -1) {
                                isBlindness = false;
                                isGotLivid = true;
                                lividName = getLividName();
                                lividColor = getLividColor().substring(0, 2);
                                ChatUtil.sendCustomPrefixMessage(this.name,"Livid Color was " + getLividColor());
                            }
                        }
                    }*/
                    }
                }
            }else {
                reload();
            }
        }
    }

    @Override
    public void onRender3D() {
        if (isGotLivid) {
            for (Entity entity : mc.theWorld.loadedEntityList) {
                if (entity instanceof EntityOtherPlayerMP) {
                    String playername = OtherUtil.removeColor(entity.getName());
                        /*if (playername.equals(lividName))
                            RenderUtil.drawOutlinedBoxEsp(entity,new Color(255,0,0));*/
                    if (playername.contains("Livid") && !playername.equals(lividName)) {
                        mc.theWorld.removeEntity(entity);
                    }
                }

                if (entity instanceof EntityArmorStand) {
                    EntityArmorStand e = (EntityArmorStand) entity;
                    if (e.hasCustomName()) {
                        if (OtherUtil.removeColorA(e.getCustomNameTag()).contains(lividColor + "&l")) {
                            RenderUtil.drawOutlinedBoxEsp(new AxisAlignedBB(entity.posX - 0.5D, entity.posY - 2.0D, entity.posZ - 0.5D, entity.posX + 0.5D, entity.posY, entity.posZ + 0.5D), new Color(255, 0, 0));
                        }
                    }
                }

            }
        }
    }

    @Override
    public void onWorldLoad() {
        reload();
    }

    private void reload() {
        isBlindness = false;
        isGotLivid = false;
        lividName = "";
        lividColor = "";
    }

    private int getColorIndex() {
        BlockPos pos = new BlockPos(5, 108, 25);
        Block b = mc.theWorld.getBlockState(pos).getBlock();
        if (b instanceof BlockColored) {
            BlockColored blockColored = (BlockColored) b;
            return blockColored.getMetaFromState(mc.theWorld.getBlockState(pos));
        }
        return -1;
    }

    private String getLividColor() {
        switch (colorIndex) {
            case 0:
                return "&fWHITE";
            case 2:
                return "&dPINK";
            case 4:
                return "&eYELLOW";
            case 5:
                return "&aGREEN";
            case 7:
                return "&7GRAY";
            case 10:
                return "&5PURPLE";
            case 11:
                return "&9BLUE";
            case 13:
                return "&2DARK GREEN";
            case 14:
                return "&cRED";
            default:
                return "";
        }
    }

    private String getLividName() {
        switch (colorIndex) {
            case 0:
                return "Vendetta Livid";
            case 2:
                return "Crossed Livid";
            case 4:
                return "Arcade Livid";
            case 5:
                return "Smile Livid";
            case 7:
                return "Doctor Livid";
            case 10:
                return "Purple Livid";
            case 11:
                return "Scream Livid";
            case 13:
                return "Frog Livid";
            case 14:
                return "Hockey Livid";
            default:
                return "";
        }
    }
}