package gg.steve.mc.pp.addons.mines;

import gg.steve.mc.pp.addon.PrisonsPlusAddon;
import gg.steve.mc.pp.addons.mines.cmd.MinesCommand;
import gg.steve.mc.pp.addons.mines.core.MineManager;
import gg.steve.mc.pp.addons.mines.create.CreationStateManager;
import gg.steve.mc.pp.addons.mines.file.MinePluginFile;
import gg.steve.mc.pp.addons.mines.listener.CreationToolInteractionListener;
import gg.steve.mc.pp.addons.mines.tool.CreationTool;
import gg.steve.mc.pp.file.PluginFileTypeManager;

public final class MinesAddon extends PrisonsPlusAddon {
    private final CreationStateManager creationStateManager;
    private final MineManager mineManager;

    public MinesAddon() {
        super(Config.IDENTIFIER.getValue());
        this.creationStateManager = new CreationStateManager();
        this.mineManager = new MineManager();
        PluginFileTypeManager.getInstance().registerPluginFileType("mine", new MinePluginFile());
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
        this.mineManager.onLoad();
    }

    @Override
    public void onShutdown() {
        this.mineManager.onShutdown();
        this.creationStateManager.onShutdown();
    }
}
