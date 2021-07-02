package gg.steve.mc.pp.addon;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.addon.exception.PrisonsPlusAddonNotFoundException;
import gg.steve.mc.pp.addon.loader.PrisonsAddonLoader;
import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.manager.ManagerClass;
import gg.steve.mc.pp.utility.LogUtil;
import org.apache.commons.lang.Validate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ManagerClass
public class PrisonsAddonManager extends AbstractManager {
    private static PrisonsAddonManager instance;
    private SPlugin sPlugin;
    private Map<String, PrisonsPlusAddon> addons;
    private PrisonsAddonLoader loader;


    public PrisonsAddonManager(SPlugin sPlugin) {
        instance = this;
        this.sPlugin = sPlugin;
        this.addons = new HashMap<>();
        this.loader = new PrisonsAddonLoader(this.sPlugin);
        AbstractManager.addManager(instance);
    }

    @Override
    public void onLoad() {
        this.loader.registerAllAddons();
        LogUtil.warning("Registered addons: " + this.addons.keySet().toString());
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

    public PrisonsPlusAddon getAddon(String identifier) throws PrisonsPlusAddonNotFoundException {
        if (!isRegistered(identifier)) throw new PrisonsPlusAddonNotFoundException(identifier);
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
            this.addons.forEach((s, prisonsPlusAddon) -> prisonsPlusAddon.unregister());
            this.addons.clear();
        }
    }

    public Collection<PrisonsPlusAddon> getRegisteredAddons() {
        return this.addons.values();
    }

    public static PrisonsAddonManager getInstance() {
        return instance;
    }

    @Override
    protected String getManagerName() {
        return "Addon";
    }
}
