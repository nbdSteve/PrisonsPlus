package gg.steve.mc.pp.addons.mines.core.exception;

import gg.steve.mc.pp.exception.AbstractException;
import gg.steve.mc.pp.exception.ExceptionClass;

@ExceptionClass
public class MineNotFoundException extends AbstractException {
    private String name;

    public MineNotFoundException(String name) {
        this.name = name;
    }

    @Override
    public String getDebugMessage() {
        return "Unable to find the mine with the name, " + name + ".";
    }
}
