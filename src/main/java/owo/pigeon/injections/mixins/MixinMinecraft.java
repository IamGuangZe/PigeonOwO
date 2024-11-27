package owo.pigeon.injections.mixins;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import owo.pigeon.events.LeftClickEvent;
import owo.pigeon.events.RightClickEvent;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "clickMouse", at = @At("HEAD"), cancellable = true)
    private void clickMouse(CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new LeftClickEvent())) ci.cancel();
    }


//    @Inject(method = "rightClickMouse", at = @At("HEAD"))
//    private void rightClickMouse(CallbackInfo ci) {
//        if (MinecraftForge.EVENT_BUS.post(new RightClickEvent())) ci.cancel();
//    }

    @Inject(method = "rightClickMouse", at = @At("RETURN"), cancellable = true)
    private void rightClickMouse(CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new RightClickEvent())) ci.cancel();
    }
}
