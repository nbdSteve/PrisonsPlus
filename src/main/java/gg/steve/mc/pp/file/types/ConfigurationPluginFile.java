package gg.steve.mc.pp.file.types;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.file.AbstractPluginFile;
import gg.steve.mc.pp.file.PluginFileClass;

import java.io.File;

@PluginFileClass
public class ConfigurationPluginFile extends AbstractPluginFile {

    public ConfigurationPluginFile(SPlugin sPlugin) {
        super("configuration", sPlugin);
    }

    @Override
    public AbstractPluginFile createPluginNewFileInstance(String name, File file) {
        ConfigurationPluginFile configurationPluginFile = new ConfigurationPluginFile(this.getSPlugin());
        configurationPluginFile.loadFromFile(name, file);
        return configurationPluginFile;
    }
}
