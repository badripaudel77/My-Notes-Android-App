package com.example.nepali_english.mynotes.models;

public class Note {

    private int id;
    private String title;
    private String description;
    private boolean is_imp;

    public Note() {
    }

    public Note(int id, String title, String description, boolean is_imp) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.is_imp = is_imp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isIs_imp() {
        return is_imp;
    }


}
