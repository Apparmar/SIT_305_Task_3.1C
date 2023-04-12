// 3.1C
//.......... Date: 12-Apr-2023
//.......... Author: Amit Parmar
//.......... Status: Initial Version

package com.example.task_31c;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //... Define some class level variables.
    EditText userNameId;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //... Set the default user name from shared preferences. Empty if was not set.
        userNameId = findViewById(R.id.userNameTextView);
        sharedPreferences = getSharedPreferences("com.example.task_31c",MODE_PRIVATE);
        userNameId.setText(sharedPreferences.getString("com.example.task_31c",""));
    }

    public void startQuiz(View view)
    {
        //... When button is clicked, set the shared preference...
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("com.example.task_31c", userNameId.getText().toString());
        editor.apply();

        //... And then move to the next page for questions.
        Intent intent = getIntent();
        intent = new Intent(this, questionPage.class);
        startActivity(intent);
    }

}