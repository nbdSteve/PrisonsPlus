package gg.steve.mc.pp.message;

import gg.steve.mc.pp.file.types.MessagePluginFile;
import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.manager.ManagerClass;
import gg.steve.mc.pp.message.exception.PluginMessageNotFoundException;
import gg.steve.mc.pp.utility.LogUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        List<String> placeholders = new ArrayList<>();
        if (this.messages.containsKey(name)) return false;
        PluginMessage message = new PluginMessage(file, key, name);
        for (String placeholder : placeholders) {
            message.registerPlaceholder(placeholder);
        }
        return this.messages.put(name, message) != null;
    }

    public void registerMessagesFromFile(MessagePluginFile file) {
        for (String key : file.get().getKeys(false)) {
            if (key.equalsIgnoreCase("file-type")) continue;
            this.registerMessage(file, key);
        }
    }

    public PluginMessage getMessageByKey(String key) throws PluginMessageNotFoundException {
        if (this.messages == null || this.messages.isEmpty() || !this.messages.containsKey(key))
            throw new PluginMessageNotFoundException(key);
        return this.messages.get(key);
    }

    public boolean sendMessage(String messageKey, Player player, String... replacements) {
        PluginMessage message;
        try {
            message = this.getMessageByKey(messageKey);
            LogUtil.warning("Getting here 1");
        } catch (PluginMessageNotFoundException e) {
            LogUtil.warning(e.getDebugMessage());
            e.printStackTrace();
            return false;
        }
        LogUtil.warning("Getting here 2");
        if (message == null) return false;
        LogUtil.warning("Getting here 3");
        LogUtil.warning("Message " + message.getText());
        message.send(player, replacements);
        return true;
    }

    public boolean sendMessage(String messageKey, CommandSender receiver, String... replacements) {
        PluginMessage message;
        try {
            message = this.getMessageByKey(messageKey);
        } catch (PluginMessageNotFoundException e) {
            LogUtil.warning(e.getDebugMessage());
            e.printStackTrace();
            return false;
        }
        if (message == null) return false;
        message.send(receiver, replacements);
        return true;
    }

    public static MessageManager getInstance() {
        return instance;
    }
}
