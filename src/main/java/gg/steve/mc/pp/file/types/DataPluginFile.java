package gg.steve.mc.pp.file.types;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.file.AbstractPluginFile;
import gg.steve.mc.pp.file.PluginFileClass;

import java.io.File;

@PluginFileClass
public class DataPluginFile extends AbstractPluginFile {

    public DataPluginFile(SPlugin sPlugin) {
        super("data", sPlugin);
    }

    @Override
    public AbstractPluginFile createPluginNewFileInstance(String name, File file) {
        DataPluginFile dataPluginFile = new DataPluginFile(this.getSPlugin());
        dataPluginFile.loadFromFile(name, file);
        return dataPluginFile;
    }
}
