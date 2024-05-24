package com.example.book_airplanetacket;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.book_airplanetacket.DatabaseHelper;

public class User {
    private int id;
    private String username;
    private String password;

    private static User currentUser;

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static User authenticateUser(Context context, String inputUsername, String inputPassword) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_USERNAME,
                DatabaseHelper.COLUMN_PASSWORD
        };

        String selection = DatabaseHelper.COLUMN_USERNAME + " = ? AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = { inputUsername, inputPassword };

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            String username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD));

            cursor.close();
            return new User(userId, username, password);
        } else {
            cursor.close();
            return null;
        }
    }
}
