package com.fournot7.upswing.app;

import android.app.Application;

import com.fournot7.upswing.util.PrefManager;

/**
 * Created by harsh on 10/12/15.
 */
public class MyApplication extends Application {
    private static MyApplication myApplication;

    private PrefManager pref;

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

    public static void dbUpdate() {
        MyApplication.getInstance().getPrefManager().resetPreferences();
    }
}
