package gg.steve.mc.pp.message.configurations;

import gg.steve.mc.pp.message.PluginMessage;
import gg.steve.mc.pp.utility.ColorUtil;
import gg.steve.mc.pp.xseries.messages.Titles;
import lombok.Data;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;

@Data
@MessageConfigurationClass
public class TitleMessageConfiguration {
    private PluginMessage parent;
    private ConfigurationSection section;
    private boolean isEnabled;

    public TitleMessageConfiguration(PluginMessage parent, ConfigurationSection section) {
        this.parent = parent;
        this.section = section;
        this.isEnabled = section.getBoolean("enabled");
    }

    public void send(Player player) {
        if (this.isEnabled) Titles.sendTitle(player, this.section);
    }

    public void send(Player player, List<String> replacements) {
        if (!this.isEnabled) return;
        String title = this.section.getString("text");
        String subtitle = this.section.getString("subtitle");

        int fadeIn = section.getInt("fade-in");
        int stay = section.getInt("stay");
        int fadeOut = section.getInt("fade-out");

        if (fadeIn < 1) fadeIn = 10;
        if (stay < 1) stay = 20;
        if (fadeOut < 1) fadeOut = 10;

        if (this.parent.getPlaceholders() != null) for (int i = 0; i < this.parent.getPlaceholders().size(); i++) {
            title = title.replace(this.parent.getPlaceholders().get(i), replacements.get(i));
            subtitle = subtitle.replace(this.parent.getPlaceholders().get(i), replacements.get(i));
        }

        Titles.sendTitle(player, fadeIn, stay, fadeOut, ColorUtil.colorize(title), ColorUtil.colorize(subtitle));
    }
}
