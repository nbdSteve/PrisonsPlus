package gg.steve.mc.pp.file;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.utility.LogUtil;
import lombok.Data;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Data
public abstract class AbstractPluginFile {
    private String fileName;
    private File file;
    private SPlugin sPlugin;
    private PluginFileType fileType;
    private YamlConfiguration configuration;

    public AbstractPluginFile(PluginFileType fileType) {
        this.fileType = fileType;
    }

    /**
     * Creates the provided yml file, the filename must be that of a file in the resources folder
     *
     * @param fileName String, the name of the file to load
     */
    public boolean loadFromPath(String fileName, SPlugin sPlugin) {
        this.fileName = fileName;
        this.sPlugin = sPlugin;
        this.file = new File(this.sPlugin.getPlugin().getDataFolder(), fileName);
        if (!file.exists()) {
            this.sPlugin.getPlugin().saveResource(fileName, false);
        }
        this.configuration = new YamlConfiguration();
        try {
            this.configuration.load(file);
        } catch (InvalidConfigurationException e) {
            LogUtil.warning("The supplied file " + fileName + " is not in the correct format, please check your YAML syntax.");
            return false;
        } catch (FileNotFoundException e) {
            LogUtil.warning("The supplied file " + fileName + " was not found, please contact the developer. Disabling the plugin.");
            SPlugin.disable();
            return false;
        } catch (IOException e) {
            LogUtil.warning("The supplied file " + fileName + " could not be loaded, please contact the developer. Disabling the plugin.");
            SPlugin.disable();
            return false;
        }
        return true;
    }

    public boolean loadFromFile(File file, SPlugin sPlugin) {
        this.file = file;
        this.fileName = this.file.getName();
        this.sPlugin = sPlugin;
        this.configuration = YamlConfiguration.loadConfiguration(file);
        return this.configuration != null;
    }

    /**
     * Saves the file, this is used for setting variables in a method
     */
    public boolean save() {
        try {
            this.configuration.save(file);
        } catch (IOException e) {
            LogUtil.warning("Error saving the supplied file: " + this.fileName + ", please contact the developer.");
            return false;
        }
        return true;
    }

    /**
     * Reloads the file, updates the values
     */
    public boolean reload() {
        try {
            this.configuration.load(file);
        } catch (InvalidConfigurationException e) {
            LogUtil.warning("The supplied file " + this.fileName + " is not in the correct format, please check your YAML syntax.");
            return false;
        } catch (FileNotFoundException e) {
            LogUtil.warning("The supplied file " + this.fileName + " was not found, please contact the developer. Disabling the plugin.");
            SPlugin.disable();
            return false;
        } catch (IOException e) {
            LogUtil.warning("The supplied file " + this.fileName + " could not be loaded, please contact the developer. Disabling the plugin.");
            SPlugin.disable();
            return false;
        }
        return true;
    }

    public boolean delete() {
        return file.delete();
    }

    /**
     * Gets the file configuration for this file
     *
     * @return FileConfiguration
     */
    public YamlConfiguration get() {
        return this.configuration;
    }
}
