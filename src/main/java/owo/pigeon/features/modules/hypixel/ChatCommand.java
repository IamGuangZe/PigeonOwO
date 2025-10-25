package owo.pigeon.features.modules.hypixel;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.settings.EnableSetting;
import owo.pigeon.settings.StringSetting;
import owo.pigeon.utils.OtherUtil;

public class ChatCommand extends Module {
    public ChatCommand() {
        super("ChatCommand", Category.HYPIXEL, -1);
    }

    public StringSetting prefix = setting("prefix", "!", v -> true);

    public EnableSetting partyChat = setting("party-chat", true, v -> true);
    public EnableSetting guildChat = setting("guild-chat", true, v -> true);
    public EnableSetting privateChat = setting("private-chat", true, v -> true);


    public EnableSetting invite = setting("invite", true, v -> true);
    public EnableSetting warp = setting("warp", true, v -> true);
    public EnableSetting kick = setting("kick", true, v -> true);
    public EnableSetting allInvite = setting("all-invite", true, v -> true);
    public EnableSetting promote = setting("promote", true, v -> true);
    public EnableSetting demote = setting("demote", true, v -> true);
    public EnableSetting transfer = setting("transfer", true, v -> true);

    // Regex from Odin (https://github.com/odtheking/Odin/)
    private final String regex = "^(?:Party > (\\[[^]]*?])? ?(\\w{1,16})(?: [ቾ⚒])?: ?(.+)$|Guild > (\\[[^]]*?])? ?(\\w{1,16})(?: \\[([^]]*?)])?: ?(.+)$|From (\\[[^]]*?])? ?(\\w{1,16}): ?(.+)$)";

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatReceive(ClientChatReceivedEvent event) {
        String message = OtherUtil.removeColor(event.message.getFormattedText());

        if (event.type == 0) {
            String channel = getChannel(message);
            String sender = getSender(message, channel);
            String content = getContent(message, channel);

            if (channel.equals("Public") || sender == null || content == null) return;
            if (!content.startsWith(prefix.getValue())) return;

            String command = content.substring(prefix.getValue().length());
            if (command.trim().isEmpty()) return;

            String[] parts = command.split(" ");
            String commandName = parts[0];

            handleChatCommand(sender,commandName,channel);
        }
    }

    private void handleChatCommand(String sender, String command, String channel) {
        switch (command.toLowerCase()) {
            case "invite":
                if (invite.getValue() && channel.equals("Private")) {
                    mc.thePlayer.sendChatMessage("/p " + sender);
                }
                break;
            case "warp":
                if (warp.getValue() && channel.equals("Party")) {
                    mc.thePlayer.sendChatMessage("/p warp");
                }
                break;
            case "kick":
                if (kick.getValue() && channel.equals("Party")) {
                    mc.thePlayer.sendChatMessage("/p kick " + sender);
                }
                break;
            case "allinvite":
                if (allInvite.getValue() && channel.equals("Party")) {
                    mc.thePlayer.sendChatMessage("/p settings allinvite");
                }
                break;
            case "promote":
                if (promote.getValue() && channel.equals("Party")) {
                    mc.thePlayer.sendChatMessage("/p promote " + sender);
                }
                break;
            case "demote":
                if (demote.getValue() && channel.equals("Party")) {
                    mc.thePlayer.sendChatMessage("/p demote " + sender);
                }
                break;
            case "transfer":
            case "pt":
            case "ptme":
                if (transfer.getValue() && channel.equals("Party")) {
                    mc.thePlayer.sendChatMessage("/p transfer " + sender);
                }
                break;
        }
    }

    private String getChannel(String message) {
        if (message.startsWith("Party") && partyChat.getValue()) {
            return "Party";
        }
        if (message.startsWith("Guild") && guildChat.getValue()) {
            return "Guild";
        }
        if (message.startsWith("From") && privateChat.getValue()) {
            return "Private";
        }
        return "Public";
    }

    private String getSender(String message, String channel) {
        switch (channel) {
            case "Party":
                return OtherUtil.regexGetPart(regex, message, 2);
            case "Guild":
                return OtherUtil.regexGetPart(regex, message, 5);
            case "Private":
                return OtherUtil.regexGetPart(regex, message, 9);
        }
        return null;
    }

    private String getContent(String message, String channel) {
        switch (channel) {
            case "Party":
                return OtherUtil.regexGetPart(regex, message, 3);
            case "Guild":
                return OtherUtil.regexGetPart(regex, message, 7);
            case "Private":
                return OtherUtil.regexGetPart(regex, message, 10);
        }
        return null;
    }
}
