package com.example.cis_470_term_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDatabase";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_USERS = "users";
    private static final String TABLE_DREAMS = "dreams";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";


    private static final String CREATE_USER_TABLE = "CREATE TABLE "
            + TABLE_USERS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASSWORD + " TEXT" + ")";

    private static final String CREATE_DREAM_TABLE = "CREATE TABLE "
            + TABLE_DREAMS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_ID + " INTEGER,"
            + COLUMN_TITLE + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_DATE + " TEXT" + ")";


    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_DREAM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DREAMS);
        onCreate(db);
    }

    // User Registration
    public void addUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        long id = db.insert(TABLE_USERS, null, values);
        db.close();
    }

    // User Login
    public int checkUser(String email, String password) {
        String[] columns = { COLUMN_ID };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_EMAIL + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = { email, password };
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int userId = -1;
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return userId;
    }

    // Add Dream
    public void addDream(String title, String description, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_USER_ID, userId);
        db.insert(TABLE_DREAMS, null, values);
        db.close();
    }

    public void deleteDream(int dreamId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = { String.valueOf(dreamId) };
        db.delete(TABLE_DREAMS, whereClause, whereArgs);
        db.close();
    }

    // Fetch User's Dreams
    public Cursor getUserDreams(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_ID + " = ?";
        String[] selectionArgs = { String.valueOf(userId) };
        Cursor cursor = db.query(TABLE_DREAMS, null, selection, selectionArgs, null, null, null);
        return cursor;
    }
}
