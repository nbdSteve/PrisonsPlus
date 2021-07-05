package gg.steve.mc.pp.exception;

import gg.steve.mc.pp.utility.LogUtil;
import lombok.Data;

@Data
public abstract class AbstractException extends Exception {

    protected AbstractException() {
        LogUtil.warning(getDebugMessage());
    }

    public abstract String getDebugMessage();
}
