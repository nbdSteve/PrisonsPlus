package gg.steve.mc.pp.file;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.file.types.*;
import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.manager.ManagerClass;
import lombok.Data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Data
@ManagerClass
public final class PluginFileTypeManager extends AbstractManager {
    private static PluginFileTypeManager instance;
    private Map<String, AbstractPluginFile> fileTypes;
    private SPlugin sPlugin;

    public PluginFileTypeManager(SPlugin sPlugin) {
        instance = this;
        this.fileTypes = new HashMap<>();
        this.sPlugin = sPlugin;
        AbstractManager.registerManager(instance);
    }

    @Override
    protected String getManagerName() {
        return "Plugin File Type";
    }

    @Override
    public void onLoad() {
        this.registerCoreFileTypes();
    }

    @Override
    public void onShutdown() {
        if (this.fileTypes != null && !this.fileTypes.isEmpty()) this.fileTypes.clear();
    }

    public static PluginFileTypeManager getInstance() {
        return instance;
    }

    public boolean registerPluginFileType(String key, AbstractPluginFile pluginFile) {
        if (this.fileTypes == null) this.fileTypes = new HashMap<>();
        if (this.fileTypes.containsKey(key)) return false;
        return this.fileTypes.put(key, pluginFile) != null;
    }

    public boolean unregisterPluginFileType(String key) {
        if (this.fileTypes == null || this.fileTypes.isEmpty() || !this.fileTypes.containsKey(key)) return false;
        return this.fileTypes.remove(key) != null;
    }

    private void registerCoreFileTypes() {
        this.registerPluginFileType("configuration", new ConfigurationPluginFile(this.sPlugin));
        this.registerPluginFileType("permission", new PermissionPluginFile(this.sPlugin));
        this.registerPluginFileType("data", new DataPluginFile(this.sPlugin));
        this.registerPluginFileType("gui", new GuiPluginFile(this.sPlugin));
        this.registerPluginFileType("message", new MessagePluginFile(this.sPlugin));
    }

    public boolean isRegisterFileTypeKey(String key) {
        if (this.fileTypes == null || this.fileTypes.isEmpty()) return false;
        return this.fileTypes.containsKey(key);
    }

    public AbstractPluginFile getNewPluginInstanceFileByKeyForFile(String key, String name, File file) {
        if (!this.isRegisterFileTypeKey(key)) return null;
        return this.fileTypes.get(key).createPluginNewFileInstance(name, file);
    }
}
