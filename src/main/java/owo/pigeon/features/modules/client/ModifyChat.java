package owo.pigeon.features.modules.client;

import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import owo.pigeon.commands.CommandManager;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.utils.OtherUtil;
import owo.pigeon.utils.hypixel.HypixelData;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ModifyChat extends Module {
    public ModifyChat() {
        super("ModifyChat", Category.CLIENT, -1);
    }

    public static boolean hidemessage = true;
    public static boolean joinlobby = false;
    public static boolean zombiesgold = false;
    public static boolean zombierepair = false;
    public static boolean coins = false;
    public static boolean reward = false;
    public static boolean emptymessage = false;

    public static boolean time = true;
    public static boolean second = false;

    public static boolean copychat = true;



    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatReceive(ClientChatReceivedEvent event) {
        String rawmessage = getFormattedText(event);
        String message = OtherUtil.removeColor(rawmessage);

        if ((event.type == 0 || event.type == 1)) {

            if (hidemessage &&
                    (!joinlobby && OtherUtil.isContainsRegex(HypixelData.JOINLOBBY,message)) ||
                    (!zombiesgold && OtherUtil.isContainsRegex(HypixelData.GOLD,message)) ||
                    (!zombierepair && OtherUtil.isContainsRegex(HypixelData.REPAIREDWINDOW,message)) ||
                    (!coins && OtherUtil.isContainsRegex(HypixelData.COIN,message)) ||
                    (!reward && OtherUtil.isContainsRegex(HypixelData.REWARD,message)) ||
                    (!emptymessage && OtherUtil.OnlyWhitespace(message))
            ) {
                event.setCanceled(true);
                return;
            }

            if (time && !OtherUtil.OnlyWhitespace(message)) {
                SimpleDateFormat time;
                if (second) {
                    time = new SimpleDateFormat("HH:mm:ss");
                } else {
                    time = new SimpleDateFormat("HH:mm");
                }
                String timeprefix = OtherUtil.parseColor("&7[" + time.format(new Date()) + "] &r");
                ChatComponentText wrapper = new ChatComponentText(timeprefix);
                wrapper.appendSibling(event.message);
                event.message = wrapper;
                //event.message = new ChatComponentText(timeprefix + rawmessage);
            }

            if (copychat && !OtherUtil.OnlyWhitespace(event.message.getUnformattedText())) {
                String copyText = event.message.getFormattedText().replaceAll("§.", "");

                ChatComponentText component = new ChatComponentText(OtherUtil.parseColor(" &7[C]"));
                component.setChatStyle(new ChatStyle()
                        .setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,CommandManager.commandPrefix + "copy " + copyText))
                        .setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                new ChatComponentText("Click to Copy this message\n" + copyText)))
                );

                event.message.appendSibling(component);

                /*
                IChatComponent iChatComponent = event.message;
                ChatStyle chatStyle = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ".copy " + copyText));

                iChatComponent.appendSibling(
                        new ChatComponentText(OtherUtil.parseColor(" &7[C]"))
                                .setChatStyle(
                                        chatStyle.setChatHoverEvent(
                                                new HoverEvent(
                                                        HoverEvent.Action.SHOW_TEXT,
                                                        new ChatComponentText(new StringBuilder().insert(0,"Click to Copy this message\n" + copyText).toString())
                                                )
                                        )
                                )
                );
                */
            }
        }
    }

    public static String getFormattedText(ClientChatReceivedEvent event) {
        return event.message.getFormattedText();
    }
}
