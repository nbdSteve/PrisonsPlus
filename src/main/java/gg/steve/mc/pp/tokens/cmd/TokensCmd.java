package gg.steve.mc.pp.tokens.cmd;

import gg.steve.mc.pp.framework.cmd.MainCommand;
import gg.steve.mc.pp.tokens.cmd.token.BalanceSubCmd;
import gg.steve.mc.pp.tokens.cmd.token.HelpSubCmd;
import gg.steve.mc.pp.tokens.cmd.token.PaySubCmd;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class TokensCmd extends MainCommand {

    public TokensCmd() {
        addHelpCommand(new HelpSubCmd());
        addSubCommand(new BalanceSubCmd());
        addSubCommand(new PaySubCmd());
    }

    @Override
    public boolean noArgsCommand(CommandSender sender, String[] args) {
        if (!getHelp().isValidCommand(sender, args)) return true;
        getHelp().onCommand(sender, args);
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return onCommand(sender, args);
    }
}
