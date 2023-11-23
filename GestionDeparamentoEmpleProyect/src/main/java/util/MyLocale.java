package util;

import java.text.NumberFormat;
import java.util.Locale;

public class MyLocale {
    

    public static String toLocalMoney(Double money) {
        return NumberFormat.getCurrencyInstance(new Locale("es", "ES")).format(money);
    }

    public static String toLocalNumber(Double number) {
        return NumberFormat.getNumberInstance(new Locale("es", "ES")).format(number);
    }
}

