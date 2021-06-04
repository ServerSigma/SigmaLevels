package com.serversigma.sigmalevels.utilitie;

import java.text.DecimalFormat;

public class NumberFormat {

    private static final String[] NUMBER_FORMAT = "k;M;B;T;Q;QQ;S;SS".split(";");
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###.#");

    private static String formatLarge(double n, int iteration) {
        double f = n / 1000.0D;
        return f < 1000 || iteration >= NUMBER_FORMAT.length - 1 ?
                DECIMAL_FORMAT.format(f) + NUMBER_FORMAT[iteration] : formatLarge(f, iteration + 1);
    }

    public static String format(double value) {
        return value < 1000 ?  DECIMAL_FORMAT.format(value) : formatLarge(value, 0);
    }

}
