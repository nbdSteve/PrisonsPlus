package gg.steve.mc.pp.addons.tokens.core;

public enum TokenType {
    MINE("Mine"),
    PVP("PVP"),
    MOB("Mob"),
    PRESTIGE("Prestige");

    private String niceName;

    TokenType(String niceName) {
        this.niceName = niceName;
    }

    public String getNiceName() {
        return niceName;
    }
}
