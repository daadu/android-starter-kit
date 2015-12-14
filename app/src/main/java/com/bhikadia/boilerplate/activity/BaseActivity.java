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

    @Override
    protected void onResume() {
        super.onResume();

        // turn on Sync Util
        if (MyApplication.getInstance().getAccountUtil().hasAccount())
            MyApplication.getInstance().getSyncUtil().setSyncSettings();
    }

    // Verifying if user logged out and start MainActivity if not
    public void verifyIfLoggedOut() {
        boolean isLoggedIn = MyApplication.getInstance().getAccountUtil().hasAccount();

        if (isLoggedIn) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
    }

    // Verifying if user logged in and start LoginActivity if not
    public void verifyIfLoggedIn() {
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
