package owo.pigeon.features.modules.client.test;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.OtherUtil;

public class MessageRetrieval extends Module {
    public MessageRetrieval() {
        super("MessageRetrieval", Category.CLIENT, -1);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatReceive(ClientChatReceivedEvent event) {

        if (this.isEnable()) {
            String message = OtherUtil.removeColor(event.message.getFormattedText());
            ChatUtil.sendMessage("Received a new message!");
            ChatUtil.sendMessage("type : " + String.valueOf(event.type) + " , IsOnlyWhiteSpace : " + OtherUtil.OnlyWhitespace(message));
            ChatUtil.sendMessageWithoutColor("Message : " + message);
        }
    }
}