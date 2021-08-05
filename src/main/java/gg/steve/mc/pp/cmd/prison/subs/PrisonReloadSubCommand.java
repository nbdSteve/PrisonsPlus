package gg.steve.mc.pp.cmd.prison.subs;

import gg.steve.mc.pp.PrisonsPlusPlugin;
import gg.steve.mc.pp.addon.PrisonAddonManager;
import gg.steve.mc.pp.cmd.AbstractCommand;
import gg.steve.mc.pp.cmd.AbstractSubCommand;
import gg.steve.mc.pp.cmd.SubCommandClass;
import gg.steve.mc.pp.message.MessageManager;
import gg.steve.mc.pp.utility.NumberFormatUtil;
import org.bukkit.command.CommandSender;

@SubCommandClass
public class PrisonReloadSubCommand extends AbstractSubCommand {

    public PrisonReloadSubCommand(AbstractCommand parent) {
        super(parent, "reload", "reload", 1, 1);
        this.registerAlias("r");
    }

    @Override
    public void run(CommandSender executor, String[] arguments) {
        PrisonsPlusPlugin.getInstance().onDisable();
        PrisonsPlusPlugin.getInstance().onLoad();
        PrisonsPlusPlugin.getInstance().onEnable();
        MessageManager.getInstance().sendMessage("reload", executor, "SUCCESS", NumberFormatUtil.format(PrisonAddonManager.getInstance().getRegisteredAddons().size()));
    }
}
