package com.crossover.jkachmar.auctionawesome.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.crossover.jkachmar.auctionawesome.db.dao.base.IAuctionItemDAO;
import com.crossover.jkachmar.auctionawesome.db.provider.DbContentProvider;
import com.crossover.jkachmar.auctionawesome.db.schema.IAuctionItemSchema;
import com.crossover.jkachmar.auctionawesome.models.AuctionItem;

import java.util.ArrayList;
import java.util.List;

public class AuctionItemDAO extends DbContentProvider implements IAuctionItemSchema, IAuctionItemDAO {

    private Cursor cursor;
    private ContentValues initialValues;

    public AuctionItemDAO(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public List<AuctionItem> fetchAuctionItemsByUsername(String username) {
        List<AuctionItem> auctionList = new ArrayList<AuctionItem>();
        final String selectionArgs[] = {String.valueOf(username)};
        final String selection = COLUMN_CREATED_BY + " = ?";
        cursor = super.query(AUCTION_ITEM_TABLE, AUCTION_ITEM_COLUMNS, selection,
                selectionArgs, COLUMN_ID, null, COLUMN_EXPIRES_IN + " DESC", null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                AuctionItem item = cursorToEntity(cursor);
                auctionList.add(item);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return auctionList;
    }

    @Override
    public List<AuctionItem> fetchAvailableAuctionItems(String username) {
        List<AuctionItem> auctionList = new ArrayList<AuctionItem>();
        final String selectionArgs[] = {String.valueOf(username)};
        final String selection = COLUMN_CREATED_BY + " != ? AND " + COLUMN_EXPIRES_IN + " > " + System.currentTimeMillis();
        cursor = super.query(AUCTION_ITEM_TABLE, AUCTION_ITEM_COLUMNS, selection, selectionArgs, COLUMN_ID, null, COLUMN_EXPIRES_IN + " DESC", null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                AuctionItem item = cursorToEntity(cursor);
                auctionList.add(item);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return auctionList;
    }

    @Override
    public List<AuctionItem> fetchWonAuctionItems(String username) {
        List<AuctionItem> auctionList = new ArrayList<AuctionItem>();
        final String selectionArgs[] = {String.valueOf(username)};
        final String selection = COLUMN_CURRENT_BIDDER + " = ? AND " + COLUMN_EXPIRES_IN + " < " + System.currentTimeMillis();
        cursor = super.query(AUCTION_ITEM_TABLE, AUCTION_ITEM_COLUMNS, selection, selectionArgs, COLUMN_ID, null, COLUMN_EXPIRES_IN + " DESC", null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                AuctionItem item = cursorToEntity(cursor);
                auctionList.add(item);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return auctionList;
    }

    @Override
    public List<AuctionItem> fetchAllAuctionItems() {
        List<AuctionItem> auctionList = new ArrayList<AuctionItem>();
        cursor = super.query(AUCTION_ITEM_TABLE, AUCTION_ITEM_COLUMNS, null,
                null, COLUMN_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                AuctionItem item = cursorToEntity(cursor);
                auctionList.add(item);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return auctionList;
    }


    public boolean addAuctionItem(AuctionItem newItem) {
        // set values
        setContentValue(newItem);
        try {
            return super.insert(AUCTION_ITEM_TABLE, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex) {
            Log.w("Database", ex.getMessage());
            return false;
        }
    }


    public boolean updateAuctionItem(AuctionItem newItem){
        setContentValue(newItem);
        try {
            return super.update(AUCTION_ITEM_TABLE, getContentValue(), COLUMN_ID + "=" + newItem.getId(), null) > 0;
        } catch (SQLiteConstraintException ex) {
            Log.w("Database", ex.getMessage());
            return false;
        }
    }
    protected AuctionItem cursorToEntity(Cursor cursor) {

        AuctionItem auctionItem = new AuctionItem();

        int idIndex;
        int nameIndex;
        int descIndex;
        int createdIndex;
        int createdByIndex;
        int expiresIndex;
        int startingBidIndex;
        int currentBidIndex;
        int bidderIndex;
        int statusIndex;

        if (cursor != null) {
            if (cursor.getColumnIndex(COLUMN_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                auctionItem.setId(cursor.getInt(idIndex));
            }
            if (cursor.getColumnIndex(COLUMN_ITEM_NAME) != -1) {
                nameIndex = cursor.getColumnIndexOrThrow(COLUMN_ITEM_NAME);
                auctionItem.setName(cursor.getString(nameIndex));
            }
            if (cursor.getColumnIndex(COLUMN_ITEM_DESCRIPTION) != -1) {
                descIndex = cursor.getColumnIndexOrThrow(COLUMN_ITEM_DESCRIPTION);
                auctionItem.setDescription(cursor.getString(descIndex));
            }
            if (cursor.getColumnIndex(COLUMN_DATE_CREATED) != -1) {
                createdIndex = cursor.getColumnIndexOrThrow(COLUMN_DATE_CREATED);
                auctionItem.setCreated(cursor.getString(createdIndex));
            }
            if (cursor.getColumnIndex(COLUMN_CREATED_BY) != -1) {
                createdByIndex = cursor.getColumnIndexOrThrow(COLUMN_CREATED_BY);
                auctionItem.setCreatedBy(cursor.getString(createdByIndex));
            }
            if (cursor.getColumnIndex(COLUMN_EXPIRES_IN) != -1) {
                expiresIndex = cursor.getColumnIndexOrThrow(COLUMN_EXPIRES_IN);
                auctionItem.setExpiresIn(cursor.getString(expiresIndex));
            }
            if (cursor.getColumnIndex(COLUMN_STARTING_BID) != -1) {
                startingBidIndex = cursor.getColumnIndexOrThrow(COLUMN_STARTING_BID);
                auctionItem.setStartingBid(cursor.getInt(startingBidIndex));
            }
            if (cursor.getColumnIndex(COLUMN_CURRENT_BID) != -1) {
                currentBidIndex = cursor.getColumnIndexOrThrow(COLUMN_CURRENT_BID);
                auctionItem.setCurrentBid(cursor.getInt(currentBidIndex));
            }
            if (cursor.getColumnIndex(COLUMN_CURRENT_BIDDER) != -1) {
                bidderIndex = cursor.getColumnIndexOrThrow(COLUMN_CURRENT_BIDDER);
                auctionItem.setCurrentBidder(cursor.getString(bidderIndex));
            }
            if (cursor.getColumnIndex(COLUMN_STATUS) != -1) {
                statusIndex = cursor.getColumnIndexOrThrow(COLUMN_STATUS);
                auctionItem.setStatus(cursor.getString(statusIndex));
            }
        }
        return auctionItem;
    }

    private void setContentValue(AuctionItem item) {
        initialValues = new ContentValues();
        initialValues.put(COLUMN_ITEM_NAME, item.getName());
        initialValues.put(COLUMN_ITEM_DESCRIPTION, item.getDescription());
        initialValues.put(COLUMN_DATE_CREATED, item.getCreated());
        initialValues.put(COLUMN_CREATED_BY, item.getCreatedBy());
        initialValues.put(COLUMN_EXPIRES_IN, item.getExpiresIn());
        initialValues.put(COLUMN_STARTING_BID, item.getStartingBid());
        initialValues.put(COLUMN_CURRENT_BID, item.getCurrentBid());
        initialValues.put(COLUMN_CURRENT_BIDDER, item.getCurrentBidder());
        initialValues.put(COLUMN_STATUS, item.getStatus());
    }

    private ContentValues getContentValue() {
        return initialValues;
    }

}