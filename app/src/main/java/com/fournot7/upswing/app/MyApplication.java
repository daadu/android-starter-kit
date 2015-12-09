package com.fournot7.upswing.app;

import android.app.Application;

/**
 * Created by harsh on 10/12/15.
 */
public class MyApplication extends Application {
    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
    }

    public MyApplication getInstance() {
        return myApplication;
    }
}
