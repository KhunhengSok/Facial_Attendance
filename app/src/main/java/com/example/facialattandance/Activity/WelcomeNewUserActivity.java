package com.example.facialattandance.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.facialattandance.R;

public class WelcomeNewUserActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_newbie);
        Button go = (Button) findViewById(R.id.go);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor isStarted = getSharedPreferences("isStarted", MODE_PRIVATE).edit();
                isStarted.putBoolean("isStarted",true);
                isStarted.apply();
                Intent intent = new Intent(WelcomeNewUserActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}
