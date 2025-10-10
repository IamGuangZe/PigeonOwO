package owo.pigeon.injections.mixins;

import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import owo.pigeon.features.modules.render.BedESP;
import owo.pigeon.utils.ModuleUtil;

@Mixin(BlockRendererDispatcher.class)
public class MixinBlockRendererDispatcher {
    @Inject(method = "renderBlock", at = @At("HEAD"))
    private void renderBlock(IBlockState state, BlockPos pos, IBlockAccess blockAccess, WorldRenderer worldRendererIn, CallbackInfoReturnable<Boolean> cir) {
        if (ModuleUtil.isEnable(BedESP.class) && state.getBlock() instanceof BlockBed) {
            // ChatUtil.sendCustomPrefixMessage("DEBUG BEDESP","ADD BED - X: " + pos.getX() + " Y: " + pos.getY() + " Z: " + pos.getY());
            BedESP.beds.add(new BlockPos(pos));
        }
    }
}
