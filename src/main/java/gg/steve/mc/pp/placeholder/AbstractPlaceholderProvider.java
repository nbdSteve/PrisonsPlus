package gg.steve.mc.pp.placeholder;

import lombok.Data;

@Data
public abstract class AbstractPlaceholderProvider {
    private PlaceholderProviderType placeholderProviderType;

    public AbstractPlaceholderProvider(PlaceholderProviderType placeholderProviderType) {
        this.placeholderProviderType = placeholderProviderType;
    }

    public abstract boolean isUsingPlaceholderProvider();

    public abstract void registerPlaceholdersWithProvider();

    public abstract void unregisterPlaceholdersWithProvider();
}
