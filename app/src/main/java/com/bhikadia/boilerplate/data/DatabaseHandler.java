package com.bhikadia.boilerplate.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bhikadia.boilerplate.app.AppConfig;
import com.bhikadia.boilerplate.app.MyApplication;

/**
 * Created by harsh on 11/12/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private String TAG = DatabaseHandler.class.getSimpleName();

    private static final int DATABASE_VERSION = AppConfig.DATABASE_VERSION;
    private static final String DATABASE_NAME = AppConfig.DATABASE_NAME;

    private final Context context;
    private static DatabaseHandler singleton;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static DatabaseHandler getInstance(final Context context) {
        if (singleton == null) {
            singleton = new DatabaseHandler(context);
        }
        return singleton;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating Item table
        db.execSQL(ItemDbHandler.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        MyApplication.getInstance().clearUserData(db);
    }

    public void dropAllTables(SQLiteDatabase db) {
        if(db == null) {
            db = this.getWritableDatabase();
        }

        db.execSQL("DROP TABLE IF EXISTS " + ItemDbHandler.TABLE_NAME);

        onCreate(db);
    }
}
