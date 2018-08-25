package com.john.www.abu.DatabaseStuffs.sqlUsers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MR AGUDA on 18-Dec-17.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private DatabaseHelper DBHelper;
    public SQLiteDatabase db;

    // Database Version
    private static final int DATABASE_VERSION =4;

    // Database Name
    private static final String DATABASE_NAME = "Message.db";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + ConsUser.Messages.TABLE_NAME + " (" +
                ConsUser.Messages.COLUMN_FRIENDS_ID + " TEXT NOT NULL," +
                ConsUser.Messages.COLUMN_FRIENDS_NAME + " TEXT NOT NULL, " +
                ConsUser.Messages.COLUMN_FRIENDS_STATUS + " TEXT NOT NULL, " +
                ConsUser.Messages.COLUMN_FRIENDS_IMAGE + " BLOB " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    //drop beneficiary table
    private String DROP_BENEFICIARY_TABLE = "DROP TABLE IF EXISTS " + ConsUser.Messages.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //---opens the database---
    public DatabaseHelper open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }


    //---closes the database---
   public void close() {
        DBHelper.close();
}


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_BENEFICIARY_TABLE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    //Method to create beneficiary records


    public void insertRecord(UserDatabase contact) {

        if(!checkUser(contact.getUserid())){
            UpdatesFriends(contact);
            return;
        }
       SQLiteDatabase db = this.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put(ConsUser.Messages.COLUMN_FRIENDS_ID, contact.getUserid());
        contentValues.put(ConsUser.Messages.COLUMN_FRIENDS_NAME, contact.getName());
        contentValues.put(ConsUser.Messages.COLUMN_FRIENDS_STATUS, contact.getStatus());
        contentValues.put(ConsUser.Messages.COLUMN_FRIENDS_IMAGE, contact.getImage());
    db.insert(ConsUser.Messages.TABLE_NAME, null, contentValues);

        db.close();
    }

    public void insertRecord2(UserDatabase contact, String bytes) {

        if(!checkUser(contact.getUserid())){
            UpdatesFriends(contact);
            return;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConsUser.Messages.COLUMN_FRIENDS_ID, contact.getUserid());
        contentValues.put(ConsUser.Messages.COLUMN_FRIENDS_NAME, contact.getName());
        contentValues.put(ConsUser.Messages.COLUMN_FRIENDS_STATUS, contact.getStatus());
        contentValues.put(ConsUser.Messages.COLUMN_FRIENDS_IMAGE, bytes);
        db.insert(ConsUser.Messages.TABLE_NAME, null, contentValues);

        db.close();
    }

    public boolean checkUser(String userid) {

        // array of columns to fetch
        String[] columns = {
                ConsUser.Messages.COLUMN_FRIENDS_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = ConsUser.Messages.COLUMN_FRIENDS_ID + " = ?";

        // selection argument
        String[] selectionArgs = {userid};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(ConsUser.Messages.TABLE_NAME, //Table to query
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

    public List<UserDatabase> getAllRecords() {
        // array of columns to fetch
        String[] columns = {
                ConsUser.Messages.COLUMN_FRIENDS_ID,
                ConsUser.Messages.COLUMN_FRIENDS_NAME,
                ConsUser.Messages.COLUMN_FRIENDS_STATUS,
                ConsUser.Messages.COLUMN_FRIENDS_IMAGE
        };
        // sorting orders
        String sortOrder =
                ConsUser.Messages.COLUMN_FRIENDS_NAME + " ASC";
        List<UserDatabase> beneficiaryList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(ConsUser.Messages.TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order


        // Traversing through all rows and adding to list




                    if (cursor.moveToFirst()) {
                        do {
                        UserDatabase beneficiary = new UserDatabase();
                        beneficiary.setUserid(cursor.getString(cursor.getColumnIndex(ConsUser.Messages.COLUMN_FRIENDS_ID)));
                        beneficiary.setName(cursor.getString(cursor.getColumnIndex(ConsUser.Messages.COLUMN_FRIENDS_NAME)));
                        beneficiary.setStatus(cursor.getString(cursor.getColumnIndex(ConsUser.Messages.COLUMN_FRIENDS_STATUS)));
                        beneficiary.setImagebyte(cursor.getBlob(cursor.getColumnIndex(ConsUser.Messages.COLUMN_FRIENDS_IMAGE)));
                        // Adding user record to list
                        beneficiaryList.add(beneficiary);
                        } while (cursor.moveToNext());
                    }



        cursor.close();
     db.close();



    return beneficiaryList;
    }


    public void UpdatesFriends(UserDatabase alluser) {
        if(checkUser(alluser.getUserid())){
            insertRecord(alluser);
            return;
        }

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( ConsUser.Messages.COLUMN_FRIENDS_NAME, alluser.getName());
          values.put(ConsUser.Messages.COLUMN_FRIENDS_STATUS, alluser.getStatus());
        values.put(ConsUser.Messages.COLUMN_FRIENDS_IMAGE, alluser.getImage());

     db.update(ConsUser.Messages.TABLE_NAME,values, ConsUser.Messages.COLUMN_FRIENDS_ID+ "= "+ alluser.getUserid(), null);

      //  myDB.update(TableName, cv, "_id="+id, null);
        db.close();
    }

    public void UpdatesFriends2(UserDatabase alluser, String ImageUrl) {
        if(checkUser(alluser.getUserid())){
            insertRecord2(alluser, ImageUrl);
            return;
        }

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ConsUser.Messages.COLUMN_FRIENDS_ID, alluser.getUserid());
        values.put(ConsUser.Messages.COLUMN_FRIENDS_IMAGE, ImageUrl);

        db.update(ConsUser.Messages.TABLE_NAME,values, ConsUser.Messages.COLUMN_FRIENDS_ID+ "= "+ alluser.getUserid(), null);

        //  myDB.update(TableName, cv, "_id="+id, null);
        db.close();
    }


    public void UpdatesFriends2(String userid, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ConsUser.Messages.COLUMN_FRIENDS_IMAGE, image);
        values.put(ConsUser.Messages.COLUMN_FRIENDS_ID, userid);

        db.update(ConsUser.Messages.TABLE_NAME,values, ConsUser.Messages.COLUMN_FRIENDS_ID+ "= "+ userid, null);

        //  myDB.update(TableName, cv, "_id="+id, null);
        db.close();
    }
    public void dropDB() {
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        db.execSQL(DROP_BENEFICIARY_TABLE);


    }

    public void deleteRecord(String contact) {
       SQLiteDatabase db = this.getReadableDatabase();
        db.delete(ConsUser.Messages.TABLE_NAME, ConsUser.Messages.COLUMN_FRIENDS_ID + " = ?", new String[]{contact});
        db.close();
    }


    public void deleteContact() {
        SQLiteDatabase db = this.getWritableDatabase();
         db.delete(ConsUser.Messages.TABLE_NAME,null, null);
         db.close();
    }
}

