# LogIn

Please contact **_xiaoyiz at cs.washington.edu_** if you have any issue.

### Examining Unlock Journaling with Diaries and Reminders for In Situ Self-Report in Health and Wellness
Xiaoyi Zhang, Laura Pina, James Fogarty, CHI 2016

LogIn introduces single-slide unlock journaling gestures appropriate for health and wellness measures. As demonstration, we implement 3 measures.
- Sleepiness: 1-dimensional scale.
- Pleasure/Accomplishment: two 1-dimensional scales.
- Mood: 2-dimensional scale.

# Code Structure
**App: single-slide journaling in app**
- MainActivity
- MainActivityLaunchByNotification
- Settings

**Lockscreen: single-slide journaling on lockscreen**
- Lockscreen: will be extended by different measures as a template class.
- LockscreenSleepiness: extend Lockscreen class to implement 1-dimensional scale for sleepiness.
- LockscreenDepression(Pleasure/Accomplishment)
- LockscreenMood
- LockscreenService: init and setup lockscreen (setup keyguardLock, register lockScreenReceiver)

**Visualization: the journaling result during user study**
- AdvancedPagerAdapter
- CalendarViewDepression: draw one day result of Pleasure/Accomplishment
- CalendarViewMood
- CalendarViewSleepiness
- SlidingTabLayout
- SlidingTabsBasicFragment
- SlidingTabStrip
- Visualization

**Receiver:**
- AlarmReceiverNotification: create notification every 30 mins
- AlarmReceiverRating: launch rating dialog at a specfic time every day
- LockScreenReceiver: **_VERY IMPORTANT_** listen to screen on/off event to launch our customized lockscreen.

**Misc:**
- LogInApp: main app
- RateAlert: pop a dialog to rate subjective experience (we do it at the end of each day to record the intrusiveness)
- Utility: reusable helper functions

# Compatible Devices

This version of code is tested on Nexus 5(Android 6.0.1) and Nexus 6P(Android 7.0) on 12/05/2016.

During our user study, we tested on the following devices:
- Google Nexus 4
- Google Nexus 5
- Samsung Note 2
- Samsung Note 3
- Samsung S3
- Samsung S4
- Samsung S5
- Samsung S6
- One plus one
- Moto G
- Moto X
- HTC one M8

LogIn may have some conflicts with lockscreen of Samsung(or other non-Nexus devices with customized launcher). Please turn Settings->Display->Lockscreen Mode to "None".
