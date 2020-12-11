package com.example.nepali_english.mynotes;

import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;

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

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_notes);

        //display the add here, banner add
        mAdView = findViewById(R.id.adViewImpNotes);
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

        //add InterstitialAd
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if(mInterstitialAd.isLoaded())
                    mInterstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mInterstitialAd.loadAd(adRequest);
            }
        });

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
            Toast.makeText(this, "You don't have any Important Notes yet, Please create notes first.", Toast.LENGTH_SHORT).show();
        //my custom adapter
        myCustomNotesAdapter  = new MyCustomNotesAdapter(ImportantNotesActivity.this, (ArrayList<Note>) allImpNotes);
        listView.setAdapter(myCustomNotesAdapter);

        //on click lead to new details screen for that note
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Note clickedNote = (Note) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getApplicationContext(), NoteDetailsScreenActivity.class);
                intent.putExtra("clickedId", clickedNote.getId());
                intent.putExtra("clickedNoteTitle", clickedNote.getTitle());
                intent.putExtra("clickedNoteDesc", clickedNote.getDescription());
                intent.putExtra("clickedNoteIsImp", clickedNote.isIs_imp());
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

    }
}