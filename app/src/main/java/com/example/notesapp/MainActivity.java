package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

import static android.widget.AdapterView.*;

public class MainActivity extends AppCompatActivity {

    static SharedPreferences sharedPreferences;
    static ArrayList arrayList = new ArrayList<String>();
    static Context context;
    static ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("com.example.notesapp", MODE_PRIVATE);

        HashSet<String> hashSet = (HashSet<String>) sharedPreferences.getStringSet("notes", null);
        if (hashSet != null) {
            arrayList = new ArrayList(hashSet);
        }

        MainActivity.context = getApplicationContext();
        ListView listView = findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NewNote.class);
                intent.putExtra("Add Note", position);
                Log.i("Position", String.valueOf(position));
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete Note")
                        .setMessage("Are you sure you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                arrayList.remove(position);
                                arrayAdapter.notifyDataSetChanged();

                                HashSet<String> hashSet = new HashSet<String>(arrayList);
                                sharedPreferences.edit().putStringSet("notes", hashSet).apply();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.addNote:
                Intent intent = new Intent(getApplicationContext(), NewNote.class);
                //intent.putExtra("Add Note", item.toString());
                intent.putExtra("Add Note", -1);
                startActivity(intent);
                return true;
            default:
                return false;
        }

    }
}


    /** public static void displayNotes(String notes){
     arrayList.add(notes);
     sharedPreferences.edit().putString("list", arrayList.toString()).apply();

     arrayAdapter = new ArrayAdapter<String>(MainActivity.context, android.R.layout.simple_list_item_1, arrayList);
     listView.setAdapter(arrayAdapter);

     //String arrList = sharedPreferences.getString("list", "");
     //Log.i("Array List", arrList);
     } **/

    /** public void editNotes(){
     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    Intent intent = new Intent(getApplicationContext(), NewNote.class);
    intent.putExtra("Add Note", position);
    startActivity(intent);
    //intent = getIntent();
    //intent.getStringExtra("Add note");
    //startActivity(intent);

    String str = sharedPreferences.getString("new note", "");
    Log.i("STR", str);
    if(!str.equals("")){
    //intent.putExtra("edit note", str);
    //startActivity(intent);
    NewNote.re_editNotes("new note", str);
    }
    }
    });
     }**/
