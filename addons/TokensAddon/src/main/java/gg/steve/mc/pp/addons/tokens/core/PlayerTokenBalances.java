package gg.steve.mc.pp.addons.tokens.core;

import gg.steve.mc.pp.addons.tokens.db.TokenDatabaseManager;
import gg.steve.mc.pp.utility.Log;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class PlayerTokenBalances {
    private TokenPlayer player;
    private Map<TokenType, Integer> balances;

    public PlayerTokenBalances(TokenPlayer player) {
        this.player = player;
        this.balances = new HashMap<>();
        if (TokenDatabaseManager.getInstance().hasExistingBalances(this.player.getPlayerId())) {
            this.deserializeBalances(TokenDatabaseManager.getInstance().getTokenBalancesForPlayer(this.player.getPlayerId()));
        } else {
            this.resetAll();
        }
    }

    public enum Query {
        SET,
        INCREMENT,
        DECREMENT,
        RESET,
        RESET_ALL,
    }

    public int update(TokenType type, Query query, int amount) {
        if (amount <= 0) amount = Math.abs(amount);
        switch (query) {
            case INCREMENT:
                return this.increment(type, amount);
            case DECREMENT:
                return this.decrement(type, amount);
            case SET:
                return this.set(type, amount);
            case RESET:
                return this.reset(type);
            case RESET_ALL:
                this.resetAll();
                break;
            default:
                return this.balances.get(type);
        }
        return -1;
    }

    public int get(TokenType type) {
        return this.balances.get(type);
    }

    private int increment(TokenType type, int increase) {
        int current = this.balances.get(type);
        this.balances.put(type, current + increase);
        return this.balances.get(type);
    }

    private int decrement(TokenType type, int decrease) {
        int current = this.balances.get(type);
        this.balances.put(type, current - decrease);
        return this.balances.get(type);
    }

    private Integer reset(TokenType type) {
        return this.balances.put(type, 0);
    }

    private void resetAll() {
        this.balances.clear();
        for (TokenType type : TokenType.values()) {
            this.balances.put(type, 0);
        }
    }

    private int set(TokenType type, int amount) {
        this.balances.put(type, amount);
        return this.balances.get(type);
    }

    private void deserializeBalances(String serializedBalances) {
        String[] parts = serializedBalances.split("-");
        for (String part : parts) {
            TokenType type = TokenType.valueOf(part.split(":")[0]);
            int balance = 0;
            if (type == null) {
                Log.severe("Unable tor ead player token balance, invalid token type detected.");
                continue;
            }
            try {
                balance = Integer.parseInt(part.split(":")[1]);
            } catch (NumberFormatException e) {
                Log.severe("Unable to read player " + type.name() + " token balance, invalid number format.");
                continue;
            }
            this.balances.put(type, balance);
        }
    }

    public String serializeBalances() {
        StringBuilder builder = new StringBuilder();
        for (TokenType type : TokenType.values()) {
            if (!this.balances.containsKey(type)) {
                builder.append(type);
                builder.append(":0-");
            } else {
                builder.append(type);
                builder.append(":");
                builder.append(this.balances.get(type));
                builder.append("-");
            }
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
