package gg.steve.mc.pp.file;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.file.exception.ConfigurationFileNotFoundException;
import gg.steve.mc.pp.file.types.*;
import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.manager.ManagerClass;
import gg.steve.mc.pp.utility.Log;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@ManagerClass
public final class FileManager extends AbstractManager {
    private static FileManager instance;
    private final SPlugin sPlugin;
    private Map<String, AbstractPluginFile> files;

    public FileManager(SPlugin sPlugin) {
        instance = this;
        this.sPlugin = sPlugin;
        this.files = new HashMap<>();
        AbstractManager.registerManager(instance);
    }

    /**
     * Enum for the core files for the plugin, the ones in this top level loader
     */
    public enum CoreFiles {
        // config
        CONFIG("prisons+.yml"),
        // permissions
        PERMISSIONS("permissions.yml"),
        // addon data
        ADDON_DATA("data" + File.separator + "addons.yml"),
        // lang
        MESSAGES("lang" + File.separator + "messages.yml"),
        DEBUG("lang" + File.separator + "debug.yml");

        private final String path;

        CoreFiles(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }

        public YamlConfiguration get() {
            return FileManager.getInstance().get(name());
        }

        public void save() {
            FileManager.getInstance().save(name());
        }

        public static void reload() {
            FileManager.getInstance().reload();
        }
    }

    @Override
    public void onLoad() {
        for (CoreFiles file : CoreFiles.values()) {
            this.registerFile(file.name(), file.getPath());
        }
    }

    @Override
    public void onShutdown() {
        if (this.files == null || this.files.isEmpty()) return;
        this.files.clear();
    }

    @Override
    protected String getManagerName() {
        return "File";
    }

    public static FileManager getInstance() {
        return instance;
    }

    public boolean registerFile(String name, String path) {
        if (this.files == null) this.files = new HashMap<>();
        if (this.files.containsKey(name)) return false;
        File file = new File(this.sPlugin.getPlugin().getDataFolder(), path);
        if (!file.exists()) {
            this.sPlugin.getPlugin().saveResource(path, false);
        }
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        String key = configuration.getString("file-type");
        return this.registerFile(key, name, file);
    }

    public boolean registerFile(String key, String name, File file) {
        if (this.files == null) this.files = new HashMap<>();
        this.files.remove(name);
        if (!PluginFileTypeManager.getInstance().isRegisterFileTypeKey(key)) {
            Log.severe("Attempted to create plugin file, but the type " + key + " is not a registered type.");
            return false;
        }
        return this.files.put(name, PluginFileTypeManager.getInstance().getNewPluginInstanceFileByKeyForFile(key, name, file)) != null;
    }

    /**
     * Gets the respective yaml configuration for the file
     *
     * @param name String, the name of the file
     * @return YamlConfiguration
     */
    public YamlConfiguration get(String name) {
        if (this.files == null) return null;
        try {
            if (!this.files.containsKey(name)) {
                throw new ConfigurationFileNotFoundException(name);
            }
        } catch (ConfigurationFileNotFoundException e) {
            Log.warning(e.getDebugMessage());
            e.printStackTrace();
        }
        return this.files.get(name).get();
    }

    /**
     * Saves the respective file
     *
     * @param name String, the name of the file
     */
    public void save(String name) {
        if (this.files == null) return;
        if (this.files.containsKey(name)) files.get(name).save();
    }

    /**
     * Reloads all of the plugins files
     */
    public void reload() {
        if (this.files == null || this.files.isEmpty()) return;
        for (AbstractPluginFile file : files.values()) file.reload();
    }
}
