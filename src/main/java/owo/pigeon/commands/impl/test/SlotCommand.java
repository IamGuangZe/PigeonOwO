package owo.pigeon.commands.impl.test;

import owo.pigeon.commands.Command;
import owo.pigeon.utils.ChatUtil;
import owo.pigeon.utils.PlayerUtil;

public class SlotCommand extends Command {
    public SlotCommand() {
        super("s");
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 0) {
            ChatUtil.sendMessage("&cPlease type a Slot!");
            return;
        }
        int solt = Integer.parseInt(args[0]);
        if (solt < 0 || solt > 8) {
            ChatUtil.sendMessage("&cSlot must between 0 and 8!");
            return;
        }
        PlayerUtil.switchItemSlot(solt);
    }
}
