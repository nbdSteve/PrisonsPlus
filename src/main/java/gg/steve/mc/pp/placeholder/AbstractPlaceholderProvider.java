package gg.steve.mc.pp.placeholder;

import lombok.Data;

@Data
public abstract class AbstractPlaceholderProvider {
    private PlaceholderProvider placeholderProvider;

    public AbstractPlaceholderProvider(PlaceholderProvider placeholderProvider) {
        this.placeholderProvider = placeholderProvider;
    }

    public abstract boolean isUsingPlaceholderProvider();

    public abstract void registerPlaceholdersWithProvider();

    public abstract void unregisterPlaceholdersWithProvider();
}
