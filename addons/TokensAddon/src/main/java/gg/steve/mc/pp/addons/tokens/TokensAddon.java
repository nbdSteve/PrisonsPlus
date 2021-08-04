package gg.steve.mc.pp.addons.tokens;

import gg.steve.mc.pp.addon.PrisonsPlusAddon;
import gg.steve.mc.pp.addons.tokens.cmd.TokensCommand;
import gg.steve.mc.pp.addons.tokens.core.TokenPlayerManager;
import gg.steve.mc.pp.addons.tokens.db.TokenDatabaseManager;
import gg.steve.mc.pp.addons.tokens.listener.PlayerJoinListener;
import gg.steve.mc.pp.addons.tokens.listener.TokenListener;

public class TokensAddon extends PrisonsPlusAddon {
    private final TokenDatabaseManager tokenDatabaseManager;
    private final TokenPlayerManager tokenPlayerManager;

    public TokensAddon() {
        super(Config.IDENTIFIER.getValue());
        this.tokenDatabaseManager = new TokenDatabaseManager();
        this.tokenPlayerManager = new TokenPlayerManager();
    }

    public enum Config {
        IDENTIFIER("TOKENS"),
        VERSION("1.0-SNAPSHOT"),
        AUTHOR("stevegoodhill"),
        NAME("Tokens");

        private final String value;

        Config(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Override
    public String getVersion() {
        return Config.VERSION.getValue();
    }

    @Override
    public String getAuthor() {
        return Config.AUTHOR.getValue();
    }

    @Override
    public String getAddonName() {
        return Config.NAME.getValue();
    }

    @Override
    public void registerCommands() {
        this.registerCommand(new TokensCommand());
    }

    @Override
    public void registerEvents() {
        this.registerListener(new PlayerJoinListener());
        this.registerListener(new TokenListener());
    }

    @Override
    public void registerFiles() {

    }

    @Override
    public void registerGuis() {

    }

    @Override
    public void registerClickActions() {

    }

    @Override
    public void onLoad() {
        this.registerCommands();
        this.registerEvents();
        this.tokenDatabaseManager.onLoad();
        this.tokenPlayerManager.onLoad();
    }

    @Override
    public void onShutdown() {
        this.tokenPlayerManager.onShutdown();
        this.tokenDatabaseManager.onShutdown();
    }
}
