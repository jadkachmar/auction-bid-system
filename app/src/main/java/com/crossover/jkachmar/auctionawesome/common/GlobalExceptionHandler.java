package com.crossover.jkachmar.auctionawesome.common;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.crossover.jkachmar.auctionawesome.main.LoginActivity;

public class GlobalExceptionHandler implements java.lang.Thread.UncaughtExceptionHandler {

    private Context context;

    public GlobalExceptionHandler(Context context) {
        this.context = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        Intent startActivity = new Intent(context, LoginActivity.class);

        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId, startActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);

        System.exit(0);
    }
}