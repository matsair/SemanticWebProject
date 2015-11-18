package com.matsschade.semanticquizapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mats on 11/11/15.
 */
public class QuestionDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Questions.db";

    private static final String SQL_CREATE_QUESTIONS = "CREATE TABLE " + DbSchema.Questions.TABLE_NAME +
            " (" + DbSchema.Questions.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
            DbSchema.Questions.COLUMN_NAME_TEXT + " TEXT," +
            DbSchema.Questions.COLUMN_NAME_CATEGORY_ID + " INTEGER)";

    private static final String SQL_CREATE_CATEGORIES = "CREATE TABLE " + DbSchema.Categories.TABLE_NAME +
            " (" + DbSchema.Categories.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
            DbSchema.Categories.COLUMN_NAME_TEXT + " TEXT)";

    private static final String SQL_DELETE_QUESTIONS = "DROP TABLE IF EXISTS " + DbSchema.Questions.TABLE_NAME;
    private static final String SQL_DELETE_CATEGORIES = "DROP TABLE IF EXISTS " + DbSchema.Categories.TABLE_NAME;

    public QuestionDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_QUESTIONS);
        db.execSQL(SQL_CREATE_CATEGORIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_QUESTIONS);
        db.execSQL(SQL_DELETE_CATEGORIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static void addQuestion(String question, int categoryId) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        ContentValues values = new ContentValues();
        values.put(DbSchema.Questions.COLUMN_NAME_TEXT, question);
        values.put(DbSchema.Questions.COLUMN_NAME_CATEGORY_ID, categoryId);

        db.insert(DbSchema.Questions.TABLE_NAME, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public static void addCategory(String categoryName) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        ContentValues values = new ContentValues();
        values.put(DbSchema.Categories.COLUMN_NAME_TEXT, categoryName);

        db.insert(DbSchema.Categories.TABLE_NAME, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public static int getCategoryId(String categoryName) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor c = db.rawQuery("SELECT " + DbSchema.Categories.COLUMN_NAME_ID +
                " FROM " + DbSchema.Categories.TABLE_NAME +
                " WHERE " + DbSchema.Categories.COLUMN_NAME_TEXT +
                " LIKE " + "\"" + categoryName + "\";", null);

        if (c.moveToFirst()) {
            return c.getInt(0);
        }
        else return 0;
    }
}
