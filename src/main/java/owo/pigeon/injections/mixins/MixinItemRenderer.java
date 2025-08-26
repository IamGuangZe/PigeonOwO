package owo.pigeon.injections.mixins;

import net.minecraft.client.renderer.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import owo.pigeon.features.modules.render.Camera;
import owo.pigeon.utils.ModuleUtil;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {
    @Inject(method = "renderFireInFirstPerson" , at = @At("HEAD") , cancellable = true)
    public void noFireInFirstPerson(float p_renderFireInFirstPerson_1_, CallbackInfo ci) {
        if (ModuleUtil.isEnable(Camera.class) && ((Camera)ModuleUtil.getModule(Camera.class)).noFire.getValue()) {
            ci.cancel();
        }
    }
}
