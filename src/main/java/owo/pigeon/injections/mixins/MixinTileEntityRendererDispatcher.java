package owo.pigeon.injections.mixins;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import owo.pigeon.features.modules.render.SkullESP;
import owo.pigeon.utils.ModuleUtil;
import owo.pigeon.utils.WorldUtil;

@Mixin(TileEntityRendererDispatcher.class)
public class MixinTileEntityRendererDispatcher {
    @Inject(method = "renderTileEntityAt(Lnet/minecraft/tileentity/TileEntity;DDDFI)V", at = @At("HEAD"))
    private void onRenderTileEntity(TileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage, CallbackInfo ci) {
        if (ModuleUtil.isEnable(SkullESP.class) && tileEntityIn instanceof TileEntitySkull) {
            String uuid = WorldUtil.getSkullUUID(tileEntityIn);
            if (uuid != null) {
                SkullESP.giftskull.add(tileEntityIn.getPos());
            }
        }
    }
}
