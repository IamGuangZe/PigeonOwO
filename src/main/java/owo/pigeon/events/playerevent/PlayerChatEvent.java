package owo.pigeon.events.playerevent;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Fired when the client sends a chat message.
 * Can be cancelled to prevent the message from being sent to the server.
 */
@Cancelable
public class PlayerChatEvent extends Event {

    public String message;

    public PlayerChatEvent(String message) {
        this.message = message;
    }

    /**
     * Get the message being sent
     * @return The chat message as a string.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Set the message to send
     * @param message The chat message as a string.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get the player who is sending the message.
     * @return The player's instance.
     */
    public EntityPlayerSP getPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }
}
