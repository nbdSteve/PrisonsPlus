package gg.steve.mc.pp.sapi.yml;

import gg.steve.mc.pp.sapi.yml.utils.FileManagerUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public enum Files {
    // generic
    CONFIG("prisons+.yml"),
    // omni
    PERMISSIONS("permissions.yml"),
    // lang
    DEBUG("lang" + File.separator + "debug.yml"),
    MESSAGES("lang" + File.separator + "messages.yml");;

    private final String path;

    Files(String path) {
        this.path = path;
    }

    public void load(FileManagerUtil fileManagerUtil) {
        fileManagerUtil.add(name(), this.path);
    }

    public YamlConfiguration get() {
        return FileManagerUtil.get(name());
    }

    public void save() {
        FileManagerUtil.save(name());
    }

    public static void reload() {
        FileManagerUtil.reload();
    }
}
