package owo.pigeon.commands.impl;

import net.minecraft.client.multiplayer.ServerData;
import owo.pigeon.commands.Command;
import owo.pigeon.utils.ChatUtil;

import static owo.pigeon.features.modules.Module.mc;

public class ServerCommand extends Command {
    public ServerCommand() {
        super("server");
    }

    @Override
    public void execute(String[] args) {
        if (mc.isSingleplayer()) {
            sendCommandError("You must be on a server to use this command!");
            return;
        }

        ServerData serverData = mc.getCurrentServerData();

        ChatUtil.sendMessage("Name : " + serverData.serverName);
        ChatUtil.sendMessage("IP : " + serverData.serverIP);
        ChatUtil.sendMessage("Version : " + serverData.gameVersion);
        ChatUtil.sendMessage("Players : " + serverData.populationInfo);

        String[] motdLines = serverData.serverMOTD.split("\n");
        for (String line : motdLines) {
            ChatUtil.sendMessage("MOTD : " + line);
        }
    }

    @Override
    public String getUsage() {
        return super.getUsage();
    }
}
