package gg.steve.mc.pp.message;

import gg.steve.mc.pp.file.types.MessagePluginFile;
import gg.steve.mc.pp.message.configurations.TitleMessageConfiguration;
import gg.steve.mc.pp.utility.ColorUtil;
import gg.steve.mc.pp.xseries.messages.ActionBar;
import lombok.Data;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Data
@MessageClass
public class PluginMessage {
    private MessagePluginFile configuration;
    private String path;
    private String name;
    private List<String> text;
    private boolean isActionBar;
    private TitleMessageConfiguration title;
    private List<String> placeholders;

    public PluginMessage(MessagePluginFile configuration, String path, String name) {
        this.configuration = configuration;
        this.path = path;
        this.name = name;
        this.text = new LinkedList<>(this.configuration.get().getStringList(this.path + ".text"));
        this.isActionBar = this.configuration.get().getBoolean(this.path + ".action-bar");
        try {
            this.title = new TitleMessageConfiguration(this, this.configuration.get().getConfigurationSection(this.path + ".title"));
        } catch (NullPointerException e) {
            this.title = null;
        }
    }

    public boolean registerPlaceholder(String placeholder) {
        if (this.placeholders == null) this.placeholders = new LinkedList<>();
        if (this.placeholders.contains(placeholder)) return false;
        return this.placeholders.add(placeholder);
    }

    public boolean unregisterPlaceholder(String placeholder) {
        if (this.placeholders == null || !this.placeholders.contains(placeholder)) return true;
        return this.placeholders.remove(placeholder);
    }

    public void send(Player receiver, String... replacements) {
        List<String> data = Arrays.asList(replacements);
        if (this.isActionBar) {
            for (String line : this.text) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                ActionBar.sendActionBar(receiver, ColorUtil.colorize(line));
            }
        } else if (this.title != null && this.title.isEnabled()) {
            this.title.send(receiver, data);
        } else {
            for (String line : this.text) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                receiver.sendMessage(ColorUtil.colorize(line));
            }
        }
    }

    public void send(CommandSender receiver, String... replacements) {
        List<String> data = Arrays.asList(replacements);
        if (this.isActionBar && receiver instanceof Player) {
            for (String line : this.text) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                ActionBar.sendActionBar((Player) receiver, ColorUtil.colorize(line));
            }
        } else if (this.title != null && this.title.isEnabled() && receiver instanceof Player) {
            this.title.send((Player) receiver, data);
        } else {
            for (String line : this.text) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                receiver.sendMessage(ColorUtil.colorize(line));
            }
        }
    }
}
