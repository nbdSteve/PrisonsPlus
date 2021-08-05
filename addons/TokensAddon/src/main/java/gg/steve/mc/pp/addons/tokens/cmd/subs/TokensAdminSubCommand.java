package gg.steve.mc.pp.addons.tokens.cmd.subs;

import gg.steve.mc.pp.addons.tokens.api.TokensAddonApi;
import gg.steve.mc.pp.addons.tokens.core.TokenType;
import gg.steve.mc.pp.addons.tokens.events.TokenBalanceUpdateMethod;
import gg.steve.mc.pp.cmd.AbstractCommand;
import gg.steve.mc.pp.cmd.AbstractSubCommand;
import gg.steve.mc.pp.cmd.SubCommandClass;
import gg.steve.mc.pp.message.MessageManager;
import gg.steve.mc.pp.utility.NumberFormatUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Locale;

@SubCommandClass
public class TokensAdminSubCommand extends AbstractSubCommand {

    public TokensAdminSubCommand(AbstractCommand parent) {
        super(parent, "admin", "token-admin", 3, 5);
        this.registerAlias("a");
    }

    protected enum Argument {
        GIVE(new String[]{"give", "add"}),
        REMOVE(new String[]{"remove", "take"}),
        SET(new String[]{"set"}),
        GET(new String[]{"get", "g", "balance", "bal"}),
        RESET(new String[]{"reset"}),
        RESET_ALL(new String[]{"reset-all"});

        private String[] aliases;

        Argument(String[] aliases) {
            this.aliases = aliases;
        }

        protected static Argument getArgumentFromString(String query) {
            for (Argument argument : Argument.values()) {
                if (Arrays.stream(argument.getAliases()).anyMatch(s -> s.equalsIgnoreCase(query))) return argument;
            }
            return null;
        }

        protected String[] getAliases() {
            return aliases;
        }
    }

    @Override
    public void run(CommandSender executor, String[] arguments) {
        // token admin arg player type amount
        Argument argument = Argument.getArgumentFromString(arguments[1]);
        if (argument == null) {
            this.getParent().doInvalidCommandMessage(executor);
            return;
        }
        TokenType type = null;
        if (arguments.length == 3 && argument != Argument.RESET_ALL) {
            this.getParent().doInvalidArgumentsMessage(executor);
            return;
        }
        if (arguments.length >= 4) {
            try {
                type = TokenType.valueOf(arguments[3].toUpperCase(Locale.ROOT));
            } catch (Exception e) {
                MessageManager.getInstance().sendMessage("invalid-token-type", executor);
                return;
            }
        }
        int amount = 0;
        if (arguments.length == 5) {
            try {
                amount = Integer.parseInt(arguments[4]);
                if (amount < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                MessageManager.getInstance().sendMessage("invalid-amount", executor);
                return;
            }
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(arguments[2]);
        int balance;
        switch (argument) {
            case GIVE:
                balance = TokensAddonApi.giveTokensToPlayer(offlinePlayer.getUniqueId(), type, amount, TokenBalanceUpdateMethod.COMMAND);
                break;
            case REMOVE:
                balance = TokensAddonApi.removeTokensFromPlayer(offlinePlayer.getUniqueId(), type, amount, TokenBalanceUpdateMethod.COMMAND);
                break;
            case SET:
                balance = TokensAddonApi.setTokenBalanceForPlayer(offlinePlayer.getUniqueId(), type, amount, TokenBalanceUpdateMethod.COMMAND);
                break;
            case GET:
                balance = TokensAddonApi.getTokenBalanceForPlayer(offlinePlayer.getUniqueId(), type);
                MessageManager.getInstance().sendMessage("get-player-token-balance", executor, offlinePlayer.getName(), type.getNiceName(), NumberFormatUtil.format(balance));
                return;
            case RESET:
                balance = TokensAddonApi.resetTokenBalanceForPlayer(offlinePlayer.getUniqueId(), type, TokenBalanceUpdateMethod.COMMAND);
                break;
            case RESET_ALL:
                TokensAddonApi.resetAllTokenBalancesForPlayer(offlinePlayer.getUniqueId(), TokenBalanceUpdateMethod.COMMAND);
                MessageManager.getInstance().sendMessage("reset-all-player-token-balances", executor, offlinePlayer.getName());
                return;
            default:
                this.getParent().doInvalidCommandMessage(executor);
                return;
        }
        MessageManager.getInstance().sendMessage("update-player-token-balance", executor, offlinePlayer.getName(), type.getNiceName(), NumberFormatUtil.format(balance));
    }
}
