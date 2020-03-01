package com.example.notesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;

public class NewNote extends AppCompatActivity {

    static EditText editText;
    public static SharedPreferences sharedPreferences;
    static int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes);

        sharedPreferences = this.getSharedPreferences("com.example.notesapp", MODE_PRIVATE);
        editText = findViewById(R.id.multiAutoCompleteTextView);

        Intent intent = getIntent();
        //String check = intent.getStringExtra("Add Note");
        //Toast.makeText(this, check, Toast.LENGTH_SHORT).show();
        noteId = intent.getIntExtra("Add Note", -1);
        Log.i("Incoming Note ID", Integer.toString(noteId));
        if (noteId != -1) {
            editText.setText((CharSequence) MainActivity.arrayList.get(noteId));
        } else {
            MainActivity.arrayList.add("");

            Log.i("Array List size", Integer.toString(MainActivity.arrayList.size()));

            noteId = MainActivity.arrayList.size() - 1;
            Log.i("New Note ID", Integer.toString(noteId));
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.arrayList.set(noteId, String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                //here we are saving the notes in an arraylist into HashSet
                //to easily store the arraylist data in form a set in Shared Preferences
                HashSet<String> hashSet = new HashSet<String>(MainActivity.arrayList);
                sharedPreferences.edit().putStringSet("notes", hashSet).apply();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}

    /**public void onBackPressed() {
     final String editNotes = editText.getText().toString();
     Log.i("EDIT NOTES", editNotes);

     String getNotes = null;

     super.onBackPressed();
     //MainActivity.displayNotes(editNotes);

     if(!editNotes.equals(""))
     {
     sharedPreferences.edit().putString("new note", editNotes).apply();
     getNotes = sharedPreferences.getString("new note", "");
     Log.i("GET NOTES", getNotes);
     MainActivity.displayNotes(getNotes);
     }
     else{
     finish();
     }
     }**/

    /**public static void re_editNotes(String key, String text) {
        String notes;
        notes = sharedPreferences.getString(key, "");
        Log.i("notes returned", notes);

        //editText.setText(text);


    }**/
