package com.appdynamics.pmdemoapps.android.ECommerceAndroid.database;

import android.provider.BaseColumns;

public final class ShoppingDBCart {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private ShoppingDBCart() {}

    /* Inner class that defines the table contents */
    public static abstract class ItemList implements BaseColumns {
        public static final String TABLE_NAME = "ITEMS";
        public static final String ITEM_ID = "ITEM_ID";
        public static final String ITEM_NAME = "NAME";
        public static final String ITEM_IMAGE_PATH = "PATH";
    }
}