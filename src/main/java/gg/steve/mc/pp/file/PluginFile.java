package gg.steve.mc.pp.file;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public enum PluginFile {
    // generic
    CONFIG("prisons+.yml"),
    // omni
    PERMISSIONS("permissions.yml"),
    // lang
    DEBUG("lang" + File.separator + "debug.yml"),
    MESSAGES("lang" + File.separator + "messages.yml");;

    private final String path;

    PluginFile(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public YamlConfiguration get() {
        return FileManager.getInstance().get(name());
    }

    public void save() {
        FileManager.getInstance().save(name());
    }

    public static void reload() {
        FileManager.getInstance().reload();
    }
}
