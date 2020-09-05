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
    EditText resetPassword , resetPasswordConfirm;
    String  password, passwordConfirm;

    Button submit_reset_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_);

        //change title
        getSupportActionBar().setTitle("Reset Password");

        submit_reset_btn = findViewById(R.id.submit_reset_btn);


        myNotesDatabaseHelper = new MyNotesDatabaseHelper(this);

        submit_reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword = findViewById(R.id.resetPassword);
                resetPasswordConfirm = findViewById(R.id.resetPasswordConfirm);

                //convert editText to the String because method expects String value
                password = resetPassword.getText().toString().trim();
                passwordConfirm = resetPasswordConfirm.getText().toString().trim();

                if (password.equals("") && passwordConfirm.equals("")) {
                    Toast.makeText(getApplicationContext(),"Please Enter yourpassword.",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(passwordConfirm)) {
                    Toast.makeText(getApplicationContext(),"Two passwords must match.",Toast.LENGTH_SHORT).show();
                    return;
                }

                //check for the data validation
                 else {
                     //update.
                    int status =Integer.parseInt( myNotesDatabaseHelper.getLoginData(passwordConfirm, password));
                    if (status>0) {
                        Toast.makeText(getApplicationContext(), "Successful reset, you can login now.", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), SignIn_Activity.class);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(ResetPasswordActivity.this, "Couldn't update your password, Try again.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
    }
}