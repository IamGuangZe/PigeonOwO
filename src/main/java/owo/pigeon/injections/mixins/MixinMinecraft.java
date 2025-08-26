package owo.pigeon.injections.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import owo.pigeon.events.clickevent.LeftClickEvent;
import owo.pigeon.events.clickevent.RightClickEndEvent;
import owo.pigeon.events.clickevent.RightClickEvent;
import owo.pigeon.features.modules.combat.AutoClicker;
import owo.pigeon.features.modules.hypixel.Skyblock.Dungeon.DungeonBrush;
import owo.pigeon.features.modules.player.DelayRemover;
import owo.pigeon.utils.ModuleUtil;


@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Shadow private int leftClickCounter;
    @Shadow private int rightClickDelayTimer;

    @Shadow public GameSettings gameSettings;

    @Inject(method = "clickMouse", at = @At("HEAD"), cancellable = true)
    private void clickMouse(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new LeftClickEvent());

        if (ModuleUtil.isEnable(DungeonBrush.class)) {
            ci.cancel();
        }

        if ((ModuleUtil.isEnable(AutoClicker.class) && ((AutoClicker) ModuleUtil.getModule(AutoClicker.class)).leftClick.getValue()) ||
                (ModuleUtil.isEnable(DelayRemover.class) && ((DelayRemover)ModuleUtil.getModule(DelayRemover.class)).noClickDelay.getValue())) {
            this.leftClickCounter = 0;
        }
    }

    @Inject(method = "rightClickMouse", at = @At("HEAD"), cancellable = true)
    private void rightClickMouse(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new RightClickEvent());

        if (ModuleUtil.isEnable(DungeonBrush.class)) {
            rightClickDelayTimer = 4;
            ci.cancel();
        }
    }

    @Inject(method = "rightClickMouse", at = @At("RETURN"))
    private void rightClickMousereturn(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new RightClickEndEvent());
        if (ModuleUtil.isEnable(DelayRemover.class) && ((DelayRemover)ModuleUtil.getModule(DelayRemover.class)).noRightClickDelay.getValue()) {
            rightClickDelayTimer = 0;
        }
    }

    @Inject(method = "sendClickBlockToController",at = @At("HEAD"), cancellable = true)
    private void noswinghand(boolean leftClick, CallbackInfo ci) {
        if (ModuleUtil.isEnable(DungeonBrush.class)) {
            ci.cancel();
        }
    }

    /*@Inject(method = "runTick", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/settings/KeyBinding;isPressed()Z",ordinal = 1))
    public void removethirdpersonfrontfacing(CallbackInfo ci) {
        if (ModuleUtil.isEnable(Camera.class) && Camera.removethirdpersonfrontfacing) {
            if (gameSettings.thirdPersonView == 2) {
                gameSettings.thirdPersonView = 0;
            }
        }
    }*/
}
