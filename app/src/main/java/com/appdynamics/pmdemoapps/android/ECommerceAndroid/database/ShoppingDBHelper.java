package com.appdynamics.pmdemoapps.android.ECommerceAndroid.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.appdynamics.pmdemoapps.android.ECommerceAndroid.database.ShoppingDBCart.ItemList;


public class ShoppingDBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ShoppingCart.db";
    
    /***************************************************************************************/
    /*             Table creation and deletion                                                                        */
    /***************************************************************************************/
    
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    
    //    ITEMS table
    private static final String SQL_CREATE_ITEMS =
        "CREATE TABLE " + ItemList.TABLE_NAME + " (" +
        		ItemList._ID + " INTEGER PRIMARY KEY," +
        		ItemList.ITEM_ID + TEXT_TYPE + COMMA_SEP +
        		ItemList.ITEM_NAME + TEXT_TYPE + COMMA_SEP +
        		ItemList.ITEM_IMAGE_PATH + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ITEMS =
        "DROP TABLE IF EXISTS " + ItemList.TABLE_NAME;
    
    /***************************************************************************************/
    /*             Table creation and deletion                                                                        */
    /***************************************************************************************/
    
    
    

    public ShoppingDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ITEMS);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ITEMS);
        onCreate(db);
    }
    
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}