package gg.steve.mc.pp.framework.utils;

import org.bukkit.ChatColor;

/**
 * Handles colouring messages and other strings
 */
public class ColorUtil {

    /**
     * Will apply the Bukkit color codes to the specified message
     *
     * @param message String, the message to colorize
     * @return String
     */
    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Strips all bukkit color codes from the message
     *
     * @param message String, the message to strip
     * @return String
     */
    public static String strip(String message) {
        return ChatColor.stripColor(message);
    }
}
