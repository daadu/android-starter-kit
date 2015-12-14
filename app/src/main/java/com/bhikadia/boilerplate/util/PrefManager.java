package com.bhikadia.boilerplate.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private String TAG = PrefManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context context;

    // Shared pref name
    final String PREF_NAME = "pref";
    // Shared pref mode
    final int PRIVATE_MODE = 0;

    // All Shared Preferences Keys
    public final static String SHOULD_UPDATE = "key";
    public final static String COMPULSORY_UPDATE = "compulsory_update";
    public  final  static String LAST_UPDATE_SHOW_TIME = "last_update_show_time";

    private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;

    public PrefManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME ,PRIVATE_MODE);
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

    public boolean getShouldUpdate() {
        return pref.getBoolean(SHOULD_UPDATE, false);
    }

    public void setShouldUpdate(boolean shouldUpdate) {
        if (shouldUpdate != getShouldUpdate()) {
            editor.putBoolean(SHOULD_UPDATE, shouldUpdate);
            editor.commit();
        }
    }

    public boolean getCompulsoryUpdate() {
        return pref.getBoolean(COMPULSORY_UPDATE, false);
    }

    public void setCompulsoryUpdate(boolean compulsoryUpdate) {
        if (compulsoryUpdate != getCompulsoryUpdate()) {
            editor.putBoolean(COMPULSORY_UPDATE, compulsoryUpdate);
            editor.commit();
        }
    }

    public Long getLastUpdateShowTime() {
        return pref.getLong(LAST_UPDATE_SHOW_TIME, 0);
    }

    public void setLastUpdateShowTime(Long time) {
        editor.putLong(LAST_UPDATE_SHOW_TIME, time);
        editor.commit();
    }
}
