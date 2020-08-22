package com.example.nepali_english.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nepali_english.mynotes.custom_adapter.MyCustomNotesAdapter;
import com.example.nepali_english.mynotes.dboperations.MyNotesDatabaseHelper;
import com.example.nepali_english.mynotes.models.Note;

import java.util.ArrayList;
import java.util.List;

public class AllNotesScreenActivity extends AppCompatActivity {

    //create new note, //go to imp notes
    ImageView create_new_note, go_to_imp_notes;

    //database helper
    MyNotesDatabaseHelper myNotesDatabaseHelper;

    //listview
    ListView listView;

    //custom adapter for note list view
    MyCustomNotesAdapter myCustomNotesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notes_screen);

        final String username = getIntent().getStringExtra("username");

        getSupportActionBar().setTitle("All Notes : " + username);

        //handle when user clicks on create_new_note
        create_new_note = findViewById(R.id.create_new_note);
        create_new_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String un = getIntent().getStringExtra("username");

                Intent i = new Intent(getApplicationContext(), CreateNewNoteActivity.class);
                i.putExtra("username", un);
                startActivity(i);
            }
        });

        //handle when user clicks on create_new_note
        go_to_imp_notes = findViewById(R.id.go_to_imp_notes);
        go_to_imp_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AllNotesScreenActivity.this, ImportantNotesActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        //display notes of the logged in user
        listView = findViewById(R.id.listView);
        myNotesDatabaseHelper = new MyNotesDatabaseHelper(AllNotesScreenActivity.this);
        final List<Note> allNotes = myNotesDatabaseHelper.getAllNotes(myNotesDatabaseHelper.getIdFromUsername(username));

        if (allNotes.size() <= 0)
            Toast.makeText(this, "You have no notes , please create note.", Toast.LENGTH_SHORT).show();
        //array adapter
        myCustomNotesAdapter = new MyCustomNotesAdapter(AllNotesScreenActivity.this, (ArrayList<Note>) allNotes);
        listView.setAdapter(myCustomNotesAdapter);

        //handle delete onclick listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //logic  to delete item
                final Note clickedNote = (Note) adapterView.getItemAtPosition(i);

                //alert dialog for deleting your note on tapping
                AlertDialog.Builder deleteNoteAlertDialog = new AlertDialog.Builder(
                        AllNotesScreenActivity.this);

                // Setting Dialog Title
                deleteNoteAlertDialog.setTitle("Delete Note !");

                // Setting Dialog Message
                deleteNoteAlertDialog.setMessage("Do you want to delete this note permanently ? [ can't be undo ]");

                // Setting Icon to Dialog
                //  alertDialog2.setIcon(R.drawable.delete);

                // Setting Positive "Yes" Btn
                deleteNoteAlertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                boolean success = myNotesDatabaseHelper.deleteOneNote(clickedNote);

                                if (!success) {
                                    Toast.makeText(AllNotesScreenActivity.this, "Couldn't be deleted your note. ", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Toast.makeText(AllNotesScreenActivity.this, "Note Deleted Successfully ", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), AllNotesScreenActivity.class);
                                intent.putExtra("username", username);
                                startActivity(intent);
                            }
                        });

                // Setting Negative "NO" Btn
                deleteNoteAlertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                // Showing Alert Dialog
                deleteNoteAlertDialog.show();
            }
        });
    }
}