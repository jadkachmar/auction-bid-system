package com.crossover.jkachmar.auctionawesome.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import com.crossover.jkachmar.auctionawesome.db.dao.base.IUserDAO;
import com.crossover.jkachmar.auctionawesome.db.dao.provider.DBContentProvider;
import com.crossover.jkachmar.auctionawesome.db.schema.IUserSchema;
import com.crossover.jkachmar.auctionawesome.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DBContentProvider implements IUserSchema, IUserDAO {

    private Cursor cursor;
    private ContentValues initialValues;

    public UserDAO(SQLiteDatabase db) {
        super(db);
    }

    public User fetchUserByName(String username) {
        final String selectionArgs[] = {String.valueOf(username)};
        final String selection = COLUMN_USER_NAME + " = ?";
        User user = null;
        cursor = super.query(USER_TABLE, USER_COLUMNS, selection,
                selectionArgs, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                user = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return user;
    }

    public List<User> fetchAllUsers() {
        List<User> userList = new ArrayList<User>();
        cursor = super.query(USER_TABLE, USER_COLUMNS, null,
                null, COLUMN_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                User user = cursorToEntity(cursor);
                userList.add(user);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return userList;
    }

    public boolean addUser(User user) {
        setContentValue(user);
        try {
            return super.insert(USER_TABLE, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex) {
            return false;
        }
    }

    protected User cursorToEntity(Cursor cursor) {

        User user = new User();

        int idIndex;
        int userNameIndex;
        int emailIndex;
        int genderIndex;
        int passwordIndex;

        if (cursor != null) {
            if (cursor.getColumnIndex(COLUMN_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                user.setId(cursor.getInt(idIndex));
            }
            if (cursor.getColumnIndex(COLUMN_USER_NAME) != -1) {
                userNameIndex = cursor.getColumnIndexOrThrow(COLUMN_USER_NAME);
                user.setUsername(cursor.getString(userNameIndex));
            }
            if (cursor.getColumnIndex(COLUMN_EMAIL) != -1) {
                emailIndex = cursor.getColumnIndexOrThrow(COLUMN_EMAIL);
                user.setEmail(cursor.getString(emailIndex));
            }
            if (cursor.getColumnIndex(COLUMN_GENDER) != -1) {
                genderIndex = cursor.getColumnIndexOrThrow(COLUMN_GENDER);
                user.setGender(cursor.getString(genderIndex));
            }
            if (cursor.getColumnIndex(COLUMN_PASSWORD) != -1) {
                passwordIndex = cursor.getColumnIndexOrThrow(COLUMN_PASSWORD);
                user.setPassword(cursor.getString(passwordIndex));
            }
        }
        return user;
    }

    private void setContentValue(User user) {
        initialValues = new ContentValues();
        initialValues.put(COLUMN_USER_NAME, user.getUsername());
        initialValues.put(COLUMN_EMAIL, user.getEmail());
        initialValues.put(COLUMN_GENDER, user.getGender());
        initialValues.put(COLUMN_PASSWORD, user.getPassword());
    }

    private ContentValues getContentValue() {
        return initialValues;
    }

}