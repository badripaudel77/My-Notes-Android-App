package com.example.nepali_english.mynotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nepali_english.mynotes.dboperations.MyNotesDatabaseHelper;

public class ResetPasswordActivity extends AppCompatActivity {

    MyNotesDatabaseHelper myNotesDatabaseHelper;

    //fields of signin
    EditText resetPassword , resetPasswordConfirm,verificationCode;
    String  password, passwordConfirm;
    int token;

    Button submit_reset_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_);

        //get passed data
        final String username = getIntent().getStringExtra("username");

        //change title
        getSupportActionBar().setTitle("Reset Password");

        submit_reset_btn = findViewById(R.id.submit_reset_btn);


        myNotesDatabaseHelper = new MyNotesDatabaseHelper(this);

        submit_reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verificationCode = findViewById(R.id.verificationCode);
                resetPassword = findViewById(R.id.resetPassword);
                resetPasswordConfirm = findViewById(R.id.resetPasswordConfirm);

                //convert editText to the String because method expects String value
                password = resetPassword.getText().toString().trim();
                passwordConfirm = resetPasswordConfirm.getText().toString().trim();
                token = Integer.valueOf(verificationCode.getText().toString().trim());

                if(token <= 0) {
                    Toast.makeText(ResetPasswordActivity.this, "Enter The Valid Received Token.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.equals("") && passwordConfirm.equals("")) {
                    Toast.makeText(getApplicationContext(),"Please Enter yourpassword.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(password.matches(".*[a-zA-Z].*") && passwordConfirm.matches(".*[0-9].*"))) {
                    Toast.makeText(getApplicationContext(), "Password must contain at least one letter and one number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(passwordConfirm)) {
                    Toast.makeText(getApplicationContext(),"Two passwords must match.",Toast.LENGTH_SHORT).show();
                    return;
                }

                //check for the data validation
                 else {
                     //update.
                    boolean status = myNotesDatabaseHelper.resetPassword(username, password, token);
                    if (status) {
                        Toast.makeText(getApplicationContext(), "Successful reset, you can login now.", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), SignIn_Activity.class);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(ResetPasswordActivity.this, "Couldn't update your password. Is your token Correct ?", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
    }
}