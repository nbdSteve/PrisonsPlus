package gg.steve.mc.pp.sapi.utils;

import org.bukkit.Bukkit;

@UtilityClass
public class ServerUtil {

    public static boolean isUsingPlugin(String plugin) {
        return Bukkit.getPluginManager().isPluginEnabled(plugin);
    }
}
