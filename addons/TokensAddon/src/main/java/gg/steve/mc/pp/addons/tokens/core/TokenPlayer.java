package gg.steve.mc.pp.addons.tokens.core;

import gg.steve.mc.pp.addons.tokens.db.TokenDatabaseManager;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

@Data
public class TokenPlayer {
    private UUID playerId;
    private OfflinePlayer offlinePlayer;
    private PlayerTokenBalances tokenBalances;

    public TokenPlayer(UUID playerId) {
        this.playerId = playerId;
        this.offlinePlayer = Bukkit.getOfflinePlayer(this.playerId);
        this.tokenBalances = new PlayerTokenBalances(this);
    }

    public void save() {
        TokenDatabaseManager.getInstance().setTokenBalancesForPlayer(this.playerId, this.tokenBalances.serializeBalances());
    }
}