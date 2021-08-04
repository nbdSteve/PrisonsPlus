package gg.steve.mc.pp.utility;

import gg.steve.mc.pp.xseries.XSound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

@UtilityClass
public class SoundUtil {

    public static void playSound(ConfigurationSection section, String entry, Player player) {
        if (section.getBoolean(entry + ".sound.enabled")) {
            String sound = section.getString(entry + ".sound.type").toUpperCase();
            if (!XSound.matchXSound(sound).isPresent()) {
                Log.warning("Unable to find sound, " + sound + ", please check your configuration.");
                return;
            }
            XSound xSound = XSound.matchXSound(sound).get();
            xSound.play(player, section.getInt(entry + ".sound.volume"), section.getInt(entry + ".sound.pitch"));
        }
    }
}
