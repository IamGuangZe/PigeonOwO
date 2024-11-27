package owo.pigeon.events;

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

    private String message;

    public PlayerChatEvent(String message) {
        this.message = message;
    }

    /**
     * 获取要发送的消息
     * @return 返回聊天消息字符串
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * 设置要发送的消息
     * @param message 设置聊天消息字符串
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取发送消息的玩家
     * @return 返回玩家实例
     */
    public EntityPlayerSP getPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }
}
