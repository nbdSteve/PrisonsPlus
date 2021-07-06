package gg.steve.mc.pp.addon;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.addon.exception.PrisonsPlusAddonNotFoundException;
import gg.steve.mc.pp.addon.loader.PrisonAddonLoader;
import gg.steve.mc.pp.file.FileManager;
import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.manager.ManagerClass;
import gg.steve.mc.pp.utility.LogUtil;
import lombok.Data;
import org.apache.commons.lang.Validate;

import java.util.*;

@Data
@ManagerClass
public class PrisonAddonManager extends AbstractManager {
    private static PrisonAddonManager instance;
    private SPlugin sPlugin;
    private Map<String, PrisonsPlusAddon> addons;
    private PrisonAddonLoader loader;
    private List<String> unregisteredAddonIdentifiers;

    public PrisonAddonManager(SPlugin sPlugin) {
        instance = this;
        this.sPlugin = sPlugin;
        this.loader = new PrisonAddonLoader(this.sPlugin);
        AbstractManager.registerManager(instance);
    }

    @Override
    public void onLoad() {
        this.loadUnregisteredAddonIdentifiersFromData();
        this.registerAllAddons();
    }

    @Override
    public void onShutdown() {
        this.unregisterAllAddons();
        if (this.unregisteredAddonIdentifiers != null && !this.unregisteredAddonIdentifiers.isEmpty()) this.unregisteredAddonIdentifiers.clear();
    }

    public boolean registerAddon(String identifier) {
        if (this.addons == null) this.addons = new HashMap<>();
        if (this.addons.containsKey(identifier.toUpperCase(Locale.ROOT))) return false;
        if (this.unregisteredAddonIdentifiers != null && this.unregisteredAddonIdentifiers.contains(identifier)) {
            this.unregisteredAddonIdentifiers.remove(identifier);
            FileManager.CoreFiles.ADDON_DATA.get().set("unregistered", this.unregisteredAddonIdentifiers);
            FileManager.CoreFiles.ADDON_DATA.save();
        }
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
        return this.unregisterAddon(addon.getIdentifier());
    }

    public boolean unregisterAddon(String identifier) {
        if (this.addons == null || this.addons.isEmpty()) return false;
        if (!this.addons.containsKey(identifier)) return false;
        this.addons.get(identifier).unregister();
        if (this.unregisteredAddonIdentifiers == null) this.unregisteredAddonIdentifiers = new ArrayList<>();
        if (!this.unregisteredAddonIdentifiers.contains(identifier)) {
            this.unregisteredAddonIdentifiers.add(identifier);
            FileManager.CoreFiles.ADDON_DATA.get().set("unregistered", this.unregisteredAddonIdentifiers);
            FileManager.CoreFiles.ADDON_DATA.save();
        }
        return this.addons.remove(identifier) != null;
    }

    public void unregisterAllAddons() {
        if (this.addons != null && !this.addons.isEmpty()) {
            this.addons.forEach((s, prisonsPlusAddon) -> prisonsPlusAddon.unregister());
            this.addons.clear();
        }
    }

    public void registerAllAddons() {
        this.loader.registerAllAddons();
    }

    public Collection<PrisonsPlusAddon> getRegisteredAddons() {
        if (this.addons == null) this.addons = new HashMap<>();
        return this.addons.values();
    }

    public static PrisonAddonManager getInstance() {
        return instance;
    }

    public void loadUnregisteredAddonIdentifiersFromData() {
        this.unregisteredAddonIdentifiers = FileManager.CoreFiles.ADDON_DATA.get().getStringList("unregistered");
    }

    public boolean isUnregistered(String identifier) {
        if (this.unregisteredAddonIdentifiers == null || this.unregisteredAddonIdentifiers.isEmpty()) return false;
        return this.unregisteredAddonIdentifiers.contains(identifier);
    }

    public boolean reloadAddon(String identifier) {
        if (this.isRegistered(identifier)) {
            if (this.addons == null || this.addons.isEmpty()) return false;
            if (!this.addons.containsKey(identifier)) return false;
            this.addons.get(identifier).unregister();
            if (this.addons.remove(identifier) == null) return false;
        }
        return this.registerAddon(identifier);
    }

    @Override
    protected String getManagerName() {
        return "Addon";
    }
}
