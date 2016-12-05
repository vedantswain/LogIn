package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.LogIn.Lockscreen.LockscreenDepression;
import com.LogIn.Lockscreen.LockscreenMood;
import com.LogIn.Lockscreen.LockscreenSleepiness;
import com.LogIn.App.MainActivity;
import com.LogIn.Utility;

public class lockScreenReceiver extends BroadcastReceiver  {
    public static boolean wasScreenOn = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        Utility.setKeyguardLock(context);
        if (!Utility.needLockscreen()) return;

        final SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        Utility.LogInType = SP.getString("LogInType", "?");

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            wasScreenOn=false;
            if (Utility.LogInType.equals("Sleepiness")) {
                context.startActivity(new Intent(context, LockscreenSleepiness.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else if (Utility.LogInType.equals("Depression")) {
                context.startActivity(new Intent(context, LockscreenDepression.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else {
                context.startActivity(new Intent(context, LockscreenMood.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            wasScreenOn=true;
            System.out.println("On");
        }
        else if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            if (Utility.LogInType.equals("Sleepiness")) {
                context.startActivity(new Intent(context, LockscreenSleepiness.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else if (Utility.LogInType.equals("Depression")) {
                context.startActivity(new Intent(context, LockscreenDepression.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else {
                context.startActivity(new Intent(context, LockscreenMood.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
            context.startActivity(new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}