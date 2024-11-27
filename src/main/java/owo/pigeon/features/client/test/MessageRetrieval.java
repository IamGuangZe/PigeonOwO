package owo.pigeon.features.client.test;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import owo.pigeon.modules.Module;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.CheckUtil;

public class MessageRetrieval extends Module {
    public MessageRetrieval() {
        super("MessageRetrieval", Keyboard.KEY_L);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatReceive(ClientChatReceivedEvent event) {

        if (MessageRetrieval.this.isEnable()) {
            String message = ChatUtil.removeColor(event.message.getFormattedText());
            ChatUtil.sendMessage("Received a new message!");
            ChatUtil.sendMessage("type : " + String.valueOf(event.type) + " , IsOnlyWhiteSpace : " + CheckUtil.OnlyWhitespace(message));
            ChatUtil.sendMessageWithoutColor("Message : " + message);
        }
    }
}