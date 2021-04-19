package gg.steve.mc.pp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.java.JavaPlugin;

@EqualsAndHashCode(callSuper = true)
@Data
public final class PrisonsPlusPlugin extends JavaPlugin {
    private static PrisonsPlusPlugin instance;
    private Economy economy;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static PrisonsPlusPlugin getInstance() {
        return instance;
    }
}
