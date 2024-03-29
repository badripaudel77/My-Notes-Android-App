package com.example.nepali_english.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nepali_english.mynotes.dboperations.MyNotesDatabaseHelper;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

public class SignIn_Activity extends AppCompatActivity {

    TextView dont_acc;
    TextView forgot_password;

    MyNotesDatabaseHelper myNotesDatabaseHelper;

    //fields of signin
    EditText editTextEmail, editTextTextPassword;
    String email, password;

    Button submit_sign_in_btn;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_);

        //display the add here, banner add
        mAdView = findViewById(R.id.adViewLogin);
        final AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //reload app if failed
        mAdView.setAdListener(new AdListener(){
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mAdView.loadAd(adRequest);
            }
        });

        //change title
        getSupportActionBar().setTitle("Sign In : My Notes");

        submit_sign_in_btn = findViewById(R.id.submit_sign_in_btn);
        //redirect to singin activity if user taps already have aacount text
        dont_acc =  findViewById(R.id.dont_acc);
        dont_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the singin activity
                Intent intent = new Intent(SignIn_Activity.this, Signup_Activity.class);
                startActivity(intent);
            }
        });

        forgot_password =  findViewById(R.id.forgot_password);
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the forgot password activity
                Intent intent = new Intent(SignIn_Activity.this, ForgotPassword_Activity.class);
                startActivity(intent);
            }
        });

        myNotesDatabaseHelper = new MyNotesDatabaseHelper(this);

        submit_sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextEmail = findViewById(R.id.editTextEmail);
                editTextTextPassword = findViewById(R.id.editTextTextPassword);

                //convert editText to the String because method expects String value
                email = editTextEmail.getText().toString().trim().toLowerCase();
                password = editTextTextPassword.getText().toString().trim();

                if (email.equals("") && password.equals("")) {
                    Toast.makeText(getApplicationContext(),"Please Enter your Email and password.",Toast.LENGTH_SHORT).show();
                    return;
                }

                //check for the data validation
                 else {
                    int status =Integer.parseInt( myNotesDatabaseHelper.getLoginData(email, password));
                    if (status>0) {
                        Toast.makeText(getApplicationContext(), "Successful login.", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), AllNotesScreenActivity.class);
                        i.putExtra("username", email);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(SignIn_Activity.this, "Please Check email or Password OR Register First.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
    }
}