package gg.steve.mc.pp.permission;

import java.util.Locale;

public enum PermissionType {
    DEFAULT(""),
    COMMAND("command");

    private String identifier;

    PermissionType(String identifier) {
        this.identifier = identifier;
    }

    public static PermissionType getPermissionTypeByIdentifier(String identifier) {
        for (PermissionType type : PermissionType.values()) {
            if (type.identifier.equalsIgnoreCase(identifier.toUpperCase(Locale.ROOT))) {
                return type;
            }
        }
        return DEFAULT;
    }
}