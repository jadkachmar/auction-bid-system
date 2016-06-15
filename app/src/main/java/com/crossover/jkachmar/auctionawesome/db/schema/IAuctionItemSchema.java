package com.crossover.jkachmar.auctionawesome.db.schema;

public interface IAuctionItemSchema {
    String AUCTION_ITEM_TABLE = "auctions";

    String COLUMN_ID = "id";

    String COLUMN_ITEM_NAME = "name";

    String COLUMN_ITEM_DESCRIPTION = "description";

    String COLUMN_DATE_CREATED = "created";

    String COLUMN_CREATED_BY = "created_by";

    String COLUMN_EXPIRES_IN = "expires_in";

    String COLUMN_STARTING_BID = "starting_bid";

    String COLUMN_CURRENT_BID = "current_bid";

    String COLUMN_CURRENT_BIDDER = "current_bidder";

    String COLUMN_STATUS = "status";

    String AUCTION_ITEM_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + AUCTION_ITEM_TABLE
            + " ("

            + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "

            + COLUMN_ITEM_NAME
            + " TEXT NOT NULL, "

            + COLUMN_ITEM_DESCRIPTION
            + " TEXT, "

            + COLUMN_DATE_CREATED
            + " TEXT, "

            + COLUMN_CREATED_BY
            + " TEXT, "

            + COLUMN_EXPIRES_IN
            + " TEXT, "

            + COLUMN_STARTING_BID
            + " INTEGER, "

            + COLUMN_CURRENT_BID
            + " INTEGER, "

            + COLUMN_CURRENT_BIDDER
            + " TEXT, "

            + COLUMN_STATUS
            + " TEXT "
            + ")";

    String[] AUCTION_ITEM_COLUMNS = new String[]{COLUMN_ID,
            COLUMN_ID, COLUMN_ITEM_NAME, COLUMN_ITEM_DESCRIPTION, COLUMN_DATE_CREATED, COLUMN_CREATED_BY, COLUMN_EXPIRES_IN, COLUMN_STARTING_BID, COLUMN_CURRENT_BID, COLUMN_CURRENT_BIDDER, COLUMN_STATUS};
}