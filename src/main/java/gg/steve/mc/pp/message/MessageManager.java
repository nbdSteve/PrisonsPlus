package gg.steve.mc.pp.message;

import gg.steve.mc.pp.file.types.MessagePluginFile;
import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.manager.ManagerClass;

import java.util.HashMap;
import java.util.Map;

@ManagerClass
public class MessageManager extends AbstractManager {
    private static MessageManager instance;
    private Map<String, PluginMessage> messages;

    public MessageManager() {
        instance = this;
        AbstractManager.registerManager(instance);
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onShutdown() {
        if (this.messages != null && !this.messages.isEmpty()) this.messages.clear();
    }

    @Override
    protected String getManagerName() {
        return "Message";
    }

    public boolean registerMessage(MessagePluginFile file, String key) {
        if (this.messages == null) this.messages = new HashMap<>();
        String name = file.get().getString(key + ".unique-name");
        if (this.messages.containsKey(name)) return false;
        return this.messages.put(name, new PluginMessage(file, key, name)) != null;
    }

    public void registerMessagesFromFile(MessagePluginFile file) {
        for (String key : file.get().getKeys(false)) {
            if (key.equalsIgnoreCase("file-type")) continue;
            this.registerMessage(file, key);
        }
    }

    public static MessageManager getInstance() {
        return instance;
    }
}
