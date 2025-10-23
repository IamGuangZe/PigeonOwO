package owo.pigeon.injections.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import owo.pigeon.features.modules.movement.SafeWalk;
import owo.pigeon.utils.ModuleUtil;

@Mixin(Entity.class)
public class MixinEntity {
    @ModifyVariable(method = "moveEntity", at = @At("STORE"), ordinal = 0)
    private boolean modifyFlag(boolean originalFlag) {
        Entity self = (Entity) (Object) this; // 强制转换为目标类
        return self.onGround
                && (ModuleUtil.isEnable(SafeWalk.class) || self.isSneaking())
                && self instanceof EntityPlayer;
    }
}
