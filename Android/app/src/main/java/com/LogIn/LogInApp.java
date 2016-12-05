package com.LogIn;

import android.app.Application;

import com.parse.Parse;

public class LogInApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(getApplicationContext());
        Parse.initialize(this, "PYRCXauUEwux9LbJtA4mp1KptYj3XRd1W1c7ukZI", "55XOAxJQEnsr7X6uIPzRzCaDDVLQDgLOFVa9DRba");
        Utility.initApp(getBaseContext());
    }
}