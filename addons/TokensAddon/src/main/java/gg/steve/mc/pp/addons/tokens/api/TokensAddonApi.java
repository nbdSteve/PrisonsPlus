package gg.steve.mc.pp.addons.tokens.api;

import gg.steve.mc.pp.addons.tokens.core.PlayerTokenBalances;
import gg.steve.mc.pp.addons.tokens.core.TokenPlayerManager;
import gg.steve.mc.pp.addons.tokens.core.TokenType;
import gg.steve.mc.pp.addons.tokens.events.PlayerTokenPayEvent;
import gg.steve.mc.pp.addons.tokens.events.TokenBalanceUpdateEvent;
import gg.steve.mc.pp.addons.tokens.events.TokenBalanceUpdateMethod;
import org.bukkit.Bukkit;

import java.util.UUID;

public class TokensAddonApi {

    public static int giveTokensToPlayer(UUID playerId, TokenType tokenType, int amount, TokenBalanceUpdateMethod updateMethod) {
        TokenBalanceUpdateEvent event = new TokenBalanceUpdateEvent(playerId, tokenType, amount, PlayerTokenBalances.Query.INCREMENT, updateMethod);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return -1;
        return event.getClosingBalance();
    }

    public static int removeTokensFromPlayer(UUID playerId, TokenType tokenType, int amount, TokenBalanceUpdateMethod updateMethod) {
        TokenBalanceUpdateEvent event = new TokenBalanceUpdateEvent(playerId, tokenType, amount, PlayerTokenBalances.Query.DECREMENT, updateMethod);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return -1;
        return event.getClosingBalance();
    }

    public static int setTokenBalanceForPlayer(UUID playerId, TokenType tokenType, int amount, TokenBalanceUpdateMethod updateMethod) {
        TokenBalanceUpdateEvent event = new TokenBalanceUpdateEvent(playerId, tokenType, amount, PlayerTokenBalances.Query.SET, updateMethod);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return -1;
        return event.getClosingBalance();
    }

    public static int resetTokenBalanceForPlayer(UUID playerId, TokenType tokenType, TokenBalanceUpdateMethod updateMethod) {
        TokenBalanceUpdateEvent event = new TokenBalanceUpdateEvent(playerId, tokenType, 0, PlayerTokenBalances.Query.RESET, updateMethod);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return -1;
        return event.getClosingBalance();
    }

    public static int resetAllTokenBalancesForPlayer(UUID playerId, TokenBalanceUpdateMethod updateMethod) {
        TokenBalanceUpdateEvent event = new TokenBalanceUpdateEvent(playerId, null, 0, PlayerTokenBalances.Query.RESET_ALL, updateMethod);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return -1;
        return event.getClosingBalance();
    }

    public static int getTokenBalanceForPlayer(UUID playerId, TokenType tokenType) {
        return TokenPlayerManager.getInstance().getTokenBalanceForPlayer(playerId, tokenType);
    }

    public static boolean payTokensFromPlayerToPlayer(UUID fromPlayerId, UUID toPlayerId, TokenType tokenType, int amount) {
        PlayerTokenPayEvent event = new PlayerTokenPayEvent(fromPlayerId, toPlayerId, tokenType, amount);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return false;
        return event.isSuccessfullTransfer();
    }
}
