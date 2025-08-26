package owo.pigeon.injections.mixins;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import owo.pigeon.commands.CommandManager;
import owo.pigeon.events.playerevent.PlayerChatEvent;


@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {
    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void CommandDetection(String message, CallbackInfo ci) {
        if (CommandManager.isSay) {
            CommandManager.isSay = false;
        } else {
            if (MinecraftForge.EVENT_BUS.post(new PlayerChatEvent(message))) ci.cancel();
        }
    }
}



//    @Shadow
//    public NetHandlerPlayClient sendQueue;
//    /**
//     * @author GuangZe
//     * @reason Intercept messages through sendChatMessage
//     */
//    @Overwrite
//    public void sendChatMessage(String message) {
//        //ChatUtil.sendMessage("MixinEntityPlayerSP sendChatMessage Test Message");
//        //ChatUtil.sendMessageWithoutColor("You are sending Message : "+message);
//        if (CommandManager.isCommand(message) && !CommandManager.isSay) {
//            CommandManager.RunCommand(message);
//        } else {
//            this.sendQueue.addToSendQueue(new C01PacketChatMessage(message));
//            CommandManager.isSay = false;
//        }
//    }



