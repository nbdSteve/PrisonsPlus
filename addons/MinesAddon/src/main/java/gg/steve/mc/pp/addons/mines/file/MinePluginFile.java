package gg.steve.mc.pp.addons.mines.file;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.file.AbstractPluginFile;
import gg.steve.mc.pp.file.PluginFileClass;

import java.io.File;

@PluginFileClass
public class MinePluginFile extends AbstractPluginFile {

    public MinePluginFile() {
        super("mine", SPlugin.getSPluginInstance());
    }

    @Override
    public AbstractPluginFile createPluginNewFileInstance(String name, File file) {
        MinePluginFile minePluginFile = new MinePluginFile();
        minePluginFile.loadFromFile(name, file);
        return minePluginFile;
    }
}
