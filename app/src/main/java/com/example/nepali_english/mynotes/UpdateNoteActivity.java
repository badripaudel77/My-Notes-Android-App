package com.example.nepali_english.mynotes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nepali_english.mynotes.dboperations.MyNotesDatabaseHelper;
import com.example.nepali_english.mynotes.models.Note;

public class UpdateNoteActivity extends AppCompatActivity {
    MyNotesDatabaseHelper myNotesDatabaseHelper;

    //create button
    Button submit_update_note_btn;

    //notes fields
    EditText updateNoteTitle, updateNoteDesc;
    Switch update_mark_as_imp;

    String strTitle, strDescription;
    boolean is_imp = false; // default to false

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        final String username = getIntent().getStringExtra("username");

        //get note details
        final String noteTitle = getIntent().getStringExtra("clickedNoteTitle");
        final String noteDesc = getIntent().getStringExtra("clickedNoteDesc");
        final boolean note_imp = getIntent().getExtras().getBoolean("clickedNoteIsImp");

        final int clickedNoteId = getIntent().getIntExtra("clickedId", -1);

        getSupportActionBar().setTitle("Update Your Note : My Notes");

        //insert note into database
        updateNoteTitle = findViewById(R.id.updateNoteTitle);
        updateNoteDesc = findViewById(R.id.updateNoteDesc);
        update_mark_as_imp = findViewById(R.id.update_mark_as_imp);

        updateNoteTitle.setText(noteTitle);
        updateNoteDesc.setText(noteDesc);
        update_mark_as_imp.setChecked(note_imp);

        //create object of myNotesDatabaseHelper
        myNotesDatabaseHelper = new MyNotesDatabaseHelper(this);

        //add the note
        submit_update_note_btn = findViewById(R.id.submit_update_note_btn);
        submit_update_note_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
               // String username = bundle.getString("username");

                //convert edittext values to string value
                strTitle = updateNoteTitle.getText().toString().trim();
                strDescription = updateNoteDesc.getText().toString().trim();
                is_imp = update_mark_as_imp.isChecked();

                if (strTitle.length() <3) {
                    Toast.makeText(UpdateNoteActivity.this, "Title must at least 3 characters long.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (strDescription.length() < 5) {
                    Toast.makeText(UpdateNoteActivity.this, " Description must be at least 5 characters long.", Toast.LENGTH_SHORT).show();
                    return;
                }

                   try {
                        boolean status = myNotesDatabaseHelper.updateNote(strTitle, strDescription, is_imp, clickedNoteId);

                        if(status) {
                             //redirect to the  same activity after successful registration.
                             Intent i = new Intent(UpdateNoteActivity.this, NoteDetailsScreenActivity.class);
                             Toast.makeText(UpdateNoteActivity.this,"Note Updated Succussfully, create more ..." , Toast.LENGTH_LONG).show();

                             i.putExtra("clickedId", clickedNoteId);
                             i.putExtra("clickedNoteTitle", strTitle);
                             i.putExtra("clickedNoteDesc", strDescription);
                             i.putExtra("clickedNoteIsImp", is_imp);

                             i.putExtra("username", username);
                             Log.i("debug : update to det", "onClick: here ? " + username + " " + clickedNoteId);
                             startActivity(i);
                        }

                        else {
                            Toast.makeText(UpdateNoteActivity.this, "Note couldn't be updated.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                   catch (Exception exception) {
                       exception.getMessage();
                       Toast.makeText(UpdateNoteActivity.this, " Exception occurred while updating note.", Toast.LENGTH_SHORT).show();
                   }
            }
        });
    }
}