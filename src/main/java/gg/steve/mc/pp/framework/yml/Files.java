package gg.steve.mc.pp.framework.yml;

import gg.steve.mc.pp.framework.yml.utils.FileManagerUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public enum Files {
    // tokens
    TOKEN_CONFIG("tokens" + File.separator + "config.yml"),
    TOKEN_MESSAGES("tokens" + File.separator + "lang" + File.separator + "messages.yml"),
    TOKEN_DEBUG("tokens" + File.separator + "lang" + File.separator + "debug.yml"),
    // generic
    CONFIG("prisons-plus.yml"),
    // permissions
    PERMISSIONS("permissions.yml"),
    // lang
    DEBUG("lang" + File.separator + "debug.yml"),
    MESSAGES("lang" + File.separator + "messages.yml");

    private final String path;

    Files(String path) {
        this.path = path;
    }

    public void load(FileManagerUtil fileManager) {
        fileManager.add(name(), this.path);
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
