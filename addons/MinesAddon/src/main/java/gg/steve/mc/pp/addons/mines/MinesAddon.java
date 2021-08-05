package gg.steve.mc.pp.addons.mines;

import gg.steve.mc.pp.addon.PrisonsPlusAddon;

public class MinesAddon extends PrisonsPlusAddon {

    public MinesAddon() {
        super(Config.IDENTIFIER.getValue());
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

    }

    @Override
    public void registerEvents() {

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

    }

    @Override
    public void onShutdown() {

    }
}
