package com.bhikadia.boilerplate.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bhikadia.boilerplate.R;
import com.bhikadia.boilerplate.app.MyApplication;

/**
 * Created by harsh on 10/12/15.
 */
public class BaseActivity extends AppCompatActivity {

    // Verifying the session
    public void verifySession() {
        boolean isLoggedIn = MyApplication.getInstance().getAccountUtil().hasAccount();

        if (!isLoggedIn) {

            MyApplication.getInstance().clearUserData(null);

            Toast.makeText(this, getString(R.string.logout_message), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
