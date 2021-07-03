package gg.steve.mc.pp.file;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.file.exception.ConfigurationFileNotFoundException;
import gg.steve.mc.pp.file.types.*;
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

    public boolean registerFile(String name, String path) {
        if (this.files == null) this.files = new HashMap<>();
        if (this.files.containsKey(name)) return false;
//        InputStream input = this.getClass().getClassLoader().getResourceAsStream(path);
        File file = new File(this.sPlugin.getPlugin().getDataFolder(), path);
        if (!file.exists()) {
            this.sPlugin.getPlugin().saveResource(path, false);
        }
//        try {
//            if (!file.exists()) file.createNewFile();
//            FileOutputStream output = new FileOutputStream(file);
//            int length;
//            byte[] bytes = new byte[1024];
//            while ((length = input.read(bytes)) != -1) {
//                output.write(bytes, 0, length);
//            }
//        } catch (IOException e) {
//            LogUtil.warning("Unable to copy the file, " + name + ", to the plugins directory. Please contact the developer.");
//            e.printStackTrace();
//        }
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        PluginFileType type = PluginFileType.getFileTypeFromString(configuration.getString("file-type"));
        return this.registerFile(name, file, type);
    }

    public boolean registerFile(String name, File file, PluginFileType type) {
        if (this.files == null) this.files = new HashMap<>();
        if (this.files.containsKey(name)) return false;
        switch (type) {
            case GUI:
                return this.files.put(name, new GuiPluginFile(name, file, this.sPlugin)) != null;
            case DATA:
                return this.files.put(name, new DataPluginFile(name, file, this.sPlugin)) != null;
            case MESSAGE:
                return this.files.put(name, new MessagePluginFile(name, file, this.sPlugin)) != null;
            case PERMISSION:
                return this.files.put(name, new PermissionPluginFile(name, file, this.sPlugin)) != null;
            case CONFIGURATION:
                return this.files.put(name, new ConfigurationPluginFile(name, file, this.sPlugin)) != null;
            default:
                return false;
        }
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

    public static FileManager getInstance() {
        return instance;
    }

    @Override
    protected String getManagerName() {
        return "File";
    }
}
