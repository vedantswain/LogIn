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

package com.LockscreenEMA.App;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.LockscreenEMA.Lockscreen.LockscreenService;
import com.LockscreenEMA.Misc.Common;
import com.LockscreenEMA.Misc.Utility;
import com.LockscreenEMA.R;
import com.LockscreenEMA.Receiver.AlarmReceiverNotification;
import com.LockscreenEMA.Receiver.AlarmReceiverRating;
import com.LockscreenEMA.Visualization.Visualization;

import net.frakbot.glowpadbackport.GlowPadView;


public class MainActivity extends Activity {
    private boolean grid_triggered = false;
    private int saved_value_negative_positive = 0;
    private int saved_value_low_high = 0;

    private static final String TAG = "MainActivity";

    public void openVisualization() {
        Intent intent = new Intent(this, Visualization.class);
//    intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void openSettings() {
        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.input, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_visualization:
                openVisualization();
                return true;
            case R.id.action_settings:
                openSettings();
                Utility.initSettings(getBaseContext());
                Utility.settingChangedWriteToParse("ClickedSettingIcon");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        Log.v(TAG, "onResume");

        String action = getIntent().getAction();
        // Prevent endless loop by adding a unique action, don't restart if action is present
        if(action == null || !action.equals("Already created")) {
            Log.v(TAG, "Force restart");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        // Remove the unique action so the next time onResume is called it will restart
        else
            getIntent().setAction(null);

        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v(TAG, "onCreate");
        getIntent().setAction("Already created");

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getActionBar().setDisplayShowTitleEnabled(false);
        startService(new Intent(this, LockscreenService.class));

        Utility.initSettings(getBaseContext());

        AlarmReceiverNotification alarm_notification = new AlarmReceiverNotification();
        alarm_notification.setNotificationAlarm(this);

        // Setup Rate Alert
        AlarmReceiverRating alarm_rating = new AlarmReceiverRating();
        alarm_rating.setRatingAlarm(this);

        String LogInType=Utility.LogInType;
        final SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(this);
//        Utility.LogInType = SP.getString("LogInType", "?");

        int  LogInType_Value = Integer.parseInt(SP.getString("pref_key_launcher_login", "4"));
        LogInType=Common.getLoginTypefromValue(LogInType_Value);
        Log.d(TAG,LogInType);

        if (LogInType.equals("Sleepiness")) {
            setContentView(R.layout.input_sleepiness);

            final TextView txt = (TextView) findViewById(R.id.textView);
            txt.setText("Sleepiness");

            final GlowPadView glowPad = (GlowPadView) findViewById(R.id.incomingCallWidget);
            glowPad.setOnTriggerListener(new GlowPadView.OnTriggerListener() {
                @Override
                public void onGrabbed(View v, int handle) {
                }

                @Override
                public void onReleased(View v, int handle) {
                    txt.setText("Sleepiness");
                }

                @Override
                public void onTrigger(View v, int target) {
                    Utility.sleepinessWriteToParse("app", target - 2);
                    glowPad.reset(true);
                    glowPad.setVisibility(View.VISIBLE);
                    if (target - 2 >= 1 && target - 2 <= 7) openVisualization();
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
        else if (LogInType.equals(Common.LOGIN_TYPE_PARENT_EMA)) {
            setContentView(R.layout.input_parent_ema);

            final TextView txt = (TextView) findViewById(R.id.textView);
            txt.setText(getResources().getString(R.string.parent_EMA_text));

            final GlowPadView glowPad = (GlowPadView) findViewById(R.id.incomingCallWidget);
            glowPad.setOnTriggerListener(new GlowPadView.OnTriggerListener() {
                @Override
                public void onGrabbed(View v, int handle) {
                }

                @Override
                public void onReleased(View v, int handle) {
                    txt.setText(getResources().getString(R.string.parent_EMA_text));
                }

                @Override
                public void onTrigger(View v, int target) {
//                    Utility.sleepinessWriteToParse("app", target - 2);
                    Toast.makeText(getApplicationContext(),"Your answer was recorded. Thank you.",Toast.LENGTH_SHORT).show();
                    glowPad.reset(true);
                    glowPad.setVisibility(View.VISIBLE);
//                    if (target - 2 >= 1 && target - 2 <= 7) openVisualization();
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
                    String sleepiness_description = Utility.convertParentEMAValueToDescription(target - 2);
                    txt.setText(sleepiness_description);
                }
            });
        }
        else if (LogInType.equals("Depression")) {
            setContentView(R.layout.input_depression);

            final TextView txt = (TextView) findViewById(R.id.textView);
            txt.setText("Pleasure/Accomplishment");

            final GlowPadView glowPad = (GlowPadView) findViewById(R.id.incomingCallWidget);
            glowPad.setOnTriggerListener(new GlowPadView.OnTriggerListener() {
                @Override
                public void onGrabbed(View v, int handle) {
                }

                @Override
                public void onReleased(View v, int handle) {
                    txt.setText("Pleasure/Accomplishment");
                }

                @Override
                public void onTrigger(View v, int target) {
                    String type = "Accomplishment";
                    if (glowPad.mhandle == 0) {
                        type = "Pleasure";
                    }
                    Utility.depressionWriteToParse("app", type, target - 1);
                    glowPad.reset(true);
                    glowPad.setVisibility(View.VISIBLE);
                    if (target - 1 >= 1 && target - 1 <= 5) openVisualization();
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
        } else {
            setContentView(R.layout.input_mood);

            final TextView txt = (TextView) findViewById(R.id.textView);
            txt.setText("Mood");

            final GlowPadView glowPad = (GlowPadView) findViewById(R.id.incomingCallWidget);
            final ImageView moodGrid = (ImageView) findViewById(R.id.mood_grid);

            grid_triggered = false;

            glowPad.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent e) {
                    if (grid_triggered) {
                        double grid_size = moodGrid.getWidth() / 12;

                        String negative_positive = "Positive";
                        saved_value_negative_positive = Math.abs((int) ((double) (e.getX() - moodGrid.getWidth() / 2) / grid_size)) + 1;
                        if (e.getX() < moodGrid.getWidth() / 2) {
                            negative_positive = "Negative";
                            saved_value_negative_positive = -saved_value_negative_positive;
                        }

                        String low_high = "Low";
                        saved_value_low_high = -(Math.abs((int) ((double) (e.getY() - moodGrid.getHeight() / 2) / grid_size)) + 1);
                        if (e.getY() < moodGrid.getHeight() / 2) {
                            low_high = "High";
                            saved_value_low_high = -saved_value_low_high;
                        }

                        String sleepiness_description =
                                Utility.convertScaleValueToAdj(Math.abs(saved_value_negative_positive)) + " "
                                        + negative_positive + "\n" +
                                        Utility.convertScaleValueToAdj(Math.abs(saved_value_low_high)) + " "
                                        + low_high;
                        TextView txt = (TextView) findViewById(R.id.textView);
                        txt.setText(sleepiness_description);
                    }
                    return false;
                }
            });

            glowPad.setOnTriggerListener(new GlowPadView.OnTriggerListener() {
                @Override
                public void onGrabbed(View v, int handle) {
                }

                @Override
                public void onReleased(View v, int handle) {
                    txt.setText("Mood");
                    if (grid_triggered) {
                        Utility.moodWriteToParse("app", saved_value_negative_positive, saved_value_low_high);
                        moodGrid.setVisibility(View.INVISIBLE);
                        glowPad.reset(true);
                        glowPad.setVisibility(View.VISIBLE);
                        openVisualization();
                        grid_triggered = false;
                    }
                }

                @Override
                public void onTrigger(View v, int target) {
                    if (target == 0) Utility.moodWriteToParse("app", -9999, -9999);
                    glowPad.reset(true);
                    glowPad.setVisibility(View.VISIBLE);
                }

                @Override
                public void onGrabbedStateChange(View v, int handle) {
                }

                @Override
                public void onFinishFinalAnimation() {
                }

                @Override
                public void onMovedOnTarget(int target) {
                    if (target == 1) {
                        glowPad.setVisibility(View.GONE);
                        glowPad.setVibrateEnabled(false);
                        moodGrid.setVisibility(View.VISIBLE);
                        grid_triggered = true;
                    }
                }
            });
        }

    }
}