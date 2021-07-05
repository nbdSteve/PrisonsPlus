package gg.steve.mc.pp.gui.exception;

import gg.steve.mc.pp.exception.AbstractException;
import gg.steve.mc.pp.exception.ExceptionClass;

@ExceptionClass
public class AbstractGuiNotFoundException extends AbstractException {
    private final String guiUniqueName;

    public AbstractGuiNotFoundException(String guiUniqueName) {
        this.guiUniqueName = guiUniqueName;
    }

    @Override
    public String getDebugMessage() {
        return "Unable to find the abstract gui with the unique name: " + this.guiUniqueName + ", double check your configuration.";
    }
}
