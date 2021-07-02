package gg.steve.mc.pp.file.types;

import gg.steve.mc.pp.file.AbstractPluginFile;
import gg.steve.mc.pp.file.PluginFileClass;
import gg.steve.mc.pp.file.PluginFileType;
import gg.steve.mc.pp.message.MessageManager;

@PluginFileClass
public class MessagePluginFile extends AbstractPluginFile {

    public MessagePluginFile() {
        super(PluginFileType.MESSAGE);
        // Register all messages from the file with the plugin
        MessageManager.getInstance().registerMessagesFromFile(this);
    }
}
