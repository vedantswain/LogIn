package com.LogIn.Lockscreen;

import android.app.WallpaperManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.LogIn.Misc.Utility;
import com.LogIn.R;

import net.frakbot.glowpadbackport.GlowPadView;

/**
 * Created by vedantdasswain on 12/02/17.
 */

public class LockscreenMultiQuestion extends Lockscreen{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lockscreen_multi_question);
        // Use user desktop wallpaper in lockscreen
        RelativeLayout ll = (RelativeLayout) findViewById(R.id.lockscreen_multi_question_main);
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
                String type = "Mood";
                if (glowPad.mhandle == 1) {
                    type = "Stress";
                }
                else {
                    type = "Activity";
                }
//                Utility.depressionWriteToParse("lockscreen", type, target - 1);
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
                String question_description;

                if (glowPad.mhandle == 0) {
                    question_description = Utility.convertScaleValueToAdv(target - 1) + " Mood";
                }
                else if (glowPad.mhandle == 1){
                    question_description = Utility.convertScaleValueToAdv(target - 1) + " Stress";
                }
                else {
                    question_description = Utility.convertScaleValueToAdv(target - 1) + " Activity";
                }
                txt.setText(question_description);
            }
        });
    }

}
