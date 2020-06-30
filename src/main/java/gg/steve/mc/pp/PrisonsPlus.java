package gg.steve.mc.pp;

import gg.steve.mc.pp.framework.SetupManager;
import gg.steve.mc.pp.framework.yml.utils.FileManagerUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;

public final class PrisonsPlus extends JavaPlugin {
    private static PrisonsPlus instance;
    private static DecimalFormat numberFormat = new DecimalFormat("#,###.##");

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        SetupManager.setupFiles(new FileManagerUtil(instance));
        SetupManager.registerCommands(instance);
        SetupManager.registerEvents(instance);
        SetupManager.loadPluginCache();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        SetupManager.shutdownPluginCache();
    }

    public static PrisonsPlus getInstance() {
        return instance;
    }

    public static String format(double number) {
        return numberFormat.format(number);
    }
}
