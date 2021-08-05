package gg.steve.mc.pp.addons.mines.cmd;

import gg.steve.mc.pp.addons.mines.cmd.subs.GiveToolSubCommand;
import gg.steve.mc.pp.addons.mines.cmd.subs.WarpSubCommand;
import gg.steve.mc.pp.cmd.AbstractCommand;
import gg.steve.mc.pp.cmd.CommandClass;
import org.bukkit.command.CommandSender;

@CommandClass
public class MinesCommand extends AbstractCommand {

    public MinesCommand() {
        super("mines", "mines");
    }

    @Override
    public void registerAllSubCommands() {
        this.registerSubCommand(new GiveToolSubCommand(this));
        this.registerSubCommand(new WarpSubCommand(this));
    }

    @Override
    public void runOnNoArgsGiven(CommandSender commandSender) {

    }

    @Override
    public void registerAliases() {
        this.registerAlias("mine");
    }
}
