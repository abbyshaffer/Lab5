package com.example.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    TextView textView3;
    private NavigationBarView navigationView;
    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //1. Display welcome message
        textView3 = (TextView) findViewById(R.id.textView3);
        Intent intent = getIntent();
        String answer = intent.getStringExtra("message");
        //textView3.setText("Welcome: "+answer+ "!");

        SharedPreferences sharedPreferences = getSharedPreferences(".com.example.lab5", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");
        textView3.setText("Welcome: "+username+ "!"); //added

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);

        //3. initiate notes class variable using readNotes method implemented in DBHelper
        //Intent intent2 = new Intent(this, DBHelper.class);
        DBHelper dbhelper = new DBHelper(sqLiteDatabase);
        notes = dbhelper.readNotes(username);


       // 4. Create an ArrayList<String> object by iterating over notes object.
       ArrayList<String> displayNotes = new ArrayList<>();
       for (Note note: notes){
           displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
       }

       //5.
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.noteListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            Intent intentNote = new Intent(getApplicationContext(), MainActivity3.class);
            intentNote.putExtra("noteid", position);
            startActivity(intentNote);
        }
    });
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.logout) {
            Intent intent = new Intent(this, MainActivity.class);
            SharedPreferences sharedPreferences = getSharedPreferences(".com.example.lab5", Context.MODE_PRIVATE);
            sharedPreferences.edit().remove("username").apply();
            startActivity(intent);
        }
        if (item.getItemId() == R.id.addnote) {
           Intent intent = new Intent(this, MainActivity3.class);
            startActivity(intent);
            System.out.println("addnote");

        }

        /**switch (item.getItemId()) {
            case R.id.logout:
                Intent intent = new Intent(this, MainActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences(".com.example.lab5", Context.MODE_PRIVATE);
                sharedPreferences.edit().remove("username").apply();
                startActivity(intent);
                //setContentView(R.layout.activity_main);
                return true;
            case R.id.addnote:
                Intent intentAddNote = new Intent(this, MainActivity3.class);
                startActivity(intentAddNote);
                return true;
        }**/
        return true;
    }
    }