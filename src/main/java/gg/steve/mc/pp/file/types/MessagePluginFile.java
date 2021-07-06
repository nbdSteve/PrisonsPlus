package gg.steve.mc.pp.file.types;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.file.AbstractPluginFile;
import gg.steve.mc.pp.file.PluginFileClass;
import gg.steve.mc.pp.file.PluginFileType;
import gg.steve.mc.pp.message.MessageManager;

import java.io.File;

@PluginFileClass
public class MessagePluginFile extends AbstractPluginFile {

    public MessagePluginFile(String name, File file, SPlugin sPlugin) {
        super(name, file, PluginFileType.MESSAGE, sPlugin);
        // Register all messages from the file with the plugin
        MessageManager.getInstance().registerMessagesFromFile(this);
    }
}
