package gg.steve.mc.pp.addons.tokens.cmd.subs;

import com.mysql.jdbc.log.LogUtils;
import gg.steve.mc.pp.addons.tokens.api.TokensAddonApi;
import gg.steve.mc.pp.addons.tokens.core.TokenType;
import gg.steve.mc.pp.addons.tokens.events.PlayerTokenPayEvent;
import gg.steve.mc.pp.cmd.AbstractCommand;
import gg.steve.mc.pp.cmd.AbstractSubCommand;
import gg.steve.mc.pp.message.MessageManager;
import gg.steve.mc.pp.utility.Log;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

public class TokensPaySubCommand extends AbstractSubCommand {

    public TokensPaySubCommand(AbstractCommand parent) {
        super(parent, "pay", "token-pay", 4, 4);
        this.registerAlias("p");
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
        if (TokensAddonApi.payTokensFromPlayerToPlayer(from.getUniqueId(), to.getUniqueId(), tokenType, amount)){
            from.sendMessage("You have paid " + to.getName() + " " + amount + " " + tokenType.getNiceName() + " tokens.");
            if (to.isOnline()) ((Player) to).sendMessage("You have received a payment of " + amount + " " + tokenType.getNiceName() + " tokens from " + from.getName() + ".");
        } else {
            MessageManager.getInstance().sendMessage("insufficient", from);
        }
    }
}
