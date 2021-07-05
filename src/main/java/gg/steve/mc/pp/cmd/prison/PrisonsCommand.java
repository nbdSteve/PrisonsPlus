package gg.steve.mc.pp.cmd.prison;

import gg.steve.mc.pp.cmd.AbstractCommand;
import gg.steve.mc.pp.cmd.prison.subs.PrisonHelpSubCommand;
import gg.steve.mc.pp.utility.LogUtil;
import org.bukkit.command.CommandSender;

public class PrisonsCommand extends AbstractCommand {

    public PrisonsCommand() {
        super("prison", "prison");
    }

    @Override
    public void registerAllSubCommands() {
        this.registerSubCommand(new PrisonHelpSubCommand(this));
    }

    @Override
    public void runOnNoArgsGiven(CommandSender sender) {
        LogUtil.warning("getting here");
//        if (this.getSubCommands() != null && this.getSubCommands().containsKey("help")) this.getSubCommands().get("help").execute(sender, new String[]);
    }

    @Override
    public void registerAliases() {
        this.registerAlias("prisons");
        this.registerAlias("p");
        this.registerAlias("p+");
        this.registerAlias("prisons+");
        this.registerAlias("prison+");
        this.registerAlias("prisonsplus");
    }
}
