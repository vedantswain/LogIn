package com.LockscreenEMA.Lockscreen;

import android.app.WallpaperManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.LockscreenEMA.Misc.Utility;
import com.LockscreenEMA.R;

import net.frakbot.glowpadbackport.GlowPadView;

public class LockscreenParentEMA extends Lockscreen {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lockscreen_parent_ema);
        // Use user desktop wallpaper in lockscreen
        RelativeLayout ll = (RelativeLayout) findViewById(R.id.lockscreen_parentEMA_main);
        ll.setBackground(WallpaperManager.getInstance(this).getFastDrawable());

        TextView txt = (TextView) ll.findViewById(R.id.textView);
        txt.setText(getResources().getString(R.string.parent_EMA_text));

        final GlowPadView glowPad = (GlowPadView) findViewById(R.id.incomingCallWidget);

        glowPad.setOnTriggerListener(new GlowPadView.OnTriggerListener() {
            @Override
            public void onGrabbed(View v, int handle) {
            }

            @Override
            public void onReleased(View v, int handle) {
                TextView txt = (TextView) findViewById(R.id.textView);
                txt.setText(getResources().getString(R.string.parent_EMA_text));
            }

            @Override
            public void onTrigger(View v, int target) {
//                Utility.parentEMAWriteToParse("lockscreen", target - 2);
                Toast.makeText(getApplicationContext(),"Your answer was recorded. Thank you.",Toast.LENGTH_SHORT).show();
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
                String parentEMA_description = Utility.convertParentEMAValueToDescription(target - 2);
                txt.setText(parentEMA_description);
            }
        });
    }
}
