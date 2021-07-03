package gg.steve.mc.pp.placeholder;

import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.manager.ManagerClass;
import gg.steve.mc.pp.placeholder.providers.PAPIPlaceholderProvider;
import gg.steve.mc.pp.utility.ServerUtil;

import java.util.HashMap;
import java.util.Map;

@ManagerClass
public class PlaceholderManager extends AbstractManager {
    private static PlaceholderManager instance;
    private Map<String, AbstractPlaceholderProvider> providers;

    public PlaceholderManager() {
        instance = this;
        AbstractManager.registerManager(instance);
    }

    @Override
    public void onLoad() {
        for (PlaceholderProviderType provider : PlaceholderProviderType.values()) {
            if (!ServerUtil.isUsingPlugin(provider.getProviderPlugin())) continue;
            AbstractPlaceholderProvider providerInstance = null;
            switch (provider) {
                case PAPI:
                    providerInstance = new PAPIPlaceholderProvider();
                    break;
            }
            if (providerInstance == null) continue;
            providerInstance.registerPlaceholdersWithProvider();
            this.registerProvider(providerInstance);
        }
    }

    @Override
    public void onShutdown() {
        if (this.providers == null || this.providers.isEmpty()) return;
        this.providers.forEach((s, provider) -> provider.unregisterPlaceholdersWithProvider());
        this.providers.clear();
    }

    public boolean registerProvider(AbstractPlaceholderProvider provider) {
        if (this.providers == null) this.providers = new HashMap<>();
        if (this.providers.containsKey(provider.getPlaceholderProviderType().getProviderPlugin())) return false;
        return this.providers.put(provider.getPlaceholderProviderType().getProviderPlugin(), provider) != null;
    }

    public static PlaceholderManager getInstance() {
        return instance;
    }

    @Override
    protected String getManagerName() {
        return "Placeholder";
    }
}
