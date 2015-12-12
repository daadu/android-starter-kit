package com.bhikadia.boilerplate.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.bhikadia.boilerplate.data.model.BaseModel;
import com.bhikadia.boilerplate.data.model.Item;

/**
 * Created by harsh on 11/12/15.
 */
public class ItemDbHandler extends BaseDbHandler {
    private final String TAG = ItemDbHandler.class.getSimpleName();

    public static final String TABLE_NAME = "Item";

    private static ItemDbHandler singleton;

    // Column names
    public static final String COL_TEXT = "text";

    // Create table statement
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COL_TEXT + " TEXT DEFAULT '',"
                    + CREATE_BASE_FIELDS
                    + ")";

    public ItemDbHandler(Context context) {
        super(context);
    }

    @Override
    public ItemDbHandler getInstance(Context context) {
        return (ItemDbHandler) super.getInstance(context);
    }

    @Override
    Uri buildUri(long id) {
        return AppContentProvider.URI_ITEM.buildUpon().appendPath(id + "").build();
    }

    @Override
    public Item get(long id) {
        Cursor cursor = context.getContentResolver().query(
                buildUri(id),null, null, null, null
        );
        Item item =  new Item(cursor);
        cursor.close();
        return item;
    }

    @Override
    void insert(BaseModel model) {
        context.getContentResolver().insert(
                AppContentProvider.URI_ITEM, model.getContent(true)
        );
    }

    @Override
    public void update(BaseModel model) {
        context.getContentResolver().update(
                buildUri(model.getId()), model.getContent(false), null, null
        );
    }

    @Override
    public void delete(BaseModel model) {
        context.getContentResolver().delete(
                AppContentProvider.URI_ITEM,
                COL_ID + " = " + model.getId(),
                null
        );
    }
}
