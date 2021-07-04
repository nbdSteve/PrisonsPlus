package gg.steve.mc.pp.gui;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public interface Clickable {

    void onClick(Player player);

    void onClick(AbstractGui gui, Player player, ConfigurationSection section, int slot);
}
