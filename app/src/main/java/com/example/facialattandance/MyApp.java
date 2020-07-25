package com.example.facialattandance;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.FirebaseApp;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Fresco library
        Fresco.initialize(this);
        FirebaseApp.initializeApp(this);
    }
}
