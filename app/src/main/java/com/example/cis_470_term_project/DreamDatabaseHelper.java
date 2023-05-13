package com.example.cis_470_term_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DreamDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DreamDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_DREAMS = "dreams";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";

    private static final String CREATE_DREAM_TABLE = "CREATE TABLE "
            + TABLE_DREAMS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_TITLE + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_DATE + " TEXT" + ")";

    public DreamDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DREAM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DREAMS);
        onCreate(db);
    }

    // add methods for adding, retrieving, and deleting dreams here
    // Add dream
    public void addDream(Dream dream) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, dream.getTitle());
        values.put(COLUMN_DESCRIPTION, dream.getDescription());
        values.put(COLUMN_DATE, dream.getDate());
        db.insert(TABLE_DREAMS, null, values);
        db.close();
    }

    // Get all dreams
    public List<Dream> getAllDreams() {
        List<Dream> dreamList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_DREAMS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Dream dream = new Dream();
                dream.setId(Integer.parseInt(cursor.getString(0)));
                dream.setTitle(cursor.getString(1));
                dream.setDescription(cursor.getString(2));
                dream.setDate(cursor.getString(3));
                dreamList.add(dream);
            } while (cursor.moveToNext());
        }

        return dreamList;
    }

    // Delete dream
    public void deleteDream(Dream dream) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DREAMS, COLUMN_ID + " = ?", new String[]{String.valueOf(dream.getId())});
        db.close();
    }

}
