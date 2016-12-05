/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.LogIn;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.frakbot.glowpadbackport.GlowPadView;

import java.util.Calendar;

class AdvancedPagerAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return Utility.num_days_experiment_length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o == view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Utility.year_start);
        cal.set(Calendar.MONTH, Utility.month_start);
        cal.set(Calendar.DAY_OF_MONTH, Utility.day_start);
        cal.add(Calendar.DATE, position);

        return cal.get(Calendar.MONTH)+1 + "/" + cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Instantiate the {@link View} which should be displayed at {@code position}. Here we
     * inflate a layout from the apps resources and then change the text view to signify the position.
     */
    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        final View view;
        LayoutInflater inflater = LayoutInflater.from(container.getContext());

        if (Utility.LogInType.equals("Sleepiness")) {
            view = inflater.inflate(R.layout.visualization_scroll_sleepiness, container, false);
            container.addView(view);
            CalendarViewSleepiness cal = (CalendarViewSleepiness) view.findViewById(R.id.CalendarViewSleepiness);
            cal.setPageIndex(position);
        } else if (Utility.LogInType.equals("Depression")) {
            view = inflater.inflate(R.layout.visualization_scroll_depression, container, false);
            container.addView(view);
            CalendarViewDepression cal = (CalendarViewDepression) view.findViewById(R.id.CalendarViewDepression);
            cal.setPageIndex(position);
        } else {
            view = inflater.inflate(R.layout.visualization_scroll_mood, container, false);
            container.addView(view);
            CalendarViewMood cal = (CalendarViewMood) view.findViewById(R.id.CalendarViewMood);
            cal.setPageIndex(position);
        }
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}