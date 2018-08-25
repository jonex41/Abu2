package com.john.www.abu.ShoppingStuffs.DatabaseForCart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.john.www.abu.ShoppingStuffs.Models.ListOfShop;

import java.util.ArrayList;
import java.util.List;


public class CartDatabase extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "cart_table";


    public static final String COLUMN_CART_ID ="_id" ;
    public static final String COLUMN_CART_NAME = "name";
    public static final String COLUMN_IMAGE = "image_url";
    public static final String COLUMN_TOTAL = "total";
    public SQLiteDatabase db;

    // Database Version
    private static final int DATABASE_VERSION =1;

    // Database Name
    private static final String DATABASE_NAME = "Cart.db";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_CART_NAME + " TEXT NOT NULL, " +
                COLUMN_IMAGE + " TEXT NOT NULL, " +
                COLUMN_TOTAL + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    //drop beneficiary table
    private String DROP_BENEFICIARY_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public CartDatabase(Context context) {
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


    //Method to create beneficiary records


    public void addtoCart(ListOfShop contact) {


        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_CART_NAME, contact.getNameOfItem());
        contentValues.put(COLUMN_IMAGE, contact.getImageUrl());
        contentValues.put(COLUMN_TOTAL, contact.getTotal_quantity_demanded());
        db.insert(TABLE_NAME, null, contentValues);

        db.close();
    }

    public String Sum( ) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = new String[] {"SUM("+COLUMN_TOTAL+")" };




        Cursor cursor = db.query(TABLE_NAME, columns,null,null,null,null,null);
        String result = "";

        int index_SUM = cursor.getColumnIndex("SUM("+COLUMN_TOTAL+")");


        for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
            result = result + cursor.getString(index_SUM) + "\n";
        }

        db.close();




        return result;
    }

    public List<ListOfShop> getAllRecords() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_CART_ID,
                COLUMN_CART_NAME,
               COLUMN_IMAGE,
               COLUMN_TOTAL
        };
        // sorting orders
      /*  String sortOrder =
                ConsUser.Messages.COLUMN_FRIENDS_NAME + " ASC";*/
        List<ListOfShop> beneficiaryList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order


        // Traversing through all rows and adding to list




        if (cursor.moveToFirst()) {
            do {
                ListOfShop beneficiary = new ListOfShop();
                beneficiary.setNameOfItem(cursor.getString(cursor.getColumnIndex(COLUMN_CART_NAME)));
                beneficiary.setTotal_quantity_demanded(cursor.getString(cursor.getColumnIndex(COLUMN_TOTAL)));
                beneficiary.setImageUrl(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)));
                // Adding user record to list
                beneficiaryList.add(beneficiary);
            } while (cursor.moveToNext());
        }



        cursor.close();
        db.close();



        return beneficiaryList;
    }




    public void deleteRecord(String contact) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME, COLUMN_CART_NAME + " = ?", new String[]{contact});
        db.close();
    }


    public void deleteContact() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null, null);
        db.close();
    }

    public long getProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        return count;
    }
}

