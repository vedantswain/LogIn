package com.LogIn;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Setting extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.setting);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences preference, String value){
        System.out.println("SettingChanged");
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        // LogIn basic setup
        Utility.condition_firstday = SP.getString("pref_key_firstday_condition", "6");
        Utility.LogInType = SP.getString("pref_key_LogIn_type", "Sleepiness");

        Utility.initSettings(getBaseContext());

        // Setup Rate Alert
        AlarmReceiverRating alarm_rating = new AlarmReceiverRating();
        alarm_rating.setRatingAlarm(this);

        Utility.setKeyguardLock(this);
    }
}