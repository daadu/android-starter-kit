package com.bhikadia.boilerplate.data;

/**
 * Created by harsh on 11/12/15.
 */
public class ItemDbHandler {

    public static final String TABLE_NAME = "Item";

    // Column names
    public static final String COL_ID = "_id";
    public static final String COL_TEXT = "text";

    // Create table statement
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_TEXT + " TEXT DEFAULT '' "
                    + ")";
}
