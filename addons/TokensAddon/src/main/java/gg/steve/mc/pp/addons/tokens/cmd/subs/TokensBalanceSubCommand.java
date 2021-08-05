package gg.steve.mc.pp.addons.tokens.cmd.subs;

import gg.steve.mc.pp.addons.tokens.core.TokenPlayerManager;
import gg.steve.mc.pp.addons.tokens.core.TokenType;
import gg.steve.mc.pp.cmd.AbstractCommand;
import gg.steve.mc.pp.cmd.AbstractSubCommand;
import gg.steve.mc.pp.cmd.SubCommandClass;
import gg.steve.mc.pp.message.MessageManager;
import gg.steve.mc.pp.permission.Permission;
import gg.steve.mc.pp.permission.PermissionManager;
import gg.steve.mc.pp.permission.exceptions.PermissionNotFoundException;
import gg.steve.mc.pp.utility.Log;
import gg.steve.mc.pp.utility.NumberFormatUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

@SubCommandClass
public class TokensBalanceSubCommand extends AbstractSubCommand {

    public TokensBalanceSubCommand(AbstractCommand parent) {
        super(parent, "balance", "token-balance", 2, 3);
        this.registerAlias("b");
        this.registerAlias("bal");
    }

    @Override
    public void run(CommandSender executor, String[] arguments) {
        boolean all = arguments.length == 2 || (arguments.length == 3 && arguments[2].equalsIgnoreCase("all"));
        TokenType tokenType = null;
        if (!all) try {
            tokenType = TokenType.valueOf(arguments[2].toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            MessageManager.getInstance().sendMessage("invalid-token-type", executor);
            return;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(arguments[1]);
        int balance;
        if (executor instanceof Player && !offlinePlayer.getName().equalsIgnoreCase(executor.getName())) {
            Permission permission;
            try {
                permission = PermissionManager.getInstance().getPermissionByKey("token-balance-other");
            } catch (PermissionNotFoundException e) {
                Log.severe("Unable to execute balance other command because the permission is not found.");
                Log.warning(e.getDebugMessage());
                return;
            }
            if (!permission.hasPermission(executor)) {
                MessageManager.getInstance().sendMessage("no-permission", executor, permission.getPermission());
                return;
            }
        }
        if (all) {
            String mine = NumberFormatUtil.format(TokenPlayerManager.getInstance().getTokenBalanceForPlayer(offlinePlayer.getUniqueId(), TokenType.MINE));
            String pvp = NumberFormatUtil.format(TokenPlayerManager.getInstance().getTokenBalanceForPlayer(offlinePlayer.getUniqueId(), TokenType.PVP));
            String mob = NumberFormatUtil.format(TokenPlayerManager.getInstance().getTokenBalanceForPlayer(offlinePlayer.getUniqueId(), TokenType.MOB));
            String prestige = NumberFormatUtil.format(TokenPlayerManager.getInstance().getTokenBalanceForPlayer(offlinePlayer.getUniqueId(), TokenType.PRESTIGE));
            MessageManager.getInstance().sendMessage("get-player-token-all-balances", executor, offlinePlayer.getName(), mine, pvp, mob, prestige);
        } else {
            balance = TokenPlayerManager.getInstance().getTokenBalanceForPlayer(offlinePlayer.getUniqueId(), tokenType);
            MessageManager.getInstance().sendMessage("get-player-token-balance", executor, offlinePlayer.getName(), tokenType.getNiceName(), NumberFormatUtil.format(balance));
        }
    }
}
