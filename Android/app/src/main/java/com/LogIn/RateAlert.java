/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.LogIn;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class RateAlert extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utility.notificationWriteToParse("RateAlertShow", "");
        setContentView(R.layout.rate_alert);
        final TextView txt = (TextView) findViewById(R.id.textView);
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String description = "On ";
        description += (new DateFormatSymbols().getMonths()[month] + " " + Integer.toString(day));
        description += "\nPlease rate intrusiveness of LogIn\nUnder condition ";
        description += Utility.getCondition();
        txt.setText(description);
        /*
        if (Utility.needLockscreen()) {
            description += "Customized Lockscreen\n";
        } else {
            description += "Original Lockscreen\n";
        }
        if (Utility.needNotification()) {
            if (Utility.needNotificationSound()) {
                description += "Notification with Sound\n";
            } else {
                description += "Notification without Sound\n";
            }
        } else {
            description += "No Notification\n";
        }*/

        final ImageView image_view = (ImageView) findViewById(R.id.imageView);
        if (Utility.getCondition() == '1') {
            image_view.setImageResource(R.drawable.preview_condition_1);
        } else if (Utility.getCondition() == '2') {
            image_view.setImageResource(R.drawable.preview_condition_2);
        } else if (Utility.getCondition() == '3') {
            image_view.setImageResource(R.drawable.preview_condition_3);
        } else if (Utility.getCondition() == '4') {
            image_view.setImageResource(R.drawable.preview_condition_4);
        } else if (Utility.getCondition() == '5') {
            image_view.setImageResource(R.drawable.preview_condition_5);
        } else if (Utility.getCondition() == '6') {
            image_view.setImageResource(R.drawable.preview_condition_6);
        }

        final SeekBar seek_bar = (SeekBar) findViewById(R.id.seekBar);
        seek_bar.setVisibility(View.VISIBLE);
        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                final TextView slider_text = (TextView) findViewById(R.id.slider_text);
                slider_text.setText(Utility.convertScaleValueToAdj(progress + 1) + " Intrusive");
            }
        });

        final Button btn = (Button) findViewById(R.id.button);
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utility.rateWriteToParse(seek_bar.getProgress() + 1);
                txt.setText("Thanks for your rating!\nThis screen will be closed now.");
                btn.setVisibility(View.INVISIBLE);
                seek_bar.setVisibility(View.INVISIBLE);

                // Exit rate alert after 3 seconds
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 3000);
            }
        });

    }
}