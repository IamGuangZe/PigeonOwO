package owo.pigeon.commands.impl;

import owo.pigeon.commands.Command;
import owo.pigeon.utils.ChatUtil;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class CopyCommand extends Command {
    public CopyCommand() {
        super("copy");
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 0) {
            ChatUtil.sendMessage("&cPlease type Text!");
            return;
        }
        String Text = String.join(" ", args);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(Text), null);
        ChatUtil.sendMessage("You copied the text : " + Text);
    }
}
