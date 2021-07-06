package gg.steve.mc.pp.file.exception;

import gg.steve.mc.pp.exception.AbstractException;
import gg.steve.mc.pp.exception.ExceptionClass;

@ExceptionClass
public class ConfigurationFileNotFoundException extends AbstractException {
    private final String file;

    public ConfigurationFileNotFoundException(String file) {
        this.file = file;
    }

    @Override
    public String getDebugMessage() {
        return "Unable to find the desired configuration file: " + file;
    }
}
