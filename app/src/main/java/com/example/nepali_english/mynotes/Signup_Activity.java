package com.example.nepali_english.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nepali_english.mynotes.alert.Alert;
import com.example.nepali_english.mynotes.dboperations.MyNotesDatabaseHelper;

public class Signup_Activity extends AppCompatActivity {
    //signup handler
    Button submit_sign_up_btn;

    //fields of signup
    EditText name, email, password, confirmPassword;
    String strName, strEmail, strPassword, strConfPassword;

    //already have account
    TextView already_acc;

    MyNotesDatabaseHelper myNotesDatabaseHelper;

    //Alert
    Alert alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_);

        //change the title
        getSupportActionBar().setTitle("Sign Up : Keep Notes Secure");

        //redirect to singin activity if user taps already have aacount text
        already_acc = findViewById(R.id.dont_acc);

        already_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the sign in activity
                Intent intent = new Intent(Signup_Activity.this, SignIn_Activity.class);
                startActivity(intent);
            }
        });
        //create object of myNotesDatabaseHelper
        myNotesDatabaseHelper = new MyNotesDatabaseHelper(this);

        //handle signup
        submit_sign_up_btn = findViewById(R.id.submit_sign_up_btn);

        submit_sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = findViewById(R.id.editTextName);
                email = findViewById(R.id.editTextEmail);
                password = findViewById(R.id.editTextTextPassword);
                confirmPassword = findViewById(R.id.editTextconfirm_pw);


                //convert EditText values to string becouse method expects String value
                strName = name.getText().toString().trim();
                strEmail = email.getText().toString().trim();
                strPassword = password.getText().toString().trim();
                strConfPassword = confirmPassword.getText().toString().trim();

                if (strName.length() < 3) {
                    Toast.makeText(Signup_Activity.this, "Name must be at least 3 characters long.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //make sure username is at least 3 char long
                if (strEmail.length() < 5) {
                    Toast.makeText(Signup_Activity.this, "Username or email must be at least 5 characters long.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //make sure username contains both letter and number
                if (!(strEmail.matches(".*[a-zA-Z].*") && strEmail.matches(".*[0-9].*"))) {
                    Toast.makeText(Signup_Activity.this, "Username or email must contain at least one letter and one number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (strPassword.length() < 5) {
                    Toast.makeText(Signup_Activity.this, "Password must contain at least 5 characters.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!(strPassword.matches(".*[a-zA-Z].*") && strPassword.matches(".*[0-9].*"))) {
                    Toast.makeText(Signup_Activity.this, "Password must contain at least one letter and one number.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!(strPassword.equals(strConfPassword))) {
                    Toast.makeText(Signup_Activity.this, "Two Passwords Must Match.", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //check if user already exists if not add to the database.
                    int existsStatus = Integer.parseInt(myNotesDatabaseHelper.checkIfUserExists(strEmail));
                    if (existsStatus > 0) {
                        Toast.makeText(Signup_Activity.this, "User with that Email Already exists.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    AlertDialog.Builder registerAlertDialog = new AlertDialog.Builder(
                            Signup_Activity.this);

                    //initializng  alert dialog
                    alertDialog = new Alert("Confirm Registration!", "Have you remembered your credentials ? [ can't be reset later ]");

                    // Setting Dialog Title
                    registerAlertDialog.setTitle(alertDialog.getAlertTitle());

                    // Setting Dialog Message
                    registerAlertDialog.setMessage(alertDialog.getAlertMessage());

                    // Setting Icon to Dialog
                    registerAlertDialog.setIcon(R.drawable.tick);

                    // Setting Positive "Yes" Btn
                    registerAlertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    // Write your code here to execute after dialog
                                    boolean status = myNotesDatabaseHelper.addUser(strName, strEmail, strPassword);
                                    if (status) {
                                        Toast.makeText(Signup_Activity.this, "Register Succussful, Please Login To Proceed ", Toast.LENGTH_SHORT).show();
                                        //redirect to the  login after successful registration
                                        Intent intent = new Intent(Signup_Activity.this, SignIn_Activity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(Signup_Activity.this, "Registration not succeeded.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            });

                    // Setting Negative "NO" Btn
                    registerAlertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
                                    Toast.makeText(getApplicationContext(),
                                            "Fill Unique Credentials and remember them.", Toast.LENGTH_SHORT)
                                            .show();
                                    dialog.cancel();
                                }
                            });

                    // Showing Alert Dialog
                    registerAlertDialog.show();
                }
            }
        });
    }
}