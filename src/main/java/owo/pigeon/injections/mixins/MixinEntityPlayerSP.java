package owo.pigeon.injections.mixins;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C01PacketChatMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import owo.pigeon.commands.CommandManager;


@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {

    @Shadow
    public NetHandlerPlayClient sendQueue;

    /**
     * @author GuangZe
     * @reason Intercept messages through sendChatMessage
     */
    @Overwrite
    public void sendChatMessage(String message) {

        //ChatUtil.sendMessage("MixinEntityPlayerSP sendChatMessage Test Message");
        //ChatUtil.sendMessageWithoutColor("You are sending Message : "+message);

        if (CommandManager.isCommand(message) && !CommandManager.isSay) {

            CommandManager.RunCommand(message);

        } else {

            this.sendQueue.addToSendQueue(new C01PacketChatMessage(message));
            // 执行原版方法，发送消息
            // 如果你需要修改消息内容，可以在这里做处理
            // 比如，添加一些自定义的内容：
            // message = message + " [Modified]"; // 修改消息内容

            CommandManager.isSay = false;
        }
    }
}



//                        -----ci.cancel()会crash-----
// 使用@Inject在sendChatMessage调用前后插入代码
//    @Inject(method = "sendChatMessage", at = @At("HEAD"))
//    public void sendChatMessage(String message, CallbackInfo ci) {
//        // 打印调试信息，验证是否进入到我们的Mixin
//        ChatUtil.sendMessage("MixinEntityPlayerSP sendChatMessage Test Message");
//        ChatUtil.sendMessageWithoutColor("You are sending Message : " + message);
//
//        if (message.contains("test")) {  // 示例：如果消息包含特定的词语
//            ChatUtil.sendMessage("该消息包含禁止的词语，取消发送！");
//            ci.cancel();
//        }
//    }



