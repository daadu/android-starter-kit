package com.bhikadia.boilerplate.data.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.bhikadia.boilerplate.data.BaseDbHandler;
import com.bhikadia.boilerplate.data.ItemDbHandler;
import com.bhikadia.boilerplate.util.DateTimeUtil;

import java.util.Date;

/**
 * Created by harsh on 12/12/15.
 */
public class BaseModel {
    long id = -1;
    private String createdAt;
    private String updatedAt;

    public BaseModel(long id, String createdAt, String updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public BaseModel(final Cursor cursor) {
        this.id = cursor.getLong(cursor.getColumnIndex(BaseDbHandler.COL_ID));
        this.createdAt = cursor.getString(cursor.getColumnIndex(BaseDbHandler.COL_CREATED_AT));
        this.updatedAt = cursor.getString(cursor.getColumnIndex(BaseDbHandler.COL_UPDATED_AT));
    }

    public ContentValues getContent(boolean shouldIncludeId) {
        ContentValues values = new ContentValues();
        if (shouldIncludeId)
            values.put(ItemDbHandler.COL_ID, id);

        values.put(BaseDbHandler.COL_CREATED_AT, createdAt);
        values.put(BaseDbHandler.COL_UPDATED_AT, updatedAt);
        return values;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = DateTimeUtil.dateToString(createdAt);
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = DateTimeUtil.dateToString(updatedAt);
    }
}
