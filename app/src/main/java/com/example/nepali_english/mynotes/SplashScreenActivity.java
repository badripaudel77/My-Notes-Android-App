package com.example.nepali_english.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();
        //work parallelly
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
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