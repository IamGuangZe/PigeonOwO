package owo.pigeon.commands;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import owo.pigeon.commands.impl.*;
import owo.pigeon.commands.impl.test.GetCommand;
import owo.pigeon.events.playerevent.PlayerChatEvent;
import owo.pigeon.features.commands.Ping;
import owo.pigeon.features.commands.Rejoin;
import owo.pigeon.utils.ChatUtil;

import java.util.ArrayList;
import java.util.Arrays;

import static owo.pigeon.features.modules.Module.mc;

public class CommandManager {

    public static final ArrayList<Command> commands = new ArrayList<>();
    public static char chatPrefix = '>';
    public static boolean isSay = false;

    public void init() {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new Ping());
        MinecraftForge.EVENT_BUS.register(new Rejoin());

        commands.add(new BindCommand());
        //commands.add(new BrushCommand());
        commands.add(new CopyCommand());
        commands.add(new FillCommand());
        commands.add(new HelpCommand());
        commands.add(new LimboCommand());
        commands.add(new PingCommand());
        commands.add(new PrefixCommand());
        commands.add(new RejoinCommand());
        commands.add(new SayCommand());
        commands.add(new SetBlockCommand());
        commands.add(new SettingCommand());
        commands.add(new ToggleCommand());

        commands.add(new GetCommand());
    }

    // 判断是否为指令
    public static boolean isCommand(String message) {
        if (message.startsWith(String.valueOf(chatPrefix))){
            return true;
        } else {
            return false;
        }
    }

    // 执行指令
    public static void RunCommand(String message) {
        String command = message.substring(1);
        if (command.isEmpty()){
            ChatUtil.sendMessage("&cThe command cannot be empty!");
            return;
        }
        String[] parts = command.split(" ");
        String commandName = parts[0];
        boolean executed = false;
        for (Command Command : commands) {
            if (Command.getCommand().equalsIgnoreCase(commandName)) {
                Command.execute(Arrays.copyOfRange(parts,1,parts.length));
                executed = true;
            }
        }
        if (!executed) {
            ChatUtil.sendMessage("&cCommand not found!");
        }
    }

    @SubscribeEvent
    public void onCommand(PlayerChatEvent event) {
        if (event.message.startsWith(String.valueOf(chatPrefix))) {
            event.setCanceled(true);
            mc.ingameGUI.getChatGUI().addToSentMessages(event.message);
            String command = event.message.substring(1);
            if (command.isEmpty()) {
                ChatUtil.sendMessage("&cThe command cannot be empty!");
                return;
            }
            String[] parts = command.split(" ");
            String commandName = parts[0];
            boolean executed = false;
            for (Command Command : commands) {
                if (Command.getCommand().equalsIgnoreCase(commandName)) {
                    Command.execute(Arrays.copyOfRange(parts, 1, parts.length));
                    executed = true;
                }
            }
            if (!executed) {
                ChatUtil.sendMessage("&cCommand not found!");
            }
        }
    }

}
