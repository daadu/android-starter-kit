package com.bhikadia.boilerplate.app;

import android.app.Application;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.bhikadia.boilerplate.R;
import com.bhikadia.boilerplate.activity.LoginActivity;
import com.bhikadia.boilerplate.data.DatabaseHandler;
import com.bhikadia.boilerplate.util.AccountUtil;
import com.bhikadia.boilerplate.util.LruBitmapCache;
import com.bhikadia.boilerplate.util.PrefManager;

/**
 * Created by harsh on 10/12/15.
 */
public class MyApplication extends Application {

    public final String TAG = MyApplication.class.getSimpleName();

    private static MyApplication myApplication;

    private PrefManager pref;
    private AccountUtil accountUtil;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
    }

    public static synchronized  MyApplication getInstance() {
        return myApplication;
    }

    public PrefManager getPrefManager() {
        if (pref == null)
            pref = new PrefManager(getApplicationContext());

        return pref;
    }

    public AccountUtil getAccountUtl(){
        if (accountUtil == null)
            accountUtil = new AccountUtil(getApplicationContext());

        return accountUtil;
    }

//    Volley Method
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


    public void clearUserData(SQLiteDatabase db) {
        // cancel all volley requests
        getRequestQueue().cancelAll(getApplicationContext());

        // Clearing all the shared preferences
        getPrefManager().resetPreferences();

        // Clear all the database tables
        DatabaseHandler.getInstance(getApplicationContext()).dropAllTables(db);
    }

    public void verifySession() {
        boolean isLoggedIn = getAccountUtl().hasAccount();

        if (!isLoggedIn) {

            clearUserData(null);

            Toast.makeText(getApplicationContext(), getString(R.string.logout_message), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(MyApplication.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
