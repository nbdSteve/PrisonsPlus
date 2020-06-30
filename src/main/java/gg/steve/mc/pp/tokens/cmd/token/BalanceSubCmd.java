package gg.steve.mc.pp.tokens.cmd.token;

import gg.steve.mc.pp.PrisonsPlus;
import gg.steve.mc.pp.framework.cmd.SubCommand;
import gg.steve.mc.pp.framework.permission.PermissionNode;
import gg.steve.mc.pp.tokens.TokenDebugMessage;
import gg.steve.mc.pp.tokens.TokenGeneralMessage;
import gg.steve.mc.pp.tokens.player.PlayerTokenManager;
import gg.steve.mc.pp.tokens.player.TokenType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class BalanceSubCmd extends SubCommand {

    public BalanceSubCmd() {
        super("balance", 1, 2, false, PermissionNode.TOKENS_BALANCE);
        addAlias("bal");
        addAlias("b");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            TokenGeneralMessage.BALANCE.message(sender, TokenType.TOKEN.name(), PrisonsPlus.format(PlayerTokenManager.getTokens(getPlayerId(sender), TokenType.TOKEN)));
        } else {
            UUID target = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
            if (getPlayerId(sender).equals(target)) {
                TokenDebugMessage.TARGET_CAN_NOT_BE_SELF.message(sender);
                return;
            }
            TokenGeneralMessage.BALANCE_QUERY.message(sender, Bukkit.getOfflinePlayer(args[1]).getName(), TokenType.TOKEN.name(), PrisonsPlus.format(PlayerTokenManager.getTokens(getPlayerId(sender), TokenType.TOKEN)));
        }
    }
}
