package gg.steve.mc.pp;

import gg.steve.mc.pp.sapi.utils.LogUtil;
import org.bukkit.plugin.java.JavaPlugin;

public final class PrisonsPlusPlugin extends JavaPlugin {
    private static PrisonsPlusPlugin instance;
    private SPlugin SPlugin;

    @Override
    public void onLoad() {
        instance = this;
        LogUtil.setPluginInstance(instance);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.SPlugin = new SPlugin(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (this.SPlugin != null) this.SPlugin.shutdown();
    }

    public static PrisonsPlusPlugin getInstance() {
        return instance;
    }
}
