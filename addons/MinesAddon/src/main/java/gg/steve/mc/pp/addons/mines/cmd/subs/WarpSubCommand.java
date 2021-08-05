package gg.steve.mc.pp.addons.mines.cmd.subs;

import gg.steve.mc.pp.cmd.AbstractCommand;
import gg.steve.mc.pp.cmd.AbstractSubCommand;
import gg.steve.mc.pp.cmd.SubCommandClass;
import org.bukkit.command.CommandSender;

@SubCommandClass
public class WarpSubCommand extends AbstractSubCommand {

    public WarpSubCommand(AbstractCommand parent) {
        super(parent, "warp", "mine-warp", 1, 2);
    }

    @Override
    public void run(CommandSender commandSender, String[] strings) {

    }
}
