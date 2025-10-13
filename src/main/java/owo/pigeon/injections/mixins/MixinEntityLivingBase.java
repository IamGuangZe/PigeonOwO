package owo.pigeon.injections.mixins;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import owo.pigeon.features.modules.movement.LegitSpeed;
import owo.pigeon.features.modules.player.DelayRemover;
import owo.pigeon.utils.ModuleUtil;

import java.util.Map;

@Mixin(EntityLivingBase.class)
public class MixinEntityLivingBase {
    @Shadow private int jumpTicks;

    @Shadow @Final private Map<Integer, PotionEffect> activePotionsMap;

    @Inject(method = "onLivingUpdate", at = @At("HEAD"))
    public void noJumpDelay(CallbackInfo ci) {
        if (ModuleUtil.isEnable(LegitSpeed.class) ||
                (ModuleUtil.isEnable(DelayRemover.class) && ((DelayRemover)ModuleUtil.getModule(DelayRemover.class)).noJumpDelay.getValue())){
            jumpTicks = 0;
        }
    }

    /*@Inject(method = "isPotionActive(Lnet/minecraft/potion/Potion;)Z", at = @At("HEAD"), cancellable = true)
    public void noblindness (Potion p_isPotionActive_1_, CallbackInfoReturnable<Boolean> cir) {
        if (p_isPotionActive_1_ == Potion.blindness) {
            if (ModuleUtil.isEnable(Camera.class) && ((Camera)ModuleUtil.getModule(Camera.class)).noBlindness.getValue() && !ModuleUtil.isEnable(LividSolver.class)) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "isPotionActive(I)Z", at = @At("HEAD"), cancellable = true)
    public void noblindness (int p_isPotionActive_1_, CallbackInfoReturnable<Boolean> cir) {
        if (p_isPotionActive_1_ == Potion.blindness.id) {
            if (ModuleUtil.isEnable(Camera.class) && ((Camera)ModuleUtil.getModule(Camera.class)).noBlindness.getValue() && !ModuleUtil.isEnable(LividSolver.class)) {
                cir.setReturnValue(false);
            }
        }
    }*/
}
