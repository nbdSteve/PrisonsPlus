package gg.steve.mc.pp.addons.tokens.cmd.subs;

import gg.steve.mc.pp.cmd.AbstractCommand;
import gg.steve.mc.pp.cmd.AbstractSubCommand;
import gg.steve.mc.pp.cmd.SubCommandClass;
import gg.steve.mc.pp.message.MessageManager;
import org.bukkit.command.CommandSender;

import java.util.List;

@SubCommandClass
public class TokensHelpSubCommand extends AbstractSubCommand {

    public TokensHelpSubCommand(AbstractCommand parent) {
        super(parent, "help", "token-help", 0, 1);
        this.registerAlias("h");
    }

    @Override
    public List<String> setTabCompletion(CommandSender commandSender, String[] strings) {
        return null;
    }

    @Override
    public void run(CommandSender executor, String[] strings) {
        MessageManager.getInstance().sendMessage("tokens-help", executor);
    }
}
