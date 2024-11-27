package owo.pigeon.modules;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import owo.pigeon.utils.ChatUtil;

public class Module {
    public static Minecraft mc = Minecraft.getMinecraft();
    public final String Name;
    public int Key;
    private boolean Enable;

    public Module(String name, int key) {
        Name = name;
        Key = key;
    }

    public void onUpdate() { }
    public void onEnable() { }
    public void onDisable() { }
    public void onRightClick() { }

    public boolean isEnable() {
        return Enable;
    }

    public final void enable() {
        Enable = true;
        MinecraftForge.EVENT_BUS.register(this);
        ChatUtil.sendMessage(this.Name + " has §aEnabled!");
        onEnable();
    }

    public final void disable() {
        Enable = false;
        MinecraftForge.EVENT_BUS.unregister(this);
        ChatUtil.sendMessage(this.Name + " has §4Disabled!");
        onDisable();
    }

    public void toggle() {
        if (Enable){
            disable();
        }else {
            enable();
        }
    }

    public void setKey(int key) {
        this.Key = key;
    }
}
