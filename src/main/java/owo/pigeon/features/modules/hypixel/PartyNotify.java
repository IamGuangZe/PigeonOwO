package owo.pigeon.features.modules.hypixel;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import owo.pigeon.features.modules.Category;
import owo.pigeon.features.modules.Module;
import owo.pigeon.settings.ModeSetting;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.OtherUtil;
import owo.pigeon.utils.hypixel.HypixelUtil;

import java.util.HashSet;
import java.util.Set;

import static owo.pigeon.utils.WorldUtil.isNotNull;

public class PartyNotify extends Module {
    public PartyNotify() {
        super("PartyNotify", Category.HYPIXEL, -1);
    }

    public enum detectionModeEnum {
        CHAT, ENTITYLIST;
    }

    public ModeSetting<detectionModeEnum> detectionMode = setting("detection-mode", detectionModeEnum.CHAT, "", v -> true);

    private Set<String> previousPlayers = new HashSet<>();
    private Set<String> newPlayersOnTick = new HashSet<>();

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatReceive(ClientChatReceivedEvent event) {
        if (detectionMode.getValue() == detectionModeEnum.CHAT) {
            String message = OtherUtil.removeColor(event.message.getFormattedText());

            if (event.type == 0) {
                String playerName = OtherUtil.regexGetPart("(.*) has joined \\(.*\\)!", message, 1);
                if (playerName != null) {
                    newPlayersOnTick.add(playerName);
                }
            }
        }
    }

    @Override
    public void onTick() {
        if (detectionMode.getValue() == detectionModeEnum.CHAT) {
            if (newPlayersOnTick.size() >= 2) {
                ChatUtil.sendCustomPrefixMessage(this.name, "Suspected Party Join (" + newPlayersOnTick.size() + "): " + String.join(", ", newPlayersOnTick));
            }

            newPlayersOnTick.clear();
        }


        if (detectionMode.getValue() == detectionModeEnum.ENTITYLIST) {
            if (isNotNull()) {

                Set<String> currentPlayers = new HashSet<>();

                for (EntityPlayer player : mc.theWorld.playerEntities) {
                    if (HypixelUtil.isNPC(player)) continue;

                    currentPlayers.add(player.getName());
                }

                if (previousPlayers.isEmpty()) {
                    previousPlayers.addAll(currentPlayers);
                    return;
                }

                Set<String> newPlayers = new HashSet<>(currentPlayers);
                newPlayers.removeAll(previousPlayers);

                if (newPlayers.size() >= 2) {
                    ChatUtil.sendCustomPrefixMessage(this.name, "Suspected Party Join (" + newPlayers.size() + "): " + String.join(", ", newPlayers));
                }

                previousPlayers.clear();
                previousPlayers.addAll(currentPlayers);
            }
        }
    }

    @Override
    public void onWorldLoad() {
        previousPlayers.clear();
        newPlayersOnTick.clear();
    }

    @Override
    public void onEnable() {
        previousPlayers.clear();
        newPlayersOnTick.clear();
    }
}

