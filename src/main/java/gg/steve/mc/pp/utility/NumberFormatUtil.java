package gg.steve.mc.pp.utility;

import java.text.DecimalFormat;

@UtilityClass
public class NumberFormatUtil {
    private static DecimalFormat numberFormat = new DecimalFormat("#,###.##");

    public static String format(double amount) {
        return numberFormat.format(amount);
    }
}
