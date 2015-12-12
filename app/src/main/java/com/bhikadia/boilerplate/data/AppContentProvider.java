package com.bhikadia.boilerplate.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.bhikadia.boilerplate.util.DateTimeUtil;

/**
 * Created by harsh on 12/12/15.
 */
public class AppContentProvider extends ContentProvider {
    private String TAG = AppContentProvider.class.getSimpleName();

    //TODO change authority
    private static final String AUTHORITY = "com.bhikadia.boilerplate.provider";
    private static final String SCHEME = "content://";

    private final UriMatcher uriMatcher = buildUriMatcher();
    private final int ITEM_ALL = 1;
    private final int ITEM_SINGLE = 2;

    private final static String ITEM_SCHEMA = SCHEME + AUTHORITY + "/item";

    public final static Uri URI_ITEM = Uri.parse(ITEM_SCHEMA);

    private UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "item", ITEM_ALL);
        uriMatcher.addURI(AUTHORITY, "item/#", ITEM_SINGLE);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case ITEM_ALL:
                return "vnd.android.cursor.dir/" + AUTHORITY + ".item";
            case ITEM_SINGLE:
                return "vnd.android.cursor.item/" + AUTHORITY + ".item";
            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = DatabaseHandler.getInstance(getContext()).getReadableDatabase();
        Cursor retCursor;

        switch (uriMatcher.match(uri)) {
            case ITEM_ALL: {
                retCursor = db.query(
                        ItemDbHandler.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder
                );
                break;
            }
            case ITEM_SINGLE: {
                String itemId = uri.getPathSegments().get(1);

                retCursor = db.query(
                        ItemDbHandler.TABLE_NAME,
                        projection,
                        ItemDbHandler.COL_ID + " = " + itemId,
                        null, null, null, null
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = DatabaseHandler.getInstance(getContext()).getWritableDatabase();
        Uri retUri;

        contentValues.put(BaseDbHandler.COL_CREATED_AT, DateTimeUtil.getNowDateTime());
        contentValues.put(BaseDbHandler.COL_UPDATED_AT, DateTimeUtil.getNowDateTime());

        switch (uriMatcher.match(uri)) {
            case ITEM_ALL: {
                long insertedId = db.insert(ItemDbHandler.TABLE_NAME, null, contentValues);

                if (insertedId > 0)
                    retUri = ContentUris.withAppendedId(AppContentProvider.URI_ITEM, insertedId);
                else
                    throw new SQLException("Failed to insert row : " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }
        getContext().getContentResolver().notifyChange(retUri, null);
        return retUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArg) {
        SQLiteDatabase db = DatabaseHandler.getInstance(getContext()).getWritableDatabase();
        int rowDeleted;

        switch (uriMatcher.match(uri)) {
            case ITEM_ALL: {
                rowDeleted = db.delete(
                        ItemDbHandler.TABLE_NAME,
                        selection, selectionArg
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }
        if (selection == null || rowDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase db = DatabaseHandler.getInstance(getContext()).getWritableDatabase();
        String tableName = "";
        // row updated count
        int updateCount = 0;

        contentValues.put(BaseDbHandler.COL_UPDATED_AT, DateTimeUtil.getNowDateTime());

        switch (uriMatcher.match(uri)) {
            case ITEM_ALL:
                tableName = ItemDbHandler.TABLE_NAME;
                break;
            case ITEM_SINGLE:
                String itemId = uri.getPathSegments().get(1);
                selection = ItemDbHandler.COL_ID + "=" + itemId + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
                tableName = ItemDbHandler.TABLE_NAME;
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }

        try {
            updateCount = db.update(tableName, contentValues, selection, selectionArgs);
        } catch (Exception e) {
            Log.e(TAG, "Exception", e);
        }

        if (updateCount > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return updateCount;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        SQLiteDatabase db = DatabaseHandler.getInstance(getContext()).getWritableDatabase();

        switch (uriMatcher.match(uri)){
            case ITEM_ALL :{
                db.beginTransaction();
                int returnCount = 0;
                try{
                    for (ContentValues value : values){
                        value.put(ItemDbHandler.COL_CREATED_AT, DateTimeUtil.getNowDateTime());
                        value.put(ItemDbHandler.COL_UPDATED_AT, DateTimeUtil.getNowDateTime());
                        long _id = db.insert(ItemDbHandler.TABLE_NAME , null , value);
                        if(-1 != _id)
                            returnCount++;
                    }
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri , null);
                return returnCount;
            }
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
