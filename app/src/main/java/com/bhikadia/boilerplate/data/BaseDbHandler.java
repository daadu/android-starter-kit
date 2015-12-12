package com.bhikadia.boilerplate.data;

import android.content.Context;
import android.net.Uri;

import com.bhikadia.boilerplate.data.model.BaseModel;

/**
 * Created by harsh on 12/12/15.
 */
public abstract class BaseDbHandler {

    public static final String COL_ID = "_id";
    public static final String COL_CREATED_AT = "created_at";
    public static final String COL_UPDATED_AT = "updated_at";

    public static final String CREATE_BASE_FIELDS =
            COL_ID + " INTEGER PRIMARY KEY,"
                    + COL_CREATED_AT + " TIMESTAMP NOT NULL DEFAULT (datetime('now','localtime')),"
                    + COL_UPDATED_AT + " TIMESTAMP NOT NULL DEFAULT (datetime('now','localtime'))";

    private static BaseDbHandler singleton;
    Context context;

    public BaseDbHandler(Context context) {
        this.context = context;
    }

    public BaseDbHandler getInstance(Context context){
        if (singleton != null)
            singleton = new ItemDbHandler(context);

        return singleton;
    }

    abstract Uri buildUri(long id);

    abstract public BaseModel get(long id);

    abstract void insert(BaseModel model);

    abstract public void update(BaseModel model);

    abstract public void delete(BaseModel model);
}
