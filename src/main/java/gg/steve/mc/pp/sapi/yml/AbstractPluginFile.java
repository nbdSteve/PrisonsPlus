package gg.steve.mc.pp.sapi.yml;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractPluginFile {

    public abstract AbstractPluginFile load(String fileName, JavaPlugin instance);

    public abstract void save();

    /**
     * Reloads the file, updates the values
     */
    public abstract void reload();

    /**
     * Gets the file configuration for this file
     *
     * @return FileConfiguration
     */
    public abstract YamlConfiguration get();
}
