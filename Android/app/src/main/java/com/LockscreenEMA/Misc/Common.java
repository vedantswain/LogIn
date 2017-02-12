package com.LockscreenEMA.Misc;

/**
 * Created by vedantdasswain on 09/12/16.
 */

public class Common {
    public static final String LOGIN_TYPE_PARENT_EMA="Parent EMA";
    public static final String LOGIN_TYPE_SLEEPINESS="Sleepiness";
    public static final String LOGIN_TYPE_DEPRESSION="Depression";
    public static final String LOGIN_TYPE_MOOD="Mood";

    public static String getLoginTypefromValue(int value){
        switch (value){
            case 1:
                return Common.LOGIN_TYPE_SLEEPINESS;
            case 2:
                return Common.LOGIN_TYPE_PARENT_EMA;
            case 3:
                return Common.LOGIN_TYPE_MOOD;
            case 4:
                return Common.LOGIN_TYPE_DEPRESSION;
            default:
                return "";
        }
    }
}
