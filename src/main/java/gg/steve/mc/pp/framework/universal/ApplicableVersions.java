package gg.steve.mc.pp.framework.universal;

import gg.steve.mc.pp.framework.nbt.utils.MinecraftVersion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApplicableVersions {
    private List<MinecraftVersion> versions;

    public ApplicableVersions(MinecraftVersion... versions) {
        this.versions = new ArrayList<>(Arrays.asList(versions));
    }

    public boolean isInServerVersion(MinecraftVersion version) {
        return versions.contains(version);
    }
}
