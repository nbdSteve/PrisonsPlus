package gg.steve.mc.pp.file;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.manager.ManagerClass;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@ManagerClass
public class FileManager extends AbstractManager {
    private static FileManager instance;
    private final SPlugin sPlugin;
    private Map<String, AbstractPluginFile> files;

    public FileManager(SPlugin sPlugin) {
        instance = this;
        this.sPlugin = sPlugin;
        this.files = new HashMap<>();
        AbstractManager.addManager(instance);
    }

    @Override
    public void onLoad() {
//        for (PluginFile file : PluginFile.values()) {
//            this.add(file.name(), file.getPath());
//        }
    }

    @Override
    public void onShutdown() {
        if (this.files == null || this.files.isEmpty()) return;
        this.files.clear();
    }

    public boolean registerFile(String name, String path) {

    }

    public boolean registerFile(String name, File file) {

    }

    /**
     * Adds a plugin file to the map of loaded files
     *
     * @param name String, the name of the file (i.e. config)
     * @param path String, the actual path for the file (i.e. config.yml)
     */
    public void add(String name, String path) {
        if (this.files == null) this.files = new HashMap<>();
        this.files.put(name, new ConfigurationFile().loadFromPath(path, this.sPlugin.getPlugin()));
    }

    public void add(String name, File file) {
        if (this.files == null) this.files = new HashMap<>();
        this.files.put(name, new ConfigurationFile().loadFromFile(file, this.sPlugin.getPlugin()));
    }

    /**
     * Gets the respective yaml configuration for the file
     *
     * @param name String, the name of the file
     * @return YamlConfiguration
     */
    public YamlConfiguration get(String name) {
        if (this.files == null) return null;
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

    public static FileManager getInstance() {
        return instance;
    }

    @Override
    protected String getManagerName() {
        return "File";
    }
}
