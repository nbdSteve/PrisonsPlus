package gg.steve.mc.pp.file.types;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.file.AbstractPluginFile;
import gg.steve.mc.pp.file.PluginFileClass;
import gg.steve.mc.pp.file.PluginFileType;
import gg.steve.mc.pp.permission.PermissionManager;

import java.io.File;

@PluginFileClass
public class PermissionPluginFile extends AbstractPluginFile {

    public PermissionPluginFile(String name, File file, SPlugin sPlugin) {
        super(name, file, PluginFileType.PERMISSION, sPlugin);
        // Register all of the permissions from the file with the plugin
        sPlugin.getPermissionManager().registerPermissionsFromFile(this);
    }
}
