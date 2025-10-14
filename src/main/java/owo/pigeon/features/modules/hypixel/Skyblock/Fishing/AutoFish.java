package owo.pigeon.features.modules.hypixel.Skyblock.Fishing;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.settings.EnableSetting;
import owo.pigeon.settings.IntSetting;
import owo.pigeon.settings.ModeSetting;
import owo.pigeon.utils.FontUtil;
import owo.pigeon.utils.OtherUtil;
import owo.pigeon.utils.PlayerUtil;

import java.util.List;

import static owo.pigeon.utils.WorldUtil.isNotNull;

public class AutoFish extends Module {
    public AutoFish() {
        super("AutoFish", Category.HYPIXEL, -1);
    }

    // 石山代码 - 2024.12.27

    public enum checkModeEnum {
        SPLASHSOUND, NOTESOUND, ARMORSTANDNAME
    }
    public enum fishModeEnum {
        PACKET, RIGHTCLICK
    }

    public ModeSetting<checkModeEnum> checkMode = setting("check-mode", checkModeEnum.ARMORSTANDNAME,"Hook detection mode.", v->true);
    public ModeSetting<fishModeEnum> FishMode = setting("fish-mode",fishModeEnum.PACKET,"", v->true);
    public EnableSetting reThrow = setting("rethrow",true,"Rethrow hook when reel.",v->true);
    public IntSetting reThrowTick = setting("rethrowtick",10,0,20,"Rethrow hook time.",v->true);
    public EnableSetting rotate = setting("autorotate", true,"", v->true);
    public EnableSetting move = setting("automove", true,"", v->true);

    private int tick = 999;

    private boolean isThrow = false;
    private boolean isThrowA = false;
    private int throwTick = 0;

    private boolean isRotate = false;
    private boolean ismove = false;

    @Override
    public void onEnable() {
        tick = reThrowTick.getValue() + 1;
        isThrow = isThrow();
    }

    @Override
    public void onDisable() {
        if (!Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())){
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
        }
    }

    @Override
    public void onUpdate() {
        if (isNotNull()) {
            if (tick == 0) {
                if (move.getValue()) {
                    KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), true);
                    if (ismove) {
                        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode(), true);
                    } else {
                        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode(), true);
                    }
                }

                if (rotate.getValue()) {
                    if (isRotate) {
                        mc.thePlayer.rotationYaw += 0.3F;
                        mc.thePlayer.rotationPitch += 0.3F;
                    } else {
                        mc.thePlayer.rotationYaw -= 0.3F;
                        mc.thePlayer.rotationPitch -= 0.3F;
                    }
                    isRotate = !isRotate;
                }

            } else if (tick == 1) {
                if (move.getValue()) {
                    KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), false);
                    if (ismove) {
                        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode(), false);
                    } else {
                        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode(), false);
                    }
                    ismove = !ismove;
                }
            }


            if (isHeldRod()) {
                if (reThrow.getValue() && reThrowTick.getValue() == tick) {
                    useRod();
                    isThrow = true;
                }
            } else {
                isThrow = false;
            }
        }

        tick ++;
        if (isThrow) {
            throwTick++;
        } else {
            throwTick = 0;
        }
    }

    @SubscribeEvent
    public void onSoundReceived(PlaySoundEvent event) {
        if (checkMode.getValue() == checkModeEnum.SPLASHSOUND && isNotNull()) {
            if (event.name.equals("game.player.swim.splash")) {
                float posX = event.result.getXPosF();
                float posY = event.result.getYPosF();
                float posZ = event.result.getZPosF();

                List<EntityFishHook> hooks = Minecraft.getMinecraft().theWorld.getEntitiesWithinAABB(EntityFishHook.class,
                        new AxisAlignedBB(posX - (0.5 / 2d), posY - (0.5 / 2d), posZ - (0.5 / 2d),
                                posX + (0.5 / 2d), posY + (0.5 / 2d), posZ + (0.5 / 2d)), null);
                for (EntityFishHook hook : hooks) {
                    if (hook.angler == mc.thePlayer) {
                        useRod();
                        isThrow = false;
                        tick = 0;
                        throwTick = 0;
                    }
                }
            }
        }

        if (checkMode.getValue() == checkModeEnum.NOTESOUND && isNotNull()) {
            if (event.name.equals("note.pling") && event.sound.getPitch() == 1) {
                useRod();
                isThrow = false;
                tick = 0;
                throwTick = 0;
            }
        }
    }

    @Override
    public void onRightClick() {
        isThrow = !isThrow();
    }

    @Override
    public void onRender2D() {
        FontUtil.drawStringWithShadow("Auto Fish", 5, 5);
        FontUtil.drawStringWithShadow("Is Throw Hook : " + isThrow, 5, 5 + h);
        FontUtil.drawStringWithShadow("Throw Tick : " + throwTick, 5, 5 + h * 2);
        FontUtil.drawStringWithShadow("tick : " + tick, 5, 5 + h * 3);
    }

    @Override
    public void onRender3D() {
        if (checkMode.getValue() == checkModeEnum.ARMORSTANDNAME) {
            for (Entity entity : mc.theWorld.loadedEntityList) {
                if (entity instanceof EntityArmorStand) {
                    EntityArmorStand armorStand = (EntityArmorStand) entity;
                    if (armorStand.hasCustomName()) {
                        if (OtherUtil.removeColorA(armorStand.getCustomNameTag()).equals("&e&l?")) {
                            isThrowA = true;
                        }

                        if (OtherUtil.removeColorA(armorStand.getCustomNameTag()).equals("&c&l!!!") && isThrowA) {
                            useRod();
                            isThrowA = false;
                            isThrow = false;
                            tick = 0;
                            throwTick = 0;
                        }
                    }
                }
            }
        }
    }

    private void useRod () {
        if (FishMode.getValue() == fishModeEnum.PACKET) {
            mc.playerController.sendUseItem(mc.thePlayer,mc.theWorld,mc.thePlayer.getHeldItem());
        } else if (FishMode.getValue() == fishModeEnum.RIGHTCLICK) {
            PlayerUtil.rightClick();
        }
    }

    private boolean isThrow() {
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityFishHook) {
                if (((EntityFishHook) entity).angler == mc.thePlayer) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isHeldRod () {
        if (mc.thePlayer.getHeldItem() == null) {
            return false;
        }
        return mc.thePlayer.getHeldItem().getItem() instanceof ItemFishingRod;
    }
}
