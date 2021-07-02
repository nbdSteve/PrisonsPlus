package gg.steve.mc.pp.utility;

import org.bukkit.Bukkit;

@UtilityClass
public class ServerUtil {

    public static boolean isUsingPlugin(String plugin) {
        return Bukkit.getPluginManager().isPluginEnabled(plugin);
    }
}
