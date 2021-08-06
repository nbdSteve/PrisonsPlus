package gg.steve.mc.pp.addons.tokens.events;

public enum TokenBalanceUpdateMethod {
    COMMAND("Command"),
    ENCHANTMENT("Enchantment"),
    PAYMENT("Payment"),
    CUSTOM("Custom");

    private String niceName;

    TokenBalanceUpdateMethod(String niceName) {
        this.niceName = niceName;
    }

    public String getNiceName() {
        return niceName;
    }
}
