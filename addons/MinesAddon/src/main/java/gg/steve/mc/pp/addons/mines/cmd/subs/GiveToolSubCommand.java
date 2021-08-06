package gg.steve.mc.pp.addons.mines.cmd.subs;

import gg.steve.mc.pp.addons.mines.tool.CreationTool;
import gg.steve.mc.pp.cmd.AbstractCommand;
import gg.steve.mc.pp.cmd.AbstractSubCommand;
import gg.steve.mc.pp.cmd.SubCommandClass;
import gg.steve.mc.pp.utility.Log;
import gg.steve.mc.pp.xseries.XItemStack;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SubCommandClass
public class GiveToolSubCommand extends AbstractSubCommand {

    public GiveToolSubCommand(AbstractCommand parent) {
        super(parent, "give-tool", "mine-give-tool", 1, 1);
        this.registerAlias("gt");
        this.registerAlias("give");
        this.registerAlias("tool");
    }

    @Override
    public void run(CommandSender executor, String[] arguments) {
        if (!(executor instanceof Player)) {
            Log.info("Only players are able to make selections, please go in-game to use this feature.");
            return;
        }
        XItemStack.giveOrDrop((Player) executor, CreationTool.getTool());
    }
}
