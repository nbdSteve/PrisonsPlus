package gg.steve.mc.pp.addons.tokens.core;

import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.manager.ManagerClass;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@ManagerClass
public class TokenPlayerManager extends AbstractManager {
    private static TokenPlayerManager instance;
    private Map<UUID, TokenPlayer> tokenPlayers;

    public TokenPlayerManager() {
        instance = this;
        AbstractManager.registerManager(this);
    }

    @Override
    public void onLoad() {
        this.registerOnlineTokenPlayers();
    }

    @Override
    public void onShutdown() {
        if (this.tokenPlayers == null || this.tokenPlayers.isEmpty()) return;
        this.unregisterOnlineTokenPlayers();
        this.tokenPlayers.clear();
    }

    @Override
    protected String getManagerName() {
        return "Token Manager";
    }

    public static TokenPlayerManager getInstance() {
        return instance;
    }

    public boolean registerTokenPlayer(UUID playerId) {
        if (this.tokenPlayers == null) this.tokenPlayers = new HashMap<>();
        if (this.tokenPlayers.containsKey(playerId)) return false;
        return this.tokenPlayers.put(playerId, new TokenPlayer(playerId)) != null;
    }

    public boolean unregisterTokenPlayer(UUID playerId) {
        if (this.tokenPlayers == null) return true;
        if (!this.tokenPlayers.containsKey(playerId)) return false;
        this.tokenPlayers.get(playerId).save();
        return this.tokenPlayers.remove(playerId) != null;
    }

    public void registerOnlineTokenPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.registerTokenPlayer(player.getUniqueId());
        }
    }

    public void unregisterOnlineTokenPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.unregisterTokenPlayer(player.getUniqueId());
        }
    }

    public TokenPlayer getTokenPlayer(UUID playerId) {
        if (this.tokenPlayers == null) this.tokenPlayers = new HashMap<>();
        if (!this.tokenPlayers.containsKey(playerId)) {
            this.tokenPlayers.put(playerId, new TokenPlayer(playerId));
        }
        return this.tokenPlayers.get(playerId);
    }

    public int getTokenBalanceForPlayer(UUID playerId, TokenType type) {
        return this.getTokenPlayer(playerId).getTokenBalances().get(type);
    }

    public void pay(UUID from, UUID to, TokenType type, int amount) {
        TokenPlayer payer = this.getTokenPlayer(from);
        TokenPlayer payee = this.getTokenPlayer(to);
        payer.getTokenBalances().update(type, PlayerTokenBalances.Query.DECREMENT, amount);
        payee.getTokenBalances().update(type, PlayerTokenBalances.Query.INCREMENT, amount);
    }

    public int give(UUID playerId, TokenType type, int amount) {
        return this.getTokenPlayer(playerId).getTokenBalances().update(type, PlayerTokenBalances.Query.INCREMENT, amount);
    }

    public int remove(UUID playerId, TokenType type, int amount) {
        return this.getTokenPlayer(playerId).getTokenBalances().update(type, PlayerTokenBalances.Query.DECREMENT, amount);
    }

    public int set(UUID playerId, TokenType type, int amount) {
        return this.getTokenPlayer(playerId).getTokenBalances().update(type, PlayerTokenBalances.Query.SET, amount);
    }

    public int reset(UUID playerId, TokenType type) {
        return this.getTokenPlayer(playerId).getTokenBalances().update(type, PlayerTokenBalances.Query.RESET, 0);
    }

    public void resetAll(UUID playerId) {
        this.getTokenPlayer(playerId).getTokenBalances().update(null, PlayerTokenBalances.Query.RESET_ALL, 0);
    }
}
