package com.fournot7.upswing.data.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.fournot7.upswing.data.ItemDbHandler;

/**
 * Created by harsh on 11/12/15.
 */
public class Item {

    private long id = -1;
    private String text;

    public Item(long id, String text) {
        this.id = id;
        this.text = text;
    }

    public Item(final Cursor cursor){
        this.id = cursor.getLong(cursor.getColumnIndex(ItemDbHandler.COL_ID));
        this.text = cursor.getString(cursor.getColumnIndex(ItemDbHandler.COL_TEXT));
    }

    public ContentValues getContent(boolean shouldIncludeId) {
        final ContentValues values = new ContentValues();
        if (shouldIncludeId)
            values.put(ItemDbHandler.COL_ID, id);

        values.put(ItemDbHandler.COL_TEXT, text);

        return values;
    }

    /*TODO public Uri buildUri() {
        return AppContentProvider.URI_OFFERS.buildUpon().appendPath(id + "").build();
    }*/

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
