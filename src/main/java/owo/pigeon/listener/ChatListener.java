package owo.pigeon.listener;

import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import owo.pigeon.utils.ChatUtil;

// https://github.com/flowerinsnow-lights-opensource/ClickTranslateBaidu/blob/master/1.8.9-forge/src/main/java/online/flowerinsnow/clicktranslate/listener/ChatListener.java
@SideOnly(Side.CLIENT)
public final class ChatListener {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatReceive(ClientChatReceivedEvent event) {
        if (event.type == 0 || event.type == 1) { // Standard Text Message
            String message = ChatUtil.removeColor(getFormattedText(event));
            ChatComponentText component = new ChatComponentText(ChatUtil.parseColor(" &7[T]"));
            component.setChatStyle(new ChatStyle()
                    .setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, message))
                    .setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentTranslation("Test")))
            );
            event.message.appendSibling(component);
        }
    }

    public static String getFormattedText(ClientChatReceivedEvent event) {
        return event.message.getFormattedText();
    }
}