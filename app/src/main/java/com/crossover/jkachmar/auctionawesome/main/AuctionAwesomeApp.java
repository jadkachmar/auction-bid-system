package com.crossover.jkachmar.auctionawesome.main;

import android.app.Application;

import com.crossover.jkachmar.auctionawesome.db.Database;

public class AuctionAwesomeApp extends Application {

   public static Database mDb;

   @Override
   public void onCreate() {
       super.onCreate();
       mDb = new Database(this);
       mDb.open();
   }

   @Override
   public void onTerminate() {
       mDb.close();
       super.onTerminate();
   }

}