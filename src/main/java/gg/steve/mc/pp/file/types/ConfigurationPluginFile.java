package gg.steve.mc.pp.file.types;

import gg.steve.mc.pp.file.AbstractPluginFile;
import gg.steve.mc.pp.file.PluginFileClass;
import gg.steve.mc.pp.file.PluginFileType;

@PluginFileClass
public class ConfigurationPluginFile extends AbstractPluginFile {

    public ConfigurationPluginFile() {
        super(PluginFileType.CONFIGURATION);
    }
}
