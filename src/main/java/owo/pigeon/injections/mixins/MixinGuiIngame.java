package owo.pigeon.injections.mixins;

import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import owo.pigeon.events.renderevent.Render2DEvent;

@Mixin(GuiIngame.class)
public class MixinGuiIngame {

    @Inject(method = "renderTooltip", at = @At(value = "RETURN"))
    public void onRenderTooltip(ScaledResolution p_renderTooltip_1_, float partialTicks, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new Render2DEvent());  // 发送事件
    }
}
