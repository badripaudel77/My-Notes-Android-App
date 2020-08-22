package com.example.nepali_english.mynotes.dboperations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nepali_english.mynotes.models.Note;

import java.util.ArrayList;
import java.util.List;

public class MyNotesDatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    //fields for the notes
    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_NOTE_TITLE = "title";
    public static final String COLUMN_NOTE_DESC = "description";
    public static final String COLUMN_NOTE_IS_IMP = "is_imp";
    public static final String COLUMN_NOTE_USER_ID = "userid";


    private static final String DATABASE_NAME = "users.db";

    //i added new table so i must change the version.
    private static final int DATABASE_VERSION = 2;


    // Initialize the database object.
    public MyNotesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // Database creation sql statement for register and login
        String SQL_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_USERS + "(" + COLUMN_ID + " integer primary key autoincrement, " +
                COLUMN_NAME + " text not null, " + COLUMN_EMAIL +
                " text not null, " +
                COLUMN_PASSWORD + " text not null);";

        String SQL_NOTES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTES + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NOTE_TITLE + " TEXT, "
                + COLUMN_NOTE_DESC + " TEXT, "
                + COLUMN_NOTE_IS_IMP + " BOOLEAN, "
                + COLUMN_NOTE_USER_ID + " INTEGER, "
                + " FOREIGN KEY" + " (" + COLUMN_NOTE_USER_ID + ")" + " REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + "));";

        database.execSQL(SQL_USERS_TABLE);
        database.execSQL(SQL_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //if (oldVersion > 1) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
        //}
    }

    //register user
    public boolean addUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(COLUMN_NAME, name);
        c.put(COLUMN_EMAIL, email);
        c.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, c);
        db.close();

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public String getLoginData(String email, String password) {
        SQLiteDatabase sql = this.getReadableDatabase();
        String query = " select count(*) from " + TABLE_USERS + " where email ='" + email + "' and password='" + password + "'";
        Cursor cursor = sql.rawQuery(query, null);
        cursor.moveToFirst();
        String count = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(0)));
        cursor.close();
        sql.close();

        return count;
    }

    //method to check if user with given email already exists
    public String checkIfUserExists(String email) {
        SQLiteDatabase sql = this.getReadableDatabase();
        String query = " select count(*) from " + TABLE_USERS + " where email ='" + email + "'";
        Cursor cursor = sql.rawQuery(query, null);
        cursor.moveToFirst();
        String count = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(0)));
        cursor.close();
        sql.close();

        return count;
    }

    //get the id of the logged in user
    // as username is unique field in users table
    public int getIdFromUsername(String username) {
        String query = "SELECT id FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = '" + username + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int id = -1;
        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex("id"));
        }
        cursor.close();
        db.close();

        return id;
    }

    //add the notes
    //register user
    public boolean addNote(String title, String description, boolean is_imp, int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(COLUMN_NOTE_TITLE, title);
        c.put(COLUMN_NOTE_DESC, description);
        c.put(COLUMN_NOTE_IS_IMP, is_imp);
        c.put(COLUMN_NOTE_USER_ID, userid);

        long result = db.insert(TABLE_NOTES, null, c);
        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //fetch notes based on the userid of logged in user
    public List<Note> getAllNotes(int userid) {

        Note note;
        List<Note> notes = new ArrayList<>();

        String title, description;
        int id;
        boolean is_imp;

        String fetchQuery = "SELECT * FROM " + TABLE_NOTES + " WHERE " + COLUMN_NOTE_USER_ID + " = '" + userid + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(fetchQuery, null);

        //fetch and add it to the arraylist
        if (cursor.moveToFirst()) {
            do {
                //store into variables
                id = cursor.getInt(0);
                title = cursor.getString(1);
                description = cursor.getString(2);
                //because boolean values are stored as integer in sqlite database.
                is_imp = cursor.getInt(3) == 1 ? true : false;

                note = new Note(id, title, description, is_imp);
                notes.add(note);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

    //fetch marked as important notes based on the userid of logged in user
    public List<Note> getAllImpNotes(int userid) {

        Note note;
        List<Note> notes = new ArrayList<>();

        String title, description;
        int id;
        boolean is_imp;

        String fetchQuery = "SELECT * FROM " + TABLE_NOTES + " WHERE " + COLUMN_NOTE_IS_IMP + " =1 AND " + COLUMN_NOTE_USER_ID + " = '" + userid + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(fetchQuery, null);

        //fetch and add it to the arraylist
        if (cursor.moveToFirst()) {
            do {
                //store into variables
                id = cursor.getInt(0);
                title = cursor.getString(1);
                description = cursor.getString(2);
                //because boolean values are stored as integer in sqlite database.
                is_imp = cursor.getInt(3) == 1 ? true : false;

                note = new Note(id, title, description, is_imp);
                notes.add(note);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

    //delete the notes
    public boolean deleteOneNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

       // String deleteQuery = "DELETE FROM " + TABLE_NOTES + " WHERE " + COLUMN_ID + " = " + note.getId();

        int delete = db.delete(TABLE_NOTES, COLUMN_ID + " =? ", new String[]{String.valueOf(note.getId())});

        return delete > 0;

        //Log.i("note id ====", "deleteOneNote: " + note.getId());
//        Cursor cursor = db.rawQuery(deleteQuery, null);
//        if(cursor.moveToFirst()) {
//            return true;
//        }
//        else {
//            return false;
//        }
    }
}