package gg.steve.mc.pp.framework.cmd.misc;

import gg.steve.mc.pp.framework.message.GeneralMessage;
import gg.steve.mc.pp.framework.permission.PermissionNode;
import gg.steve.mc.pp.framework.yml.Files;
import gg.steve.mc.pp.framework.cmd.SubCommand;
import org.bukkit.command.CommandSender;

public class ReloadSubCmd extends SubCommand {

    public ReloadSubCmd() {
        super("reload", 1, 1, false, PermissionNode.RELOAD);
        addAlias("r");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Files.reload();
//        Bukkit.getPluginManager().disablePlugin(ToolsPlus.get());
//        Bukkit.getPluginManager().enablePlugin(ToolsPlus.get());
        GeneralMessage.RELOAD.message(sender);
    }
}
