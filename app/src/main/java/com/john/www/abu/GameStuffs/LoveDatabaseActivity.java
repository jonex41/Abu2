package com.john.www.abu.GameStuffs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




import java.util.ArrayList;
import java.util.List;
public class LoveDatabaseActivity extends SQLiteOpenHelper {
    public static final String TABLE_LOVE = "love_me";


    public static final String COLUMN_LOVE_ID ="_id" ;
    public static final String COLUMN_BOY_NAME = "boy_name";
    public static final String COLUMN_GIRL_NAME = "gril_name";
    public static final String COLUMN_PERCENT = "percent_love";
    public static final String COLUMN_MESSAGE = "love_message";

    public SQLiteDatabase db;

    // Database Version
    private static final int DATABASE_VERSION =2;

    // Database Name
    private static final String DATABASE_NAME = "love.db";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + TABLE_LOVE + " (" +
                COLUMN_LOVE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_BOY_NAME + " TEXT NOT NULL, " +
                COLUMN_MESSAGE + " TEXT NOT NULL, " +
                COLUMN_GIRL_NAME + " TEXT NOT NULL, " +
                COLUMN_PERCENT + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    //drop beneficiary table
    private String DROP_BENEFICIARY_TABLE = "DROP TABLE IF EXISTS " + TABLE_LOVE;

    public LoveDatabaseActivity(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_BENEFICIARY_TABLE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public boolean checkUser(String boyName, String girlName) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_BOY_NAME,
                COLUMN_GIRL_NAME
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_BOY_NAME + " LIKE? "+ " AND "+ COLUMN_GIRL_NAME +" LIKE? ";


        // selection argument
        String[] selectionArgs = {boyName, girlName};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_LOVE, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    //Method to create beneficiary records


    public void addtoCart(LoveModel contact) {


        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_BOY_NAME, contact.getBoy_name());
        contentValues.put(COLUMN_GIRL_NAME, contact.getGirl_name());
        contentValues.put(COLUMN_MESSAGE, contact.getMessage_love());
        contentValues.put(COLUMN_PERCENT, contact.getPercent_value());
        db.insert(TABLE_LOVE, null, contentValues);

        db.close();
    }

    public LoveModel getindivMessages(String FirstArg, String SecondArg){
        LoveModel td = new LoveModel();
        // array of columns to fetch
        String[] columns = {
                COLUMN_LOVE_ID,
                COLUMN_MESSAGE,
                COLUMN_PERCENT,
                COLUMN_GIRL_NAME,
                COLUMN_BOY_NAME

        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_BOY_NAME + " LIKE? "+ " AND "+ COLUMN_GIRL_NAME +" LIKE? ";

       // String sortOrder = ConsMessages.Messages.COLUMN_FRIENDS_MESSAGE + " ASC";
        // selection argument
        String[] selectionArgs = {FirstArg, SecondArg};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_LOVE, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order

        if (cursor.moveToFirst()) {
            do {



                td.setMessage_love(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE)));
                td.setPercent_value(cursor.getString(cursor.getColumnIndex(COLUMN_PERCENT)));
                // adding to todo list

            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        return td;
    }







}

