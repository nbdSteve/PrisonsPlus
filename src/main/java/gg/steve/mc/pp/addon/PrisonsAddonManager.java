package gg.steve.mc.pp.addon;

import gg.steve.mc.pp.addon.loader.PrisonsAddonLoader;
import gg.steve.mc.pp.manager.AbstractManager;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PrisonsAddonManager extends AbstractManager {
    private static PrisonsAddonManager instance;
    private JavaPlugin plugin;
    private Map<String, PrisonsPlusAddon> addons;
    private PrisonsAddonLoader loader;


    public PrisonsAddonManager(JavaPlugin plugin) {
        super();
        instance = this;
        this.plugin = plugin;
        this.addons = new HashMap<>();
        this.loader = new PrisonsAddonLoader(this.plugin);
    }

    @Override
    public void onLoad() {
        this.loader.registerAllAddons();
    }

    @Override
    public void onShutdown() {
        this.unregisterAllAddons();
    }

    public boolean registerAddon(String identifier) {
        if (this.addons == null) this.addons = new HashMap<>();
        if (this.addons.containsKey(identifier.toUpperCase(Locale.ROOT))) return false;
        return this.loader.registerAddon(identifier);
    }

    public boolean registerAddon(PrisonsPlusAddon addon) {
        Validate.notNull(addon, "Trying to install addon that is null");
        Validate.notNull(addon.getIdentifier(), "Addon must have a unique identifier");
        if (addons == null) addons = new HashMap<>();
        if (addons.containsKey(addon.getIdentifier())) return false;
        return addons.put(addon.getIdentifier(), addon) != null;
    }

    public boolean isRegistered(String identifier) {
        if (this.addons == null || this.addons.isEmpty()) return false;
        return this.addons.get(identifier) != null;
    }

    public PrisonsPlusAddon getAddon(String identifier) {
        if (!isRegistered(identifier)) return null;
        return this.addons.get(identifier);
    }

    public boolean unregisterAddon(PrisonsPlusAddon addon) {
        if (this.addons == null || this.addons.isEmpty()) return false;
        if (!this.addons.containsKey(addon.getIdentifier())) return false;
        this.addons.get(addon.getIdentifier()).unregister();
        return this.addons.remove(addon.getIdentifier()) != null;
    }

    public void unregisterAllAddons() {
        if (this.addons != null && !this.addons.isEmpty()) {
            this.addons.forEach((s, prisonsPlusAddon) -> {
                prisonsPlusAddon.unregister();
            });
            this.addons.clear();
        }
    }

    public static PrisonsAddonManager getInstance() {
        return instance;
    }
}
