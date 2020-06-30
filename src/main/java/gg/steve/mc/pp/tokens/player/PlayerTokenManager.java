package gg.steve.mc.pp.tokens.player;

import gg.steve.mc.pp.tokens.db.DatabaseUtil;
import gg.steve.mc.pp.tokens.event.AddMethodType;
import gg.steve.mc.pp.tokens.event.PreTokenAddEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerTokenManager implements Listener {
    private static Map<UUID, TokenPlayer> tokenPlayers;

    public static void initialise() {
        tokenPlayers = new HashMap<>();
    }

    public static void addTokenPlayer(UUID playerId, int tokens, int prestige) {
        if (tokenPlayers.containsKey(playerId)) return;
        tokenPlayers.put(playerId, new TokenPlayer(playerId, tokens, prestige));
    }

    public static void removeTokenPlayer(UUID playerId) {
        if (!tokenPlayers.containsKey(playerId)) return;
        tokenPlayers.remove(playerId);
    }

    public static TokenPlayer getTokenPlayer(UUID playerId) {
        return tokenPlayers.get(playerId);
    }

    public static int getTokens(UUID playerId, TokenType type) {
        return tokenPlayers.get(playerId).getTokens(type);
    }

    public static Map<UUID, TokenPlayer> getTokenPlayers() {
        return tokenPlayers;
    }

    public static boolean pay(TokenType type, UUID from, UUID to, int amount) {
        if (!tokenPlayers.containsKey(from)) return false;
        if (!tokenPlayers.containsKey(to)) {
            DatabaseUtil.loadPlayerTokenData(to);
            if (getTokenPlayer(to) == null) return false;
        }
        getTokenPlayer(from).removeTokens(type, amount);
        getTokenPlayer(to).addTokens(type, amount);
        return true;
    }

    public static void addTokens(TokenType type, UUID playerId, int amount, AddMethodType addMethod) {
        if (!tokenPlayers.containsKey(playerId)) DatabaseUtil.loadPlayerTokenData(playerId);
        Bukkit.getPluginManager().callEvent(new PreTokenAddEvent(tokenPlayers.get(playerId), type, amount, addMethod));
    }

    public static void removeTokens(TokenType type, UUID playerId, int amount) {
        if (!tokenPlayers.containsKey(playerId)) DatabaseUtil.loadPlayerTokenData(playerId);
        tokenPlayers.get(playerId).removeTokens(type, amount);
    }

    public static void setTokens(TokenType type, UUID playerId, int amount) {
        if (!tokenPlayers.containsKey(playerId)) DatabaseUtil.loadPlayerTokenData(playerId);
        tokenPlayers.get(playerId).setTokens(type, amount);
    }

    public static void resetTokens(TokenType type, UUID playerId) {
        if (!tokenPlayers.containsKey(playerId)) DatabaseUtil.loadPlayerTokenData(playerId);
        tokenPlayers.get(playerId).resetTokens(type);
    }

    public static void clearPlayerMap() {
        tokenPlayers.clear();
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        if (tokenPlayers.containsKey(event.getPlayer().getUniqueId())) return;
        DatabaseUtil.loadPlayerTokenData(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        if (!tokenPlayers.containsKey(event.getPlayer().getUniqueId())) return;
        DatabaseUtil.savePlayerTokenData(event.getPlayer().getUniqueId());
    }
}
