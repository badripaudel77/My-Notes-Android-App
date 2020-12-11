package com.example.nepali_english.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nepali_english.mynotes.dboperations.MyNotesDatabaseHelper;
import com.example.nepali_english.mynotes.models.Note;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

public class CreateNewNoteActivity extends AppCompatActivity {

    MyNotesDatabaseHelper myNotesDatabaseHelper;

    //go to all notes
    TextView go_to_all_notes;
    
    //create button
    Button create_new_note_btn;

    //notes fields
    EditText title, description;
    Switch mark_as_imp;

    String strTitle, strDescription;
    boolean is_imp = false; // default to false

    //Note classs
    Note noteModel;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_note);

        //display the add here, banner add
        mAdView = findViewById(R.id.adViewCreateNote);
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

        final String username = getIntent().getStringExtra("username");

        getSupportActionBar().setTitle("Create New Note : My Notes");

        //handle go_to_all_notes
        go_to_all_notes = findViewById(R.id.go_to_all_notes);
        go_to_all_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CreateNewNoteActivity.this, AllNotesScreenActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        //insert note into database
        title = findViewById(R.id.editNoteTitle);
        description = findViewById(R.id.editNoteDesc);
        mark_as_imp = findViewById(R.id.mark_imp);

        //create object of myNotesDatabaseHelper
        myNotesDatabaseHelper = new MyNotesDatabaseHelper(this);

        //add the note
        create_new_note_btn = findViewById(R.id.submit_create_new_note_btn);
        create_new_note_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                String username = bundle.getString("username");

                //convert edittext values to string value
                strTitle = title.getText().toString().trim();
                strDescription = description.getText().toString().trim();
                is_imp = mark_as_imp.isChecked();

                if (strTitle.length() <3) {
                    Toast.makeText(CreateNewNoteActivity.this, "Title must at least 3 characters long.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (strDescription.length() < 5) {
                    Toast.makeText(CreateNewNoteActivity.this, " Description must be at least 5 characters long.", Toast.LENGTH_SHORT).show();
                    return;
                }

                   try {
                        noteModel = new Note(-1, strTitle, strDescription, is_imp );
                        boolean status = myNotesDatabaseHelper.addNote(noteModel.getTitle(), noteModel.getDescription(),noteModel.isIs_imp(), myNotesDatabaseHelper.getIdFromUsername(username));

                        if(status) {
                             //redirect to the  same activity after successful registration.
                             Intent i = new Intent(CreateNewNoteActivity.this, AllNotesScreenActivity.class);
                             Toast.makeText(CreateNewNoteActivity.this,"Note created Succussfully, create more ..." , Toast.LENGTH_LONG).show();
                             i.putExtra("username", username);
                             startActivity(i);
                        }

                        else {
                            Toast.makeText(CreateNewNoteActivity.this, "Note couldn't be created.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                   catch (Exception exception) {
                       exception.getMessage();
                       Toast.makeText(CreateNewNoteActivity.this, " Exception occurred while creating note.", Toast.LENGTH_SHORT).show();
                   }
            }
        });
    }
}