package gg.steve.mc.pp.exception;

import gg.steve.mc.pp.utility.Log;
import lombok.Data;

@Data
public abstract class AbstractException extends Exception {

    protected AbstractException() {
        Log.warning(getDebugMessage());
    }

    public abstract String getDebugMessage();
}
