package com.example.nepali_english.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nepali_english.mynotes.custom_adapter.MyCustomNotesAdapter;
import com.example.nepali_english.mynotes.dboperations.MyNotesDatabaseHelper;
import com.example.nepali_english.mynotes.models.Note;

import java.util.ArrayList;
import java.util.List;

public class ImportantNotesActivity extends AppCompatActivity {

    ImageView go_to_all;

    //database helper
    MyNotesDatabaseHelper myNotesDatabaseHelper;

    //listview
    ListView listView;

    //custom adapter for note list view
    MyCustomNotesAdapter myCustomNotesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_notes);

        //get username
        final String username = getIntent().getStringExtra("username");

        getSupportActionBar().setTitle("Important Notes : My Notes");

        //handle go to all notes screen
        go_to_all = findViewById(R.id.go_to_all);
        go_to_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AllNotesScreenActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        //display important notes of the logged in user
        listView = findViewById(R.id.listViewImp);
        myNotesDatabaseHelper = new MyNotesDatabaseHelper(ImportantNotesActivity.this);
        List<Note> allImpNotes = myNotesDatabaseHelper.getAllImpNotes(myNotesDatabaseHelper.getIdFromUsername(username));

        if(allImpNotes.size() <= 0)
            Toast.makeText(this, "You don't have any Important Notes.", Toast.LENGTH_SHORT).show();
        //my custom adapter
        myCustomNotesAdapter  = new MyCustomNotesAdapter(ImportantNotesActivity.this, (ArrayList<Note>) allImpNotes);
        listView.setAdapter(myCustomNotesAdapter);

    }
}