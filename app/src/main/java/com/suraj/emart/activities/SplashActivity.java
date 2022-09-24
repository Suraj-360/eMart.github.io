package com.suraj.emart.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.suraj.emart.databinding.ActivitySplashBinding;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding activitySplashBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySplashBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(activitySplashBinding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent startAppIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(startAppIntent);
                finish();
            }
        },3350);
    }
}