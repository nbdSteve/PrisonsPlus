package gg.steve.mc.pp.file.types;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.file.AbstractPluginFile;
import gg.steve.mc.pp.file.PluginFileClass;
import gg.steve.mc.pp.gui.GuiManager;

import java.io.File;

@PluginFileClass
public class GuiPluginFile extends AbstractPluginFile {

    public GuiPluginFile(SPlugin sPlugin) {
        super("gui", sPlugin);
    }

    @Override
    public AbstractPluginFile createPluginNewFileInstance(String name, File file) {
        GuiPluginFile guiPluginFile = new GuiPluginFile(this.getSPlugin());
        guiPluginFile.loadFromFile(name, file);
        GuiManager.getInstance().registerGuiFromFile(guiPluginFile);
        return guiPluginFile;
    }
}
