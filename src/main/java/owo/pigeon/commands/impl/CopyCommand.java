package owo.pigeon.commands.impl;

import owo.pigeon.commands.Command;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.CommandUtil;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

import static owo.pigeon.commands.CommandManager.commandPrefix;

public class CopyCommand extends Command {
    public CopyCommand() {
        super("copy");
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 0) {
            CommandUtil.sendCommandError(CommandUtil.errorReason.UnknownOrIncompleteCommand,
                    this.getCommand(),
                    args,
                    args.length
            );
            return;
        }
        String Text = String.join(" ", args);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(Text), null);
        ChatUtil.sendMessage("You copied the text : " + Text);
    }

    @Override
    public String getUsage() {
        return commandPrefix + "copy <message>";
    }
}
