package com.example.nepali_english.mynotes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nepali_english.mynotes.dboperations.MyNotesDatabaseHelper;

public class ForgotPassword_Activity extends AppCompatActivity {

    EditText editTextEmail;
    private String email;

    Button send_code;

    MyNotesDatabaseHelper myNotesDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_);

        //change title
        getSupportActionBar().setTitle("Send Verification Code");

        send_code = findViewById(R.id.send_code);

        send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextEmail = findViewById(R.id.regiesteredEmail);
                email = editTextEmail.getText().toString().trim();

                if (email.length() < 4) {
                    Toast.makeText(getApplicationContext(),"Please Enter your correct Email.",Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    myNotesDatabaseHelper = new MyNotesDatabaseHelper(ForgotPassword_Activity.this);
                    //check if user already exists send the code to the email and redirect to the reset password activity
                    int existsStatus = Integer.parseInt(myNotesDatabaseHelper.checkIfUserExists(email));
                    Log.i("exist ?", "onClick: exists ? " + existsStatus);
                    if (existsStatus > 0) {
                        Toast.makeText(ForgotPassword_Activity.this, "wow ✔ that Email exists. Reset your Password now.", Toast.LENGTH_SHORT).show();
                        //redirect to the  login after successful registration
                        Intent intent = new Intent(ForgotPassword_Activity.this, ResetPasswordActivity.class);
                        intent.putExtra("username", email);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(ForgotPassword_Activity.this, "This Email is not registered 😢.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
    }
}