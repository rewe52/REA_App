package com.example.vladislav.rea_attention;

import android.content.SharedPreferences;

/**
 * Created by Vladislav on 17.09.2017.
 */

public class EditSharedPreferences {

    SharedPreferences.Editor editor;

    public void addPreferences(SharedPreferences mSettings, String APP_PREFERENCES, String preferences){
        editor = mSettings.edit();

        // type email to preferences
        editor.putString(APP_PREFERENCES, preferences).apply();
    }

    public void deletePreferences(SharedPreferences mSettings, String APP_PREFERENCES){
        editor = mSettings.edit();

        // type email to preferences
        editor.remove(APP_PREFERENCES).apply();
    }

}
