package gg.steve.mc.pp.tokens.cmd.token;

import gg.steve.mc.pp.framework.cmd.SubCommand;
import gg.steve.mc.pp.framework.permission.PermissionNode;
import gg.steve.mc.pp.tokens.TokenDebugMessage;
import gg.steve.mc.pp.tokens.TokenGeneralMessage;
import gg.steve.mc.pp.tokens.player.PlayerTokenManager;
import gg.steve.mc.pp.tokens.player.TokenType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class PaySubCmd extends SubCommand {

    public PaySubCmd() {
        super("pay", 3, 3, true, PermissionNode.TOKENS_PAY);
        addAlias("p");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        //tokens pay player 10
        UUID target = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
        if (getPlayerId(sender).equals(target)) {
            TokenDebugMessage.TARGET_CAN_NOT_BE_SELF.message(sender);
            return;
        }
        int amount;
        try {
            amount = Integer.parseInt(args[2]);
        } catch (Exception e) {
            TokenDebugMessage.INVALID_AMOUNT.message(sender);
            return;
        }
        if (amount < 1) {
            TokenDebugMessage.INVALID_AMOUNT.message(sender);
            return;
        }
        if (PlayerTokenManager.getTokens(getPlayerId(sender), TokenType.TOKEN) < amount) {
            TokenGeneralMessage.INSUFFICIENT_TOKENS.message(sender, TokenType.TOKEN.name());
            return;
        }
        if (PlayerTokenManager.pay(TokenType.TOKEN, getPlayerId(sender), target, amount)) {
            TokenGeneralMessage.PAY_PAYER.message(sender, Bukkit.getOfflinePlayer(target).getName(), args[2], TokenType.TOKEN.name());
            if (Bukkit.getPlayer(target) != null) {
                TokenGeneralMessage.PAY_RECEIVER.message(Bukkit.getPlayer(target), sender.getName(), args[2], TokenType.TOKEN.name());
            }
        } else {
            // player not loaded
        }
    }
}
