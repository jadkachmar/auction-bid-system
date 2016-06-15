package com.crossover.jkachmar.auctionawesome.common;

import java.text.DecimalFormat;
import java.util.Calendar;

public class AuctionUtils {

    private static final DecimalFormat decimalFormat = new DecimalFormat("00");

    public static String getFormattedDate(String timeInMillisStr) {
        long timeInMillis = Long.parseLong(timeInMillisStr);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
                + cal.get(Calendar.DAY_OF_MONTH) + " at " + decimalFormat.format(cal.get(Calendar.HOUR_OF_DAY)) + ":"
                + decimalFormat.format(cal.get(Calendar.MINUTE));
    }

    public static boolean hasExpired(String expiresIn){
        Calendar expiresCal = Calendar.getInstance();
        expiresCal.setTimeInMillis(Long.parseLong(expiresIn));
        Calendar currentCal = Calendar.getInstance();

        return currentCal.getTimeInMillis() - expiresCal.getTimeInMillis() > 0;
    }
}
