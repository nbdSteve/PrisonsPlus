package gg.steve.mc.pp.addons.tokens.cmd.subs;

import gg.steve.mc.pp.addons.tokens.api.TokensAddonApi;
import gg.steve.mc.pp.addons.tokens.core.TokenPlayerManager;
import gg.steve.mc.pp.addons.tokens.core.TokenType;
import gg.steve.mc.pp.cmd.AbstractCommand;
import gg.steve.mc.pp.cmd.AbstractSubCommand;
import gg.steve.mc.pp.cmd.SubCommandClass;
import gg.steve.mc.pp.message.MessageManager;
import gg.steve.mc.pp.utility.Log;
import gg.steve.mc.pp.utility.NumberFormatUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SubCommandClass
public class TokensPaySubCommand extends AbstractSubCommand {

    public TokensPaySubCommand(AbstractCommand parent) {
        super(parent, "pay", "token-pay", 4, 4);
        this.registerAlias("p");
    }

    @Override
    public List<String> setTabCompletion(CommandSender executor, String[] arguments) {
        List<String> completions = new ArrayList<>();
        if (arguments.length == 2) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.getName().equalsIgnoreCase(executor.getName())) completions.add(player.getName());
            }
        } else if (arguments.length == 3) {
            for (TokenType tokenType : TokenType.values()) {
                completions.add(tokenType.name().toLowerCase(Locale.ROOT));
            }
        } else if (arguments.length == 4) {
            completions.add(String.valueOf(1));
        }
        return completions;
    }

    @Override
    public void run(CommandSender executor, String[] arguments) {
        // /tokens pay nbdsteve mine 10
        if (!(executor instanceof Player)) {
            Log.info("You cannot pay players from the console, please use the '/token admin give' command.");
            return;
        }
        Player from = (Player) executor;
        TokenType tokenType;
        try {
            tokenType = TokenType.valueOf(arguments[2].toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            MessageManager.getInstance().sendMessage("invalid-token-type", executor);
            return;
        }
        int amount;
        try {
            amount = Integer.parseInt(arguments[3]);
            if (amount < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            MessageManager.getInstance().sendMessage("invalid-amount", executor);
            return;
        }
        OfflinePlayer to = Bukkit.getOfflinePlayer(arguments[1]);
        if (from.getUniqueId().equals(to.getUniqueId())) {
            MessageManager.getInstance().sendMessage("unable-to-pay-self", from);
            return;
        }
        if (TokensAddonApi.payTokensFromPlayerToPlayer(from.getUniqueId(), to.getUniqueId(), tokenType, amount, false)) {
            MessageManager.getInstance().sendMessage("pay-token-payer", from, to.getName(), NumberFormatUtil.format(amount), tokenType.getNiceName());
            if (to.isOnline())
                MessageManager.getInstance().sendMessage("pay-token-receiver", to.getPlayer(), from.getName(), NumberFormatUtil.format(amount), tokenType.getNiceName());
        } else {
            MessageManager.getInstance().sendMessage("insufficient", from);
        }
    }
}
