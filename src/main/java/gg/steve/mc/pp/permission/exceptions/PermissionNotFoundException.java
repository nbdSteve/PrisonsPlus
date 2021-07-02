package gg.steve.mc.pp.permission.exceptions;

import gg.steve.mc.pp.exception.AbstractException;
import gg.steve.mc.pp.exception.ExceptionClass;

@ExceptionClass
public class PermissionNotFoundException extends AbstractException {
    private final String permission;

    public PermissionNotFoundException(String permission) {
        this.permission = permission;
    }

    @Override
    public String getDebugMessage() {
        return "Unable to locate permission with key: " + permission;
    }
}
