package gg.steve.mc.pp.file;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public abstract class AbstractPluginFile {

    public abstract AbstractPluginFile loadFromPath(String fileName, JavaPlugin instance);

    public abstract AbstractPluginFile loadFromFile(File file, JavaPlugin instance);

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
