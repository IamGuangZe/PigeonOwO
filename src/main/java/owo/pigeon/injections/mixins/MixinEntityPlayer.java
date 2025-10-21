package owo.pigeon.injections.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import owo.pigeon.events.playerevent.StopSprintEvent;
import owo.pigeon.features.modules.hypixel.ZombieHelper;
import owo.pigeon.utils.ModuleUtil;
import owo.pigeon.utils.hypixel.HypixelGames;
import owo.pigeon.utils.hypixel.HypixelUtil;

@Mixin(EntityPlayer.class)
public class MixinEntityPlayer {
    @Shadow
    protected boolean sleeping;

    @Inject(method = "attackTargetEntityWithCurrentItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;setSprinting(Z)V"))
    public void stopSprint(Entity p_attackTargetEntityWithCurrentItem_1_, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new StopSprintEvent());
    }

    @Inject(method = "isPlayerSleeping", at = @At("HEAD"), cancellable = true)
    public void easyRevive(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(this.sleeping && !(ModuleUtil.isEnable(ZombieHelper.class) && ((ZombieHelper) ModuleUtil.getModule(ZombieHelper.class)).easyRevive.getValue() && HypixelUtil.isInGame(HypixelGames.ZOMBIES)));
    }
}
