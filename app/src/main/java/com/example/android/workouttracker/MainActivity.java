package com.example.android.workouttracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.workouttracker.data.WorkoutDBHelper;

import java.util.ArrayList;

import static com.example.android.workouttracker.WorkoutAdapter.getWorkoutCalories;

public class MainActivity extends AppCompatActivity {

    SQLiteOpenHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create variables for the calories sum and calories percent
        int caloriesSum = 0;
        double caloriesPercent;

        //Create an instance of the WorkoutDBHelper class
        mDbHelper = new WorkoutDBHelper(this);

        //Query all workouts of the database and create an ArrayList of them
        Cursor cursor = Utils.readAllWorkouts(mDbHelper);
        ArrayList<Workout> workouts = Utils.extractData(cursor);

        //Sum the calories of the workouts
        for (Workout workout : workouts){
            caloriesSum += getWorkoutCalories(workout.getWorkoutIntensity(), workout.getWorkoutTime());
        }

        //Count the percent of the calories - percentage of 10.000 kcal
        caloriesPercent = caloriesSum/100;

        //Set the opacity of the background
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        mainLayout.getBackground().setAlpha(240);

        //Get the progress circle and set the calories sum to it
        ProgressBar progressCircle = (ProgressBar) findViewById(R.id.progressCircle);
        progressCircle.setProgress(caloriesSum);

        //Get the TextViews and set the calories and the percentage to them
        TextView caloriesTextView = (TextView) findViewById(R.id.progressText);
        TextView percentTextView = (TextView) findViewById(R.id.percentText);
        caloriesTextView.setText(caloriesSum + " " + getResources().getString(R.string.energy_unit));
        percentTextView.setText(caloriesPercent + getResources().getString(R.string.percent_text));

        //Get the Go to the Workouts Button and create an OnClickListener
        Button goToButton = (Button) findViewById(R.id.goto_button);
        goToButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //When tapped on the Button, start the WorkoutCatalogActivity
                startActivity(new Intent(MainActivity.this, WorkoutCatalogActivity.class));
            }
        });


    }
}
