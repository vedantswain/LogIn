package com.LockscreenEMA.Lockscreen;

import android.app.WallpaperManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.LockscreenEMA.R;
import com.LockscreenEMA.Misc.Utility;

import net.frakbot.glowpadbackport.GlowPadView;

public class LockscreenDepression extends Lockscreen {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lockscreen_depression);
        // Use user desktop wallpaper in lockscreen
        RelativeLayout ll = (RelativeLayout) findViewById(R.id.lockscreen_depression_main);
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
                String type = "Accomplishment";
                if (glowPad.mhandle == 0) {
                    type = "Pleasure";
                }
                Utility.depressionWriteToParse("lockscreen", type, target - 1);
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
                String depression_description;

                if (glowPad.mhandle == 0) {
                    depression_description = Utility.convertScaleValueToAdv(target - 1) + " Pleasure";
                } else {
                    depression_description = Utility.convertScaleValueToAdv(target - 1) + " Accomplishment";
                }
                txt.setText(depression_description);
            }
        });
    }
}
