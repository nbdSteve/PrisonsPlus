package gg.steve.mc.pp.addons.mines.cmd.subs;

import gg.steve.mc.pp.addons.mines.core.Mine;
import gg.steve.mc.pp.addons.mines.core.MineManager;
import gg.steve.mc.pp.addons.mines.core.exception.MineNotFoundException;
import gg.steve.mc.pp.cmd.AbstractCommand;
import gg.steve.mc.pp.cmd.AbstractSubCommand;
import gg.steve.mc.pp.cmd.SubCommandClass;
import gg.steve.mc.pp.message.MessageManager;
import gg.steve.mc.pp.permission.Permission;
import gg.steve.mc.pp.permission.PermissionManager;
import gg.steve.mc.pp.permission.exceptions.PermissionNotFoundException;
import gg.steve.mc.pp.utility.Log;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@SubCommandClass
public class WarpSubCommand extends AbstractSubCommand {

    public WarpSubCommand(AbstractCommand parent) {
        super(parent, "warp", "mine-warp", 1, 3);
    }

    @Override
    public List<String> setTabCompletion(CommandSender commandSender, String[] strings) {
        return null;
    }

    @Override
    public void run(CommandSender executor, String[] arguments) {
        if (!(executor instanceof Player) && arguments.length < 3) {
            Log.info("Please specify the player that you wish to warp, you can not warp the command line.");
            return;
        }
        Mine mine;
        try {
            mine = MineManager.getInstance().getRegisteredMineByName(arguments[1]);
        } catch (MineNotFoundException e) {
            executor.sendMessage(ChatColor.RED + e.getDebugMessage());
            return;
        }
        if (arguments.length == 3) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(arguments[2]);
            if (executor instanceof Player && !offlinePlayer.getName().equalsIgnoreCase(executor.getName())) {
                Permission permission;
                try {
                    permission = PermissionManager.getInstance().getPermissionByKey("mine-warp-other");
                } catch (PermissionNotFoundException e) {
                    Log.severe("Unable to execute warp other command because the permission is not found.");
                    Log.warning(e.getDebugMessage());
                    return;
                }
                if (!permission.hasPermission(executor)) {
                    MessageManager.getInstance().sendMessage("no-permission", executor, permission.getPermission());
                    return;
                }
            }
        }
        mine.getSpawnLocation().teleportPlayerToLocation((Player) executor);
    }
}
