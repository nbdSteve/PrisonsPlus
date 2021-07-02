package gg.steve.mc.pp.addon.loader;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.addon.PrisonsAddonManager;
import gg.steve.mc.pp.addon.PrisonsPlusAddon;
import gg.steve.mc.pp.utility.FileClassUtil;
import gg.steve.mc.pp.utility.LogUtil;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.List;

public class PrisonsAddonLoader {
    private final SPlugin sPlugin;

    public PrisonsAddonLoader(SPlugin sPlugin) {
        this.sPlugin = sPlugin;
        File addonsFolder = new File(this.sPlugin.getPlugin().getDataFolder(), "addons");
        if (!addonsFolder.exists()) {
            addonsFolder.mkdirs();
        }
    }

    public void registerAllAddons() {
        if (this.sPlugin.getPlugin() == null) return;
        List<Class<?>> classes = FileClassUtil.getClasses(this.sPlugin.getPlugin(), "addons", PrisonsPlusAddon.class);
        if (classes == null || classes.isEmpty()) return;
        classes.forEach(klass -> {
            PrisonsPlusAddon addon = this.createAddonInstance(klass);
            this.registerAddon(addon);
        });
    }

    public boolean registerAddon(String addonName) {
        String builder = addonName.toUpperCase().charAt(0) + addonName.substring(1).toLowerCase() + "Addon";
        List<Class<?>> subs = FileClassUtil.getClasses(this.sPlugin.getPlugin(), "addons", builder, PrisonsPlusAddon.class);
        if (subs == null || subs.isEmpty()) {
            return false;
        }
        PrisonsPlusAddon addon = createAddonInstance(subs.get(0));
        registerAddon(addon);
        return true;
    }

    public void registerAddon(PrisonsPlusAddon addon) {
        addon.register();
        PrisonsAddonManager.getInstance().registerAddon(addon);
    }

    private PrisonsPlusAddon createAddonInstance(Class<?> klass) {
        if (klass == null) return null;
        PrisonsPlusAddon addon = null;
        if (!PrisonsPlusAddon.class.isAssignableFrom(klass)) return null;
        try {
            Constructor<?>[] c = klass.getConstructors();
            if (c.length == 0) {
                addon = (PrisonsPlusAddon) klass.newInstance();
            } else {
                for (Constructor<?> con : c) {
                    if (con.getParameterTypes().length == 0) {
                        addon = (PrisonsPlusAddon) klass.newInstance();
                        break;
                    }
                }
            }
        } catch (Throwable t) {
            LogUtil.warning(t.getMessage());
            LogUtil.warning("Failed to init Prisons+ addon from class: " + klass.getName());
        }
        return addon;
    }
}
