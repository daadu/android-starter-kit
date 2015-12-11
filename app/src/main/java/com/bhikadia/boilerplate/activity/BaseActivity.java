package com.bhikadia.boilerplate.activity;

import android.support.v7.app.AppCompatActivity;

import com.bhikadia.boilerplate.app.MyApplication;

/**
 * Created by harsh on 10/12/15.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();

        // Verifying the session
        MyApplication.getInstance().verifySession();
    }
}
