package com.example.task_31c;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

public class questionPage extends AppCompatActivity implements View.OnClickListener{

    //... Initiaize some variables
    TextView totalQuestions, questionHeader, questionText,pageHeader;
    Button optionOne, optionTwo, optionThree, nextQuestions;
    SharedPreferences sharedPreferences;

    ProgressBar progress;
    int score, currentQuestion = 0;
    int totalQuestion = Questions.questions.length;
    String userAnswer = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_page);

        //... Get the user name from shared preference
        sharedPreferences = getSharedPreferences("com.example.task_31c",MODE_PRIVATE);
        String userNameId = sharedPreferences.getString("com.example.task_31c","");

        //... Page header to show "Welcome User" message.
        pageHeader = findViewById(R.id.pageHeader);
        pageHeader.setText(pageHeader.getText() + " " + userNameId);

        //... Progress text, question title and question text
        totalQuestions = findViewById(R.id.progressText);
        questionHeader = findViewById(R.id.questionHeader);
        questionText = findViewById(R.id.questionDetail);

        //... 3 Options for a question and move next button
        optionOne = findViewById(R.id.optionOne);
        optionTwo = findViewById(R.id.optionTwo);
        optionThree = findViewById(R.id.optionThree);
        nextQuestions = findViewById(R.id.nextButton);

        //... Set the click listener for all buttons
        optionOne.setOnClickListener(this);
        optionTwo.setOnClickListener(this);
        optionThree.setOnClickListener(this);
        nextQuestions.setOnClickListener(this);

        //... Progress bar
        progress = findViewById(R.id.progressBar);
        progress.setMax(Questions.questions.length);

        //... Call this method to set question and button texts and progress bar value.
        moveNextQuestion();
        //... Set all buttons to white background by default.
        resetColor();
    }

    @Override
    public void onClick(View v) {

        //... When a button is clicked, check if it is an answer button or move next button
        Button currentButton = (Button) v;
        resetColor();

        // If Move next button is clicked...
        if (currentButton.getId() == nextQuestions.getId())
        {
            //... Check if current question is last question. Move to score page if last question.
            currentQuestion++;
            if (currentQuestion == Questions.questions.length)
            {
                viewFinalScore(v);
            }

            //... Otherwise set next question and update button text.
            else
            {
                pageHeader.setText("");
                moveNextQuestion();
            }
        }

        //... If answer button is clicked, disable rest of the buttons.
        else
        {
            nextQuestions.setEnabled(true);
            //... If correct answer, increment score and set background to green
            if (currentButton.getText() == Questions.correctAnswers[currentQuestion])
            {
                score++;
                currentButton.setBackgroundColor(Color.GREEN);
            }
            //... Otherwise set color to red
            else
            {
                currentButton.setBackgroundColor(Color.RED);
            }

            disableAllButtons(false);
            currentButton.setEnabled(true);
        }
    }

    //... Increment the index and get all the required values for next question.
    public void moveNextQuestion() {
        totalQuestions.setText(currentQuestion+1 + "/" + totalQuestion);
        questionHeader.setText(Questions.questionHeaders[currentQuestion]);
        questionText.setText(Questions.questions[currentQuestion]);
        optionOne.setText(Questions.answers[currentQuestion][0]);
        optionTwo.setText(Questions.answers[currentQuestion][1]);
        optionThree.setText(Questions.answers[currentQuestion][2]);
        if (currentQuestion + 1 == Questions.questions.length)
        {
            nextQuestions.setText("Submit");
        }

        //... Disable buttons and increment progress bar
        disableAllButtons(true);
        progress.setProgress(currentQuestion + 1);
    }

    //... Reset color method for buttons
    public void resetColor()
    {
        optionOne.setBackgroundColor(Color.WHITE);
        optionTwo.setBackgroundColor(Color.WHITE);
        optionThree.setBackgroundColor(Color.WHITE);
        nextQuestions.setBackgroundColor(Color.WHITE);
        nextQuestions.setEnabled(false);
    }

    //... Method to disable buttons
    public void disableAllButtons(Boolean setEnabled)
    {
        optionOne.setEnabled(setEnabled);
        optionTwo.setEnabled(setEnabled);
        optionThree.setEnabled(setEnabled);
    }

    //... Open the final score page after last question is answered.
    public void viewFinalScore(View view)
    {
        Intent intent = getIntent();
        intent = new Intent(this, scorePage.class);
        intent.putExtra("finalScore",score);
        intent.putExtra("totalScore",Questions.questions.length);
        startActivity(intent);
    }
}
