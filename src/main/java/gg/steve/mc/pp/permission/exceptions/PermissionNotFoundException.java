package gg.steve.mc.pp.permission.exceptions;

import gg.steve.mc.pp.sapi.utils.LogUtil;

public class SPermissionNotFoundException extends Exception {

    public SPermissionNotFoundException(String permission) {
        LogUtil.warning("Unable to locate permission with key: " + permission);
    }
}
