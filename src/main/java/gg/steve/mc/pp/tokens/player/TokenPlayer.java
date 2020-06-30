package gg.steve.mc.pp.tokens.player;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class TokenPlayer {
    private UUID playerId;
    private OfflinePlayer player;
    private int tokens;
    private int prestige;

    public TokenPlayer(UUID playerId, int tokens, int prestige) {
        this.playerId = playerId;
        this.player = Bukkit.getOfflinePlayer(playerId);
        this.tokens = tokens;
        this.prestige = prestige;
    }

    public void setTokens(TokenType type, int amount) {
        switch (type) {
            case TOKEN:
                this.tokens = amount;
                break;
            case PRESTIGE:
                this.prestige = amount;
                break;
        }
    }

    public void resetTokens(TokenType type) {
        switch (type) {
            case TOKEN:
                this.tokens = 0;
                break;
            case PRESTIGE:
                this.prestige = 0;
                break;
        }
    }

    public void addTokens(TokenType type, int amount) {
        switch (type) {
            case TOKEN:
                this.tokens += amount;
                break;
            case PRESTIGE:
                this.prestige += amount;
                break;
        }
    }

    public void removeTokens(TokenType type, int amount) {
        switch (type) {
            case TOKEN:
                this.tokens -= amount;
                break;
            case PRESTIGE:
                this.prestige -= amount;
                break;
        }
    }

    public int getTokens(TokenType type) {
        switch (type) {
            case TOKEN:
                return this.tokens;
            case PRESTIGE:
                return this.prestige;
        }
        return 0;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }
}
