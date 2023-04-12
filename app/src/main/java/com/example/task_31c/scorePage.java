package com.example.task_31c;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class scorePage extends AppCompatActivity {

    //... Variable initialisation
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_page);

        //... Get final score and total score values
        Intent intent = getIntent();
        String finalScore = " " + intent.getIntExtra("finalScore",0);
        String totalScore = " " + intent.getIntExtra("totalScore",0);

        TextView finalMsg = findViewById(R.id.finalMsg);
        //... Set the "Congratulations user" message
        sharedPreferences = getSharedPreferences("com.example.task_31c",MODE_PRIVATE);
        String userNameId = sharedPreferences.getString("com.example.task_31c","");
        finalMsg.setText("Congratulations: " + userNameId + " !");

        //... Set score message
        TextView scoreText = findViewById(R.id.finalScore);
        scoreText.setText("Your score: " + finalScore + "/" + totalScore);

        //... Exit the system on finish.
        Button exitButton = findViewById(R.id.finishButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("com.example.task_31c",MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                finish();
                System.exit(0);
            }
        });

        //... Initialise the first page when a restart is requested.
        Button restart = findViewById(R.id.newQuizButton);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(scorePage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}