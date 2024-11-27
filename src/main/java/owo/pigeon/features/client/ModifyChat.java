package owo.pigeon.features.client;

import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import owo.pigeon.modules.Module;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.CheckUtil;
import owo.pigeon.utils.HypixelUtil;

public class ModifyChat extends Module {
    public ModifyChat() {
        super("ModifyChat", Keyboard.KEY_M);
    }

    public static boolean copychat = true;
    public static boolean joinlobby = false;
    public static boolean zombiesgold = false;
    public static boolean zombierepair = false;
    public static boolean coins = false;
    public static boolean emptymessage = false;



    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatReceive(ClientChatReceivedEvent event) {
        String message = ChatUtil.removeColor(getFormattedText(event));

        if ((event.type == 0 || event.type == 1)) {

            if ((!joinlobby && CheckUtil.isContainsRegex(HypixelUtil.JOINLOBBY,message)) ||
                    (!zombiesgold && CheckUtil.isContainsRegex(HypixelUtil.GOLD,message)) ||
                    (!zombierepair && CheckUtil.isContainsRegex(HypixelUtil.REPAIREDWINDOW,message)) ||
                    (!coins && CheckUtil.isContainsRegex(HypixelUtil.COIN,message)) ||
                    (!emptymessage && CheckUtil.OnlyWhitespace(message))
            ) {
                event.setCanceled(true);
                return;
            }

            if (copychat && !CheckUtil.OnlyWhitespace(message)) {
                ChatComponentText component = new ChatComponentText(ChatUtil.parseColor(" &7[T]"));
                component.setChatStyle(new ChatStyle()
                        .setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ".copy " + message))
                );
                event.message.appendSibling(component);
            }
        }
    }

    public static String getFormattedText(ClientChatReceivedEvent event) {
        return event.message.getFormattedText();
    }
}
