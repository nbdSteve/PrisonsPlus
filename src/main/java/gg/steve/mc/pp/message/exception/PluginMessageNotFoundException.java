package gg.steve.mc.pp.message.exception;

import gg.steve.mc.pp.exception.AbstractException;
import gg.steve.mc.pp.exception.ExceptionClass;

@ExceptionClass
public class PluginMessageNotFoundException extends AbstractException {
    private final String key;

    public PluginMessageNotFoundException(String key) {
        this.key = key;
    }

    @Override
    public String getDebugMessage() {
        return "Unable to find the plugin message with the key: " + this.key + ".";
    }
}
