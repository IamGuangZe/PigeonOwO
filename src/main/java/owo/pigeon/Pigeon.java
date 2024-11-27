package owo.pigeon;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.lwjgl.opengl.Display;
import owo.pigeon.commands.CommandManager;
import owo.pigeon.listener.ChatListener;
import owo.pigeon.modules.ModuleManager;


@Mod(modid = Pigeon.MOD_ID,name = Pigeon.MOD_NAME,version = Pigeon.MOD_VER)
public class Pigeon {
    public static final String MOD_ID = "pigeonowo";
    public static final String MOD_NAME = "PigeonOWO";
    public static final String MOD_VER = "0.0.1";

    public static final Pigeon instance = new Pigeon();
    public static ModuleManager modulemanager;
    public static CommandManager commandmanager;


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Display.setTitle("Ciallo～(∠・ω< )⌒★ | Minecraft 1.8.9");

//        MinecraftForge.EVENT_BUS.register(new ChatListener());

        modulemanager = new ModuleManager();
        modulemanager.init();

        commandmanager = new CommandManager();
        commandmanager.init();

    }
}