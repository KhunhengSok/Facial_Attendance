package com.example.facialattandance.Model;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Fresco library
        Fresco.initialize(this);
    }
}
