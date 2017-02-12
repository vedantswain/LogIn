package com.LockscreenEMA.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.LockscreenEMA.App.MainActivity;
import com.LockscreenEMA.Lockscreen.LockscreenDepression;
import com.LockscreenEMA.Lockscreen.LockscreenMood;
import com.LockscreenEMA.Lockscreen.LockscreenParentEMA;
import com.LockscreenEMA.Lockscreen.LockscreenSleepiness;
import com.LockscreenEMA.Misc.Common;
import com.LockscreenEMA.Misc.Utility;

import static com.LockscreenEMA.Misc.Utility.LogInType;

public class LockScreenReceiver extends BroadcastReceiver  {
    public static boolean wasScreenOn = true;
    public static final String TAG = "LockScreenReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Utility.setKeyguardLock(context);
        if (!Utility.needLockscreen()) return;

        final SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
//        Utility.LogInType = SP.getString("LogInType", "?");
        int  LogInType_Value = Integer.parseInt(SP.getString("pref_key_lockscreen_login", "2"));
        LogInType=Common.getLoginTypefromValue(LogInType_Value);
        Log.d(TAG,LogInType);

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            wasScreenOn=false;
            if (LogInType.equals("Sleepiness")) {
                context.startActivity(new Intent(context, LockscreenSleepiness.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else if (LogInType.equals("Depression")) {
                context.startActivity(new Intent(context, LockscreenDepression.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
            else if (LogInType.equals(Common.LOGIN_TYPE_PARENT_EMA)) {
                context.startActivity(new Intent(context, LockscreenParentEMA.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
            else {
                context.startActivity(new Intent(context, LockscreenMood.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            wasScreenOn=true;
            System.out.println("On");
        }
        else if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            if (LogInType.equals("Sleepiness")) {
                context.startActivity(new Intent(context, LockscreenSleepiness.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else if (LogInType.equals("Depression")) {
                context.startActivity(new Intent(context, LockscreenDepression.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
            else if (LogInType.equals(Common.LOGIN_TYPE_PARENT_EMA)) {
                context.startActivity(new Intent(context, LockscreenParentEMA.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
            else {
                context.startActivity(new Intent(context, LockscreenMood.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
            context.startActivity(new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}