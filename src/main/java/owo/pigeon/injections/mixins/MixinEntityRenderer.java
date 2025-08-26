package owo.pigeon.injections.mixins;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import owo.pigeon.events.renderevent.Render3DEvent;
import owo.pigeon.features.modules.render.Camera;
import owo.pigeon.utils.ModuleUtil;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

    @Inject(method = "renderWorldPass", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z", shift = At.Shift.BEFORE))
    private void renderWorldPass(int pass, float partialTicks, long finishTimeNano, CallbackInfo callbackInfo) {
        MinecraftForge.EVENT_BUS.post(new Render3DEvent());
    }

    @Inject(method = "hurtCameraEffect", at = @At("HEAD"), cancellable = true)
    public void noHurtCam(float effect, CallbackInfo ci) {
        if (ModuleUtil.isEnable(Camera.class) && ((Camera)ModuleUtil.getModule(Camera.class)).noHurtCam.getValue()) {
            ci.cancel();
        }
    }

    @ModifyConstant(method = "orientCamera", constant = @Constant(intValue = 8))
    public int CameraClip(int constant) {
        return (ModuleUtil.isEnable(Camera.class) && ((Camera)ModuleUtil.getModule(Camera.class)).camNoClip.getValue()) ? 0: constant;
    }
}