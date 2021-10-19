package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static String usernameKey;

    public void loginUser(View view){
        EditText myTextField1 = (EditText) findViewById(R.id.myTextField1);
        String user = myTextField1.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences(".com.example.lab5", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", user).apply();

        //sharedPreferences.edit().putString("username", "user").apply();
        //String username = sharedPreferences.getString("username","");
        goToActivity2(user);
    }

    public void goToActivity2(String user){
        String s = String.valueOf(user);
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("message",s);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String usernameKey = "username";
        SharedPreferences sharedPreferences = getSharedPreferences(".com.example.lab5", Context.MODE_PRIVATE);

        if (!sharedPreferences.getString(usernameKey, "").equals("")){
            String s = sharedPreferences.getString(usernameKey,"");
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra("message",s);
            startActivity(intent);
        }else{
            setContentView(R.layout.activity_main);
        }

    }

}