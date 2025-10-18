package owo.pigeon.features.modules;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import owo.pigeon.settings.SettingDesigner;
import owo.pigeon.utils.ChatUtil;

public class Module extends SettingDesigner {
    public static Minecraft mc = Minecraft.getMinecraft();
    public final int h = mc.fontRendererObj.FONT_HEIGHT + 1;
    public final String name;
    public final Category category;
    public int Key;
    private boolean Enable;


    public Module(String name, Category category, int key) {
        this.name = name;
        this.category = category;
        Key = key;
    }

    public void onTick() { }
    public void onEnable() { }
    public void onDisable() { }
    public void onRender2D() { }
    public void onRender3D() { }
    public void onWorldLoad() { }
    public void onLeftClick() { }
    public void onRightClick() { }
    public void onRightClickEnd() { }

    public boolean isEnable() {
        return Enable;
    }

    public final void enable() {
        Enable = true;
        MinecraftForge.EVENT_BUS.register(this);
        ChatUtil.sendMessage(this.name + " has §aEnabled!");
        onEnable();
    }

    public final void disable() {
        Enable = false;
        MinecraftForge.EVENT_BUS.unregister(this);
        ChatUtil.sendMessage(this.name + " has §4Disabled!");
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
