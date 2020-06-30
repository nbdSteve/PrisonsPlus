package gg.steve.mc.pp.framework.universal;

import org.bukkit.enchantments.Enchantment;

public enum UniversalEnchantment {
    EFFICIENCY(Enchantment.DIG_SPEED, true, true, true, true, true, true, true, true, true, true, "Efficiency");

    private Enchantment bukkitEnchant;
    private boolean MC1_7, MC1_8, MC1_9, MC1_10, MC1_11, MC1_12, MC1_14, MC1_13, MC1_15, MC1_16;
    private String niceName;

    UniversalEnchantment(Enchantment bukkitEnchant, boolean MC1_7, boolean MC1_8, boolean MC1_9, boolean MC1_10, boolean MC1_11, boolean MC1_12, boolean MC1_14, boolean MC1_13, boolean MC1_15, boolean MC1_16, String niceName) {
        this.bukkitEnchant = bukkitEnchant;
        this.MC1_7 = MC1_7;
        this.MC1_8 = MC1_8;
        this.MC1_9 = MC1_9;
        this.MC1_10 = MC1_10;
        this.MC1_11 = MC1_11;
        this.MC1_12 = MC1_12;
        this.MC1_14 = MC1_14;
        this.MC1_13 = MC1_13;
        this.MC1_15 = MC1_15;
        this.MC1_16 = MC1_16;
        this.niceName = niceName;
    }

//    public boolean isInCurrentVersion(String version) {
//
//    }
}
