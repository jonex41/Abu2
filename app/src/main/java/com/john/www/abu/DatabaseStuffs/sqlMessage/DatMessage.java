package com.john.www.abu.DatabaseStuffs.sqlMessage;

/**
 * Created by MR AGUDA on 04-Dec-17.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by delaroy on 5/10/17.
 */
public class DatMessage extends SQLiteOpenHelper {


    private DatMessage DBHelper;
private Context context;





    // Database Version
    private static final int DATABASE_VERSION =23;

    // Database Name
    private static final String DATABASE_NAME = "Messagetable.db";



    @Override
    public void onCreate(SQLiteDatabase DB1) {



        final String SQL_MESSAGES = "CREATE TABLE " + ConsMessages.Messages.TABLE_MESSAGES + " (" +

                ConsMessages.Messages.COLUMN_FRIENDS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ConsMessages.Messages.FIRSTKEY + " TEXT NOT NULL, " +
                ConsMessages.Messages.COLUMN_FRIENDS_SENDER + "  TEXT NOT NULL, " +
                ConsMessages.Messages.COLUMN_FRIENDS_RECIEVER + " TEXT NOT NULL, " +
                ConsMessages.Messages.COLUMN_FRIENDS_TYPE + " TEXT NOT NULL, " +
                ConsMessages.Messages.COLUMN_FRIENDS_MESSAGE + " TEXT, " +
                ConsMessages.Messages.COLUMN_PICTURE + " BLOB, " +
                ConsMessages.Messages.COLUMN_FRIENDS_TIME + " TEXT NOT NULL " +

                "); ";


        DB1.execSQL(SQL_MESSAGES);

    }



    public DatMessage(Context context) {
        //need to change this after testing
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
    }


    //---opens the database---
    public DatMessage open() throws SQLException {
     //   db = DBHelper.getWritableDatabase();
        return this;
    }


    //---closes the database---
    public void close() {
        DBHelper.close();
    }
    private String DROP_BENEFICIARY_TABLE = "DROP TABLE IF EXISTS " + ConsMessages.Messages.TABLE_MESSAGES;

    @Override
    public void onUpgrade(SQLiteDatabase db2, int oldVersion, int newVersion) {

        db2.execSQL(DROP_BENEFICIARY_TABLE);
        onCreate(db2);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void createToDoForSender(Manss todo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ConsMessages.Messages.FIRSTKEY ,todo.getRecieverId()+ todo.getSenderId());
        values.put(ConsMessages.Messages.COLUMN_FRIENDS_SENDER , todo.getSenderId());
        values.put(ConsMessages.Messages.COLUMN_FRIENDS_RECIEVER , todo.getRecieverId());
        values.put(ConsMessages.Messages.COLUMN_FRIENDS_MESSAGE , todo.getMessage());
        values.put(ConsMessages.Messages.COLUMN_PICTURE , todo.getSendImage());
        values.put(ConsMessages.Messages.COLUMN_FRIENDS_TYPE , todo.getType());
        values.put(ConsMessages.Messages.COLUMN_FRIENDS_TIME , todo.getTime());



        // insert row
       db.insert(ConsMessages.Messages.TABLE_MESSAGES, null, values);
        db.close();
       // Toast.makeText(context, "message created", Toast.LENGTH_SHORT).show();
    }

    public void createToDoForReciever(Manss todo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ConsMessages.Messages.FIRSTKEY ,todo.getSenderId()+ todo.getRecieverId());
        values.put(ConsMessages.Messages.COLUMN_FRIENDS_SENDER , todo.getSenderId());
        values.put(ConsMessages.Messages.COLUMN_FRIENDS_RECIEVER , todo.getRecieverId());
        values.put(ConsMessages.Messages.COLUMN_FRIENDS_MESSAGE , todo.getMessage());
        values.put(ConsMessages.Messages.COLUMN_PICTURE , todo.getSendImage());
        values.put(ConsMessages.Messages.COLUMN_FRIENDS_TYPE , todo.getType());
        values.put(ConsMessages.Messages.COLUMN_FRIENDS_TIME , todo.getTime());



        // insert row
        db.insert(ConsMessages.Messages.TABLE_MESSAGES, null, values);
        db.close();
        // Toast.makeText(context, "message created", Toast.LENGTH_SHORT).show();
    }






    public List<Manss> getindivMessages(String FirstArg){
     List<Manss> todos = new ArrayList<Manss>();
        // array of columns to fetch
        String[] columns = {
                ConsMessages.Messages.FIRSTKEY,
                ConsMessages.Messages.COLUMN_FRIENDS_SENDER,
                ConsMessages.Messages.COLUMN_FRIENDS_RECIEVER,
                ConsMessages.Messages.COLUMN_FRIENDS_MESSAGE,
                ConsMessages.Messages.COLUMN_PICTURE,
                ConsMessages.Messages.COLUMN_FRIENDS_TYPE,
                ConsMessages.Messages.COLUMN_FRIENDS_TIME

        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = ConsMessages.Messages.FIRSTKEY + " LIKE? ";

        String sortOrder =
                ConsMessages.Messages.COLUMN_FRIENDS_MESSAGE + " ASC";
        // selection argument
        String[] selectionArgs = {FirstArg};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor c = db.query(ConsMessages.Messages.TABLE_MESSAGES, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order

            if (c.moveToFirst()) {
                do {


                    Manss td = new Manss();
                    td.setFirstkey(c.getString(c.getColumnIndex(ConsMessages.Messages.FIRSTKEY)));
                    td.setSenderId(c.getString(c.getColumnIndex(ConsMessages.Messages.COLUMN_FRIENDS_SENDER)));
                    td.setRecieverId(c.getString(c.getColumnIndex(ConsMessages.Messages.COLUMN_FRIENDS_RECIEVER)));
                    td.setType(c.getString(c.getColumnIndex(ConsMessages.Messages.COLUMN_FRIENDS_TYPE)));
                    td.setSendImage(c.getBlob(c.getColumnIndex(ConsMessages.Messages.COLUMN_PICTURE)));
                    td.setMessage((c.getString(c.getColumnIndex(ConsMessages.Messages.COLUMN_FRIENDS_MESSAGE))));
                    td.setTime(c.getString(c.getColumnIndex(ConsMessages.Messages.COLUMN_FRIENDS_TIME)));
                    // adding to todo list
                        todos.add(td);
                } while (c.moveToNext());
            }

        c.close();

        db.close();

        return todos;
    }



     public List<Manss> getAllToDosByTag(String tag_name) {
        List<Manss> todos = new ArrayList<Manss>();



        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + ConsMessages.Messages.TABLE_MESSAGES  + " WHERE " +
                ConsMessages.Messages.COLUMN_FRIENDS_RECIEVER + "=?", new String[]{tag_name + ""});


        //  Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list


            if (c.moveToFirst()) {
                do {
                    Manss td = new Manss();
                    td.setMessage((c.getString(c.getColumnIndex(ConsMessages.Messages.COLUMN_FRIENDS_MESSAGE))));
                    td.setTime(c.getString(c.getColumnIndex(ConsMessages.Messages.COLUMN_FRIENDS_TIME)));

                    // adding to todo list
                    todos.add(td);
                } while (c.moveToNext());
            }

        c.close();

        db.close();
        return todos;
    }

    public boolean checkUser(String time) {

        // array of columns to fetch
        String[] columns = {
               ConsMessages.Messages.COLUMN_FRIENDS_TIME
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection =ConsMessages.Messages.COLUMN_FRIENDS_TIME + " = ?";

        // selection argument
        String[] selectionArgs = {time};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(ConsMessages.Messages.TABLE_MESSAGES, //Table to query
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


    private void Deleteall(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ConsMessages.Messages.TABLE_MESSAGES, null, null);
        db.close();
    }

    public boolean deleteMessage(String name) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ConsMessages.Messages.TABLE_MESSAGES, ConsMessages.Messages.FIRSTKEY + "=" + name, null) > 0;
    }

    public boolean deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ConsMessages.Messages.TABLE_MESSAGES,null, null) > 0;
    }

    /*public void deleteRecord(String contact) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(ConsUser.Messages.TABLE_NAME, ConsUser.Messages.COLUMN_FRIENDS_ID + " = ?", new String[]{contact});
        db.close();
    }*/
}

