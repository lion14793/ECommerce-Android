package com.appdynamics.pmdemoapps.android.ECommerceAndroid.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.appdynamics.pmdemoapps.android.ECommerceAndroid.database.ShoppingDBCart.ItemList;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.model.Item;

public class ItemsDataSource {
	//private Context ctx;
	private ShoppingDBHelper dbHelper;
	
	public ItemsDataSource(Context ctx){
		//this.ctx = ctx;
		dbHelper = new ShoppingDBHelper(ctx);
	}
	
	
	public void insertItem(Item item){
		// Gets the data repository in write mode
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(ItemList.ITEM_ID, item.getId());
		values.put(ItemList.ITEM_NAME, item.getTitle());
		values.put(ItemList.ITEM_IMAGE_PATH, item.getImagePath());

		// Insert the new row, returning the primary key value of the new row
		long newRowId = db.insert(
				ItemList.TABLE_NAME,null,values);
	}
	
	
	public Cursor getItems(){
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
				ItemList.ITEM_ID,
				ItemList.ITEM_NAME,ItemList.ITEM_IMAGE_PATH
		    };

		// How you want the results sorted in the resulting Cursor
		String sortOrder =
				ItemList.ITEM_ID + " DESC";
		String selection = null;
		String[] selectionArgs = null;

		Cursor c = db.query(
				ItemList.TABLE_NAME,  // The table to query
		    projection,                               // The columns to return
		    selection,                                // The columns for the WHERE clause
		    selectionArgs,                            // The values for the WHERE clause
		    null,                                     // don't group the rows
		    null,                                     // don't filter by row groups
		    sortOrder                                 // The sort order
		    );
		return c;
	}
	
	
	public void cleanItems(){
		SQLiteDatabase db = dbHelper.getWritableDatabase();	
		db.delete(ItemList.TABLE_NAME, null, null);
	}
}
