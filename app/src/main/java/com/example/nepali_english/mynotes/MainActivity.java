package com.example.nepali_english.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //first time appearing buttons
    Button entry_register_btn;
    Button entry_login_btn;
    TextView take_me_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hide the toolbar
        getSupportActionBar().hide();

        //handle signin btn to move from one activity to another
        entry_login_btn = findViewById(R.id.entry_login_btn);
        entry_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //call the singin activity
                Intent intent = new Intent(MainActivity.this, SignIn_Activity.class);
                startActivity(intent);
            }
        });

        //handle signup btn to move from one activity to another
        entry_register_btn = findViewById(R.id.entry_register_btn);
        entry_register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //call the singin activity
                Intent intent = new Intent(MainActivity.this, Signup_Activity.class);
                startActivity(intent);
            }
        });
    }

}