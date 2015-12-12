package com.bhikadia.boilerplate.data.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.bhikadia.boilerplate.data.AppContentProvider;
import com.bhikadia.boilerplate.data.ItemDbHandler;

/**
 * Created by harsh on 11/12/15.
 */
public class Item extends BaseModel {

    private String text;

    public Item(long id, String createdAt, String updatedAt, String text) {
        super(id, createdAt, updatedAt);
        this.text = text;
    }

    public Item(final Cursor cursor){
        super(cursor);
        this.text = cursor.getString(cursor.getColumnIndex(ItemDbHandler.COL_TEXT));
    }

    public ContentValues getContent(boolean shouldIncludeId) {
        ContentValues values = super.getContent(shouldIncludeId);

        values.put(ItemDbHandler.COL_TEXT, text);

        return values;
    }

    public Uri buildUri() {
        return AppContentProvider.URI_ITEM.buildUpon().appendPath(id + "").build();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
