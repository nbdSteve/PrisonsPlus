package gg.steve.mc.pp.gui.exception;

import gg.steve.mc.pp.exception.AbstractException;
import gg.steve.mc.pp.exception.ExceptionClass;
import gg.steve.mc.pp.file.AbstractPluginFile;

@ExceptionClass
public class InvalidConfigurationFileTypeException extends AbstractException {
    private final AbstractPluginFile configuration;

    public InvalidConfigurationFileTypeException(AbstractPluginFile configuration) {
        this.configuration = configuration;
    }

    @Override
    public String getDebugMessage() {
        return "Unable to use the supplied configuration file, " + configuration.getName() + ", because it is not of file type 'gui'.";
    }
}
