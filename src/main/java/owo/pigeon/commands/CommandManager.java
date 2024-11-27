package owo.pigeon.commands;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import owo.pigeon.commands.impl.*;
import owo.pigeon.commands.impl.test.GetScoreboard;
import owo.pigeon.commands.impl.test.GetToolCommand;
import owo.pigeon.commands.impl.test.ItemCommand;
import owo.pigeon.commands.impl.test.SlotCommand;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.TimerExample;

import java.util.*;

public class CommandManager {

    private static final ArrayList<Command> commands = new ArrayList<>();
    private static char chatPrefix = '.';
    public static boolean isSay = false;

    public void init() {
        MinecraftForge.EVENT_BUS.register(this);

        commands.add(new CopyCommand());
        commands.add(new HelpCommand());
        commands.add(new PingCommand());
        commands.add(new SayCommand());
        commands.add(new ToggleCommand());

        commands.add(new GetScoreboard());
        commands.add(new GetToolCommand());
        commands.add(new ItemCommand());
        commands.add(new SlotCommand());
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

    // 为Runclient 无法 Mixin 的产物，仅在单人模式(作为服务端)可用
    /*        @SubscribeEvent
        public void onCommand(ServerChatEvent event) {
            if (event.message.startsWith(String.valueOf(chatPrefix))) {
                event.setCanceled(true);
                mc.ingameGUI.getChatGUI().addToSentMessages(event.message);
                String command = event.message.substring(1);
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
        }*/

    // 返回延迟
    @SubscribeEvent
    public void onChatReceivePing(ClientChatReceivedEvent event) {
        if ((ChatUtil.removeColor(event.message.getFormattedText()).contains("pigeongetping") ||
                ChatUtil.removeColor(event.message.getFormattedText()).contains("command")) &&
                TimerExample.isTimerRunning
        ) {
            TimerExample.stopTimer();
            event.setCanceled(true);

            String color;
            if (TimerExample.elapsedTime < 50) {
                color = "&a";
            } else if (TimerExample.elapsedTime < 100) {
                color = "&2";
            } else if (TimerExample.elapsedTime < 150) {
                color = "&e";
            } else if (TimerExample.elapsedTime < 250) {
                color = "&6";
            } else {
                color = "&c";
            }

            ChatUtil.sendMessage(color + TimerExample.elapsedTime + " &7ms");
        }
    }
}
