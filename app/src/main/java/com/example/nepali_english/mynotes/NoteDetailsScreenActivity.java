package com.example.nepali_english.mynotes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nepali_english.mynotes.alert.Alert;
import com.example.nepali_english.mynotes.custom_adapter.MyCustomDetailsNotesAdapter;
import com.example.nepali_english.mynotes.dboperations.MyNotesDatabaseHelper;
import com.example.nepali_english.mynotes.models.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteDetailsScreenActivity extends AppCompatActivity {

    ImageView go_to_all;

    //database helper
    MyNotesDatabaseHelper myNotesDatabaseHelper;

    //listview
    ListView listView;

    //custom adapter for note details list view
    MyCustomDetailsNotesAdapter myCustomDetailsNotesAdapter;

    List<Note> noteDetails;

    //alert
    Alert alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details_screen);

        final int clickedNoteId = getIntent().getIntExtra("clickedId", -1);

        //get username
        final String username = getIntent().getStringExtra("username");

        getSupportActionBar().setTitle("Details : My Notes");

        //handle go to all notes screen
        go_to_all = findViewById(R.id.go_back);
        go_to_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AllNotesScreenActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        //display important notes of the logged in user
        listView = findViewById(R.id.listViewDetails);
        myNotesDatabaseHelper = new MyNotesDatabaseHelper(NoteDetailsScreenActivity.this);
        List<Note> noteDetails = myNotesDatabaseHelper.getDetails(clickedNoteId);

        //my custom adapter
        myCustomDetailsNotesAdapter  = new MyCustomDetailsNotesAdapter(NoteDetailsScreenActivity.this, (ArrayList<Note>) noteDetails);
        listView.setAdapter(myCustomDetailsNotesAdapter);

        //oclick says wanna update ?
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //alert dialog for deleting your note on tapping
                AlertDialog.Builder deleteNoteAlertDialog = new AlertDialog.Builder(
                        NoteDetailsScreenActivity.this);

                //initializng  alert dialog
                alert = new Alert("Update Note !", "Do you want to update this note?");

                // Setting Dialog Title
                deleteNoteAlertDialog.setTitle(alert.getAlertTitle());

                // Setting Dialog Message
                deleteNoteAlertDialog.setMessage(alert.getAlertMessage());

                // Setting Icon to Dialog
                deleteNoteAlertDialog.setIcon(R.drawable.update);

                // Setting Positive "Yes" Btn
                deleteNoteAlertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                boolean success = myNotesDatabaseHelper.updateOne(clickedNoteId);
                                if (!success) {
                                    Toast.makeText(NoteDetailsScreenActivity.this, "Couldn't be updated your note. ", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Toast.makeText(NoteDetailsScreenActivity.this, "Note Updated Successfully ", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), NoteDetailsScreenActivity.class);
                                intent.putExtra("username", username);
                                intent.putExtra("clickedId", clickedNoteId);
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