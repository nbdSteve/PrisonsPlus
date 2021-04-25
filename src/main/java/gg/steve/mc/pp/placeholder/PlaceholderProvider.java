package gg.steve.mc.pp.placeholder;

public enum PlaceholderProvider {
    PAPI("PlaceholderAPI");

    private String pluginName;

    PlaceholderProvider(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getProviderPlugin() {
        return this.pluginName;
    }
}
