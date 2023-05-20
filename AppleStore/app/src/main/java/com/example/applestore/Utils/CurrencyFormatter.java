package com.example.applestore.Utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CurrencyFormatter {
    public static String formatCurrency(long amount){
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        return numberFormat.format(amount) + " VNĐ";
    }
}
