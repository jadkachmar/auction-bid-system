package com.crossover.jkachmar.auctionawesome.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.crossover.jkachmar.auctionawesome.db.dao.AuctionItemDAO;
import com.crossover.jkachmar.auctionawesome.db.dao.UserDAO;
import com.crossover.jkachmar.auctionawesome.db.schema.IAuctionItemSchema;
import com.crossover.jkachmar.auctionawesome.db.schema.IUserSchema;

public class Database {
    private static final String DATABASE_NAME = "awesome_auction.db";
    private static final int DATABASE_VERSION = 1;

    private DatabaseHelper mDbHelper;

    public static UserDAO userDAO;
    public static AuctionItemDAO auctionDAO;

    private final Context context;

    public Database open() throws SQLException {
        mDbHelper = new DatabaseHelper(context);
        SQLiteDatabase mDb = mDbHelper.getWritableDatabase();

        userDAO = new UserDAO(mDb);
        auctionDAO = new AuctionItemDAO(mDb);

        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public Database(Context context) {
        this.context = context;
    }


    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(IUserSchema.USER_TABLE_CREATE);
            db.execSQL(IAuctionItemSchema.AUCTION_ITEM_TABLE_CREATE);
            db.execSQL(IUserSchema.USER_TABLE_INSERT_USER_BOT);
            db.execSQL(IUserSchema.USER_TABLE_INSERT_USER_1);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + IUserSchema.USER_TABLE);
            onCreate(db);
        }
    }

}