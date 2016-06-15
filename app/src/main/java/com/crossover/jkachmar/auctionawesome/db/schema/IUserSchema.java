package com.crossover.jkachmar.auctionawesome.db.schema;

import com.crossover.jkachmar.auctionawesome.common.AuctionConstants;

public interface IUserSchema {
    String USER_TABLE = "users";

    String COLUMN_ID = "id";

    String COLUMN_USER_NAME = "username";

    String COLUMN_EMAIL = "email";

    String COLUMN_GENDER = "gender";

    String COLUMN_PASSWORD = "password";

    String USER_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + USER_TABLE
            + " ("
            + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + COLUMN_USER_NAME
            + " TEXT NOT NULL, "
            + COLUMN_EMAIL
            + " TEXT, "
            + COLUMN_GENDER
            + " TEXT, "
            + COLUMN_PASSWORD
            + " TEXT "
            + ")";

    String USER_TABLE_INSERT_USER_1 = "INSERT INTO " + USER_TABLE + "(" + COLUMN_USER_NAME + ", " + COLUMN_EMAIL + ", " + COLUMN_GENDER + ", " + COLUMN_PASSWORD
            + ") SELECT '" + AuctionConstants.AUCTION_USER1_USERNAME + "', '" + AuctionConstants.AUCTION_USER1_EMAIL + "', 'M', '" + AuctionConstants.AUCTION_USER1_PASSWORD + "' WHERE NOT EXISTS(SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_USER_NAME + " = '" + AuctionConstants.AUCTION_USER1_USERNAME + "')";

    String USER_TABLE_INSERT_USER_BOT = "INSERT INTO " + USER_TABLE + "(" + COLUMN_USER_NAME + ", " + COLUMN_EMAIL + ", " + COLUMN_GENDER + ", " + COLUMN_PASSWORD
            + ") SELECT '" + AuctionConstants.AUCTION_BOT_USERNAME + "', '" + AuctionConstants.AUCTION_BOT_EMAIL + "', 'M', '" + AuctionConstants.AUCTION_BOT_PASSWORD + "' WHERE NOT EXISTS(SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_USER_NAME + " = '" + AuctionConstants.AUCTION_BOT_USERNAME + "')";

    String[] USER_COLUMNS = new String[]{COLUMN_ID,
            COLUMN_USER_NAME, COLUMN_EMAIL, COLUMN_GENDER, COLUMN_PASSWORD};
}