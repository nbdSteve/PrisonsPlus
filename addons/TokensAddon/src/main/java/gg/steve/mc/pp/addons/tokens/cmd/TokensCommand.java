package gg.steve.mc.pp.addons.tokens.cmd;

import gg.steve.mc.pp.addons.tokens.cmd.subs.*;
import gg.steve.mc.pp.cmd.AbstractCommand;
import org.bukkit.command.CommandSender;

public class TokensCommand extends AbstractCommand {

    public TokensCommand() {
        super("tokens", "token");
    }

    @Override
    public void registerAllSubCommands() {
        this.registerSubCommand(new TokensHelpSubCommand(this));
        this.registerSubCommand(new TokensAdminSubCommand(this));
        this.registerSubCommand(new TokensPaySubCommand(this));
        this.registerSubCommand(new TokensBalanceSubCommand(this));
        this.registerSubCommand(new TokensShopGuiSubCommand(this));
    }

    @Override
    public void runOnNoArgsGiven(CommandSender executor) {
        if (this.getSubCommands() != null && this.getSubCommands().containsKey("help"))
            this.getSubCommands().get("help").execute(executor, new String[0]);
    }

    @Override
    public void registerAliases() {
        this.registerAlias("token");
    }
}
