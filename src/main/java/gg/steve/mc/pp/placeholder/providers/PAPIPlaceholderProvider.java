package gg.steve.mc.pp.placeholder.providers;

import gg.steve.mc.pp.placeholder.AbstractPlaceholderProvider;
import gg.steve.mc.pp.placeholder.PlaceholderProvider;
import gg.steve.mc.pp.sapi.utils.ServerUtil;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import java.util.ArrayList;
import java.util.List;

public class PAPIPlaceholderProvider extends AbstractPlaceholderProvider {
    private static PAPIPlaceholderProvider instance;
    private static List<PlaceholderExpansion> expansions;

    public PAPIPlaceholderProvider() {
        super(PlaceholderProvider.PAPI);
        instance = this;
        expansions = new ArrayList<>();
    }

    @Override
    public boolean isUsingPlaceholderProvider() {
        return ServerUtil.isUsingPlugin(super.getPlaceholderProvider().getProviderPlugin());
    }

    @Override
    public void registerPlaceholdersWithProvider() {
        if (expansions == null || expansions.isEmpty()) return;
        expansions.forEach(PlaceholderExpansion::register);
    }

    @Override
    public void unregisterPlaceholdersWithProvider() {

    }

    public static boolean registerExpansion(PlaceholderExpansion expansion) {
        if (expansions == null) expansions = new ArrayList<>();
        if (expansions.contains(expansion)) return false;
        return expansions.add(expansion);
    }

    public static PAPIPlaceholderProvider getInstance() {
        return instance;
    }
}
