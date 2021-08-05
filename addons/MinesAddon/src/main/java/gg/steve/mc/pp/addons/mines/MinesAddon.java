package gg.steve.mc.pp.addons.mines;

import gg.steve.mc.pp.addon.PrisonsPlusAddon;
import gg.steve.mc.pp.addons.mines.cmd.MinesCommand;
import gg.steve.mc.pp.addons.mines.create.CreationStateManager;
import gg.steve.mc.pp.addons.mines.listener.CreationToolInteractionListener;
import gg.steve.mc.pp.addons.mines.tool.CreationTool;

public final class MinesAddon extends PrisonsPlusAddon {
    private final CreationStateManager creationStateManager;

    public MinesAddon() {
        super(Config.IDENTIFIER.getValue());
        this.creationStateManager = new CreationStateManager();
    }

    public enum Config {
        IDENTIFIER("MINES"),
        VERSION("1.0-SNAPSHOT"),
        AUTHOR("stevegoodhill"),
        NAME("Mines");

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
        this.registerCommand(new MinesCommand());
    }

    @Override
    public void registerEvents() {
        this.registerListener(new CreationToolInteractionListener());
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
        new CreationTool();
        this.creationStateManager.onLoad();
    }

    @Override
    public void onShutdown() {
        this.creationStateManager.onShutdown();
    }
}
