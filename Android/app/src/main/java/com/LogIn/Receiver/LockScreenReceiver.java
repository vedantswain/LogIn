package com.LogIn.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.LogIn.App.MainActivity;
import com.LogIn.Lockscreen.LockscreenDepression;
import com.LogIn.Lockscreen.LockscreenMood;
import com.LogIn.Lockscreen.LockscreenMultiQuestion;
import com.LogIn.Lockscreen.LockscreenParentEMA;
import com.LogIn.Lockscreen.LockscreenSleepiness;
import com.LogIn.Misc.Common;
import com.LogIn.Misc.Utility;

import static com.LogIn.Misc.Utility.LogInType;

public class LockScreenReceiver extends BroadcastReceiver  {
    public static boolean wasScreenOn = true;
    public static final String TAG = "LockScreenReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Utility.setKeyguardLock(context);
        if (!Utility.needLockscreen()) return;

        final SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
//        Utility.LogInType = SP.getString("LogInType", "?");
        int  LogInType_Value = Integer.parseInt(SP.getString("pref_key_lockscreen_login", "5"));
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
            else if (LogInType.equals(Common.LOGIN_TYPE_MULTI_QUESTION)) {
                context.startActivity(new Intent(context, LockscreenMultiQuestion.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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
            else if (LogInType.equals(Common.LOGIN_TYPE_MULTI_QUESTION)) {
                context.startActivity(new Intent(context, LockscreenMultiQuestion.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
            else {
                context.startActivity(new Intent(context, LockscreenMood.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
            context.startActivity(new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}