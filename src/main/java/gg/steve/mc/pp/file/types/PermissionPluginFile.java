package gg.steve.mc.pp.file.types;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.file.AbstractPluginFile;
import gg.steve.mc.pp.file.PluginFileClass;
import gg.steve.mc.pp.permission.PermissionManager;

import java.io.File;

@PluginFileClass
public class PermissionPluginFile extends AbstractPluginFile {

    public PermissionPluginFile(SPlugin sPlugin) {
        super("permission", sPlugin);
    }

    @Override
    public AbstractPluginFile createPluginNewFileInstance(String name, File file) {
        PermissionPluginFile permissionPluginFile = new PermissionPluginFile(this.getSPlugin());
        permissionPluginFile.loadFromFile(name, file);
        PermissionManager.getInstance().registerPermissionsFromFile(permissionPluginFile);
        return permissionPluginFile;
    }
}
