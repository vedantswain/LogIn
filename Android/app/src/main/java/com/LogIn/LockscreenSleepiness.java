package com.LogIn;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import net.frakbot.glowpadbackport.GlowPadView;

public class LockscreenSleepiness extends Lockscreen {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lockscreen_sleepiness);
        // Use user desktop wallpaper in lockscreen
        RelativeLayout ll = (RelativeLayout) findViewById(R.id.lockscreen_sleepiness_main);
        ll.setBackground(WallpaperManager.getInstance(this).getFastDrawable());

        final GlowPadView glowPad = (GlowPadView) findViewById(R.id.incomingCallWidget);

        glowPad.setOnTriggerListener(new GlowPadView.OnTriggerListener() {
            @Override
            public void onGrabbed(View v, int handle) {
            }

            @Override
            public void onReleased(View v, int handle) {
                TextView txt = (TextView) findViewById(R.id.textView);
                txt.setText("");
            }

            @Override
            public void onTrigger(View v, int target) {
                Utility.sleepinessWriteToParse("lockscreen", target - 2);
                glowPad.reset(true);
                v.setVisibility(View.GONE);
                finish();
            }

            @Override
            public void onGrabbedStateChange(View v, int handle) {
            }

            @Override
            public void onFinishFinalAnimation() {
            }

            @Override
            public void onMovedOnTarget(int target) {
                final TextView txt = (TextView) findViewById(R.id.textView);
                String sleepiness_description = Utility.convertSleepinessValueToDescription(target - 2);
                txt.setText(sleepiness_description);
            }
        });
    }
}
