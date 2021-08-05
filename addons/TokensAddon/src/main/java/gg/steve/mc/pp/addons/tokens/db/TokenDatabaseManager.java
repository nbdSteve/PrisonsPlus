package gg.steve.mc.pp.addons.tokens.db;

import gg.steve.mc.pp.db.SQLDatabaseHandler;
import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.manager.ManagerClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@ManagerClass
public final class TokenDatabaseManager extends AbstractManager {
    private static TokenDatabaseManager instance;

    public TokenDatabaseManager() {
        instance = this;
        AbstractManager.registerManager(instance);
    }

    @Override
    public void onLoad() {
        this.generateTables();
    }

    @Override
    public void onShutdown() {

    }

    @Override
    protected String getManagerName() {
        return "TokensDatabase";
    }

    public static TokenDatabaseManager getInstance() {
        return instance;
    }

    private void generateTables() {
        SQLDatabaseHandler.getInstance().execute("CREATE TABLE IF NOT EXISTS tokens(player_id VARCHAR(36) NOT NULL, token_balances VARCHAR(255) NOT NULL, PRIMARY KEY (player_id))");
    }

    public String getTokenBalancesForPlayer(UUID playerId) {
        try {
            return SQLDatabaseHandler.getInstance().query("SELECT * FROM tokens WHERE player_id='" + String.valueOf(playerId) + "'", "token_balances");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void setTokenBalancesForPlayer(UUID playerId, String balances) {
        if (!this.getTokenBalancesForPlayer(playerId).equalsIgnoreCase("")) {
            SQLDatabaseHandler.getInstance().synchronousUpdate("UPDATE tokens SET token_balances='" + balances + "' WHERE player_id='" + String.valueOf(playerId) + "'");
        } else {
            SQLDatabaseHandler.getInstance().synchronousInsert("INSERT INTO tokens (player_id, token_balances) VALUES (?, ?);", String.valueOf(playerId), balances);
        }
    }

    public boolean hasExistingBalances(UUID playerId) {
        return !this.getTokenBalancesForPlayer(playerId).equalsIgnoreCase("");
    }
}
