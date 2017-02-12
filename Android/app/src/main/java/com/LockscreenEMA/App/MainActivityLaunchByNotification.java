package com.LockscreenEMA.App;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.LockscreenEMA.Misc.Utility;

public class MainActivityLaunchByNotification extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.notificationWriteToParse("NotificationLaunchMainActivity", "");
        Intent alarmIntent = new Intent(this, MainActivity.class);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(alarmIntent);
        finish();
    }
}