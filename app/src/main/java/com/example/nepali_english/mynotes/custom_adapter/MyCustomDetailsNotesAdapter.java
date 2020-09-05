package com.example.nepali_english.mynotes.custom_adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nepali_english.mynotes.R;
import com.example.nepali_english.mynotes.models.Note;

import java.util.ArrayList;

public class MyCustomDetailsNotesAdapter extends BaseAdapter {

    Context context;
    ArrayList<Note> noteList;

    public MyCustomDetailsNotesAdapter(Context context, ArrayList<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
    }

    @Override
    public int getCount() {
        return this.noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

           //inflate our custom listview
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_notes_details_listview, null);

            TextView title_text =  view.findViewById(R.id.note_details_title);
            TextView desc_text =    view.findViewById(R.id.note_details_desc);

            Note note = noteList.get(position);

            title_text.setText(note.getTitle());
            desc_text.setText(note.getDescription());

           return view;
    }
}
