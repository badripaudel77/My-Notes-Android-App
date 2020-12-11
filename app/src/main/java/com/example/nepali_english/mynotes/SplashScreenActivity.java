package com.example.nepali_english.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //initialize the mobile ad sdk here at app launch
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        getSupportActionBar().hide();
        //work parallelly
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000); //2.5 seconds
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    //add finish so when press back, this doesn't take to splash screen again.
                    finish();
                }
            }
        };
        thread.start();
    }
}