package com.fournot7.upswing.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.fournot7.upswing.R;

public class PrefManager {

    private String TAG = PrefManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context context;
    // Shared pref mode
    final int PRIVATE_MODE = 0;

    // All Shared Preferences Keys
    private final String KEY = "key";

    private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;

    public PrefManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(
                context.getResources().getString(R.string.app_name),
                PRIVATE_MODE
        );

        editor = pref.edit();
    }

    public void setOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        this.onSharedPreferenceChangeListener = onSharedPreferenceChangeListener;
        pref.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    public void resetPreferences() {
        editor.clear();
        editor.commit();
    }

    public String getKey() {
        return pref.getString(KEY, null);
    }

    public void setKey(String key) {
        editor.putString(KEY, key);
        editor.commit();
    }
}
