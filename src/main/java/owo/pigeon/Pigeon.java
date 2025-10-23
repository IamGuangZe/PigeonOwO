package owo.pigeon;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.lwjgl.opengl.Display;
import owo.pigeon.commands.CommandManager;
import owo.pigeon.configs.ConfigManager;
import owo.pigeon.features.modules.ModuleManager;
import owo.pigeon.features.utils.SafeMessage;
import owo.pigeon.gui.ClickGuiScreen;


@Mod(modid = Pigeon.MOD_ID,name = Pigeon.MOD_NAME,version = Pigeon.MOD_VER)
public class Pigeon {
    public static final String MOD_ID = "pigeonowo";
    public static final String MOD_NAME = "PigeonOWO";
    public static final String MOD_VER = "0.0.1";

    public static final Pigeon instance = new Pigeon();
    public static ModuleManager modulemanager;
    public static CommandManager commandmanager;
    public static ConfigManager configManager;
    public static ClickGuiScreen clickGuiScreen;

    public static final String watermark = Pigeon.MOD_NAME + " v" + Pigeon.MOD_VER + " by GuangZe233";


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Display.setTitle("Ciallo～(∠・ω< )⌒★ | Minecraft 1.8.9");
        MinecraftForge.EVENT_BUS.register(new SafeMessage());

        modulemanager = new ModuleManager();
        modulemanager.init();

        commandmanager = new CommandManager();
        commandmanager.init();

        clickGuiScreen = new ClickGuiScreen();

        configManager = new ConfigManager();
        configManager.init();
    }
}