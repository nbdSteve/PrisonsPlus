package gg.steve.mc.pp.tokens.cmd.token;

import gg.steve.mc.pp.framework.cmd.SubCommand;
import gg.steve.mc.pp.framework.permission.PermissionNode;
import gg.steve.mc.pp.tokens.TokenGeneralMessage;
import org.bukkit.command.CommandSender;

public class HelpSubCmd extends SubCommand {

    public HelpSubCmd() {
        super("help", 0, 1, false, PermissionNode.TOKENS_HELP);
        addAlias("h");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        TokenGeneralMessage.HELP.message(sender);
    }
}
