package gg.steve.mc.pp.file.types;

import gg.steve.mc.pp.SPlugin;
import gg.steve.mc.pp.file.AbstractPluginFile;
import gg.steve.mc.pp.file.PluginFileClass;
import gg.steve.mc.pp.message.MessageManager;

import java.io.File;

@PluginFileClass
public class MessagePluginFile extends AbstractPluginFile {

    public MessagePluginFile(SPlugin sPlugin) {
        super("message", sPlugin);
    }

    @Override
    public AbstractPluginFile createPluginNewFileInstance(String name, File file) {
        MessagePluginFile messagePluginFile = new MessagePluginFile(this.getSPlugin());
        messagePluginFile.loadFromFile(name, file);
        MessageManager.getInstance().registerMessagesFromFile(messagePluginFile);
        return messagePluginFile;
    }
}
