package gg.steve.mc.pp.economy.providers;

import gg.steve.mc.pp.economy.AbstractEconomyProvider;
import gg.steve.mc.pp.economy.EconomyType;
import gg.steve.mc.pp.utility.LogUtil;
import gg.steve.mc.pp.utility.ServerUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;

@EconomyProviderClass
public class VaultEconomyProvider extends AbstractEconomyProvider {
    private static VaultEconomyProvider instance;
    private Economy economy;

    public VaultEconomyProvider() {
        super(EconomyType.VAULT);
        instance = this;
    }

    @Override
    public boolean isUsingEconomy() {
        return ServerUtil.isUsingPlugin(super.getEconomyType().getEconomyPlugin());
    }

    @Override
    public void hookIntoEconomyProvider() {
        try {
            this.economy = Bukkit.getServer().getServicesManager().getRegistration(Economy.class).getProvider();
        } catch (NullPointerException e) {
            LogUtil.warning("Tried to hook into Vault but failed, please install an economy plugin e.g. EssentialsX.");
        }
    }

    @Override
    public boolean isEconomyHooked() {
        return this.economy != null;
    }

    public static VaultEconomyProvider getInstance() {
        return instance;
    }

    public Economy getEconomy() {
        return economy;
    }
}
