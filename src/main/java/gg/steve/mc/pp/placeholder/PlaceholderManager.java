package gg.steve.mc.pp.placeholder;

import gg.steve.mc.pp.manager.AbstractManager;
import gg.steve.mc.pp.placeholder.providers.PAPIPlaceholderProvider;
import gg.steve.mc.pp.sapi.utils.ServerUtil;

import java.util.HashMap;
import java.util.Map;

public class PlaceholderManager extends AbstractManager {
    private static PlaceholderManager instance;
    private Map<String, AbstractPlaceholderProvider> providers;

    public PlaceholderManager() {
        instance = this;
        this.providers = new HashMap<>();
    }

    @Override
    public void onLoad() {
        for (PlaceholderProvider provider : PlaceholderProvider.values()) {
            if (!ServerUtil.isUsingPlugin(provider.getProviderPlugin())) continue;
            AbstractPlaceholderProvider providerInstance = null;
            switch (provider) {
                case PAPI:
                    providerInstance = new PAPIPlaceholderProvider();
                    break;
            }
            if (providerInstance == null) continue;
            providerInstance.registerPlaceholdersWithProvider();
            this.addProvider(providerInstance);
        }
    }

    @Override
    public void onShutdown() {
        if (this.providers == null || this.providers.isEmpty()) return;
        this.providers.forEach((s, provider) -> {
            provider.unregisterPlaceholdersWithProvider();
        });
        this.providers.clear();
    }

    public boolean addProvider(AbstractPlaceholderProvider provider) {
        if (this.providers == null) this.providers = new HashMap<>();
        if (this.providers.containsKey(provider.getPlaceholderProvider().getProviderPlugin())) return false;
        return this.providers.put(provider.getPlaceholderProvider().getProviderPlugin(), provider) != null;
    }

    public static PlaceholderManager getInstance() {
        return instance;
    }
}
