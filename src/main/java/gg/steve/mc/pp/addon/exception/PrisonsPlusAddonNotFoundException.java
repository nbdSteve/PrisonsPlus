package gg.steve.mc.pp.addon.exception;

import gg.steve.mc.pp.exception.AbstractException;
import gg.steve.mc.pp.exception.ExceptionClass;

@ExceptionClass
public class PrisonsPlusAddonNotFoundException extends AbstractException {
    private final String addon;

    public PrisonsPlusAddonNotFoundException(String addon) {
        this.addon = addon;
    }

    @Override
    public String getDebugMessage() {
        return "Unable to find the addon with name: " + addon;
    }
}
