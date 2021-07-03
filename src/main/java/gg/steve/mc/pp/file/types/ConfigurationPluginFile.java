package gg.steve.mc.pp.file.types;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.file.AbstractPluginFile;
import gg.steve.mc.pp.file.PluginFileClass;
import gg.steve.mc.pp.file.PluginFileType;

import java.io.File;

@PluginFileClass
public class ConfigurationPluginFile extends AbstractPluginFile {

    public ConfigurationPluginFile(String name, File file, SPlugin sPlugin) {
        super(name, file, PluginFileType.CONFIGURATION, sPlugin);
    }
}
