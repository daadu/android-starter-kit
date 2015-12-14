package com.bhikadia.boilerplate.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bhikadia.boilerplate.R;
import com.bhikadia.boilerplate.api.UpdateApi;
import com.bhikadia.boilerplate.app.AppConfig;
import com.bhikadia.boilerplate.app.MyApplication;
import com.bhikadia.boilerplate.fragment.AppUpdateDialogueFragment;
import com.bhikadia.boilerplate.util.PrefManager;

/**
 * Created by harsh on 10/12/15.
 */
public class BaseActivity extends AppCompatActivity {

    boolean isLoginActivity = false;
    AppUpdateDialogueFragment appUpdateDialogueFragment;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (!isLoginActivity){
            verifyIfLoggedIn();
            checkForUpdate();
        }
    }

    protected void checkForUpdate() {
        UpdateApi updateApi = new UpdateApi();
        updateApi.call();

        showUpdateDialogue();
        MyApplication.getInstance().getPrefManager().setOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals(PrefManager.SHOULD_UPDATE)) {
                    showUpdateDialogue();
                }
            }
        });
    }

    protected void showUpdateDialogue() {
        if (MyApplication.getInstance().getPrefManager().getShouldUpdate() && isCheckUpdateRequestTimeout()){
            appUpdateDialogueFragment = new AppUpdateDialogueFragment();
            appUpdateDialogueFragment.setCancelable(!MyApplication.getInstance().getPrefManager().getCompulsoryUpdate());

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            Fragment prev = getSupportFragmentManager().findFragmentByTag("update_dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            ft.add(appUpdateDialogueFragment, "update_dialog");
            ft.commitAllowingStateLoss();
        }
    }

    // Verifying if user logged out and start MainActivity if not
    public void verifyIfLoggedOut() {
        isLoginActivity = true;

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

    protected boolean isCheckUpdateRequestTimeout() {
        Long lastRequestTime = MyApplication.getInstance().getPrefManager().getLastUpdateShowTime();
        Long currentTime = System.currentTimeMillis() / 1000;

        return currentTime - lastRequestTime > AppConfig.UPDATE_CHECK_TIMEOUT;
    }
}
