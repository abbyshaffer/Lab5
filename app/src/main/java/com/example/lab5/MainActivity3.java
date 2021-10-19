package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity3 extends AppCompatActivity {

    int noteid = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        EditText editNote = (EditText) findViewById(R.id.editNote);
       // String content = editNote.getText().toString();
        //SharedPreferences sharedPreferences = getSharedPreferences(".com.example.lab5", Context.MODE_PRIVATE);
        //sharedPreferences.edit().putString("noteid", content).apply();

        Intent intent = getIntent();
        noteid = intent.getIntExtra("noteid",-1);

        System.out.println("NOTEID:" +noteid);

        //clicked on
        if (noteid != -1) {
            Note note = MainActivity2.notes.get(noteid);
            String noteContent = note.getContent();
            editNote.setText(noteContent);
        }
    }


    public void saveMethod(View view){
       //1
        EditText myNote = (EditText) findViewById(R.id.editNote);
        String content = myNote.getText().toString();

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);

        DBHelper dbhelper = new DBHelper(sqLiteDatabase);

        SharedPreferences sharedPreferences = getSharedPreferences(".com.example.lab5", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");

        String title;
        DateFormat dateFormat= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1){
            title = "NOTE_"+(MainActivity2.notes.size()+1);
            dbhelper.saveNotes(username, title, content, date);
        }else{
            title = "NOTE_"+(noteid+1);
            dbhelper.updateNote(title,date,content,username);
        }
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);

    }

}