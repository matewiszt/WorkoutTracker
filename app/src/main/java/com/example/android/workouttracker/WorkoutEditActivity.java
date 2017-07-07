package com.example.android.workouttracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.workouttracker.data.WorkoutContract.WorkoutEntry;
import com.example.android.workouttracker.data.WorkoutDBHelper;

public class WorkoutEditActivity extends AppCompatActivity {

    SQLiteOpenHelper mDbHelper;

    //Create global variables for the EditTexts and the Spinner
    EditText mNameEditText;

    EditText mTimeEditText;

    Spinner mIntensitySpinner;

    //Create a global variable for the intensity
    private int mIntensity = WorkoutEntry.INTENSITY_MEDIUM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_edit);

        //Create variables for the Workout data
        String workoutName;
        int workoutTime;
        int workoutIntensity;

        //If the activity is crashed or the device is rotated, load the saved name, time and intensity values
        // and assign them to the variables
        if (savedInstanceState == null) {

            Bundle extras = getIntent().getExtras();

            if (extras == null) {

                workoutName = null;
                workoutTime = 0;
                workoutIntensity = 0;
            } else {

                workoutName = extras.getString("name");
                workoutTime = extras.getInt("time");
                workoutIntensity = extras.getInt("intensity");
            }

        } else {

            workoutName = (String) savedInstanceState.getSerializable("name");
            workoutTime = (int) savedInstanceState.getSerializable("time");
            workoutIntensity = (int) savedInstanceState.getSerializable("intensity");
        }

        //Create an instant of the WorkoutDBHelper
        mDbHelper = new WorkoutDBHelper(this);

        //Get the EditTexts and the Spinner
        mNameEditText = (EditText) findViewById(R.id.name_edit);
        mTimeEditText = (EditText) findViewById(R.id.time_edit);
        mIntensitySpinner = (Spinner) findViewById(R.id.intensity_spinner);

        //Setup the Spinner with the intensity values
        setupSpinner();

        //Set the saved values of the EditTexts and the Spinner
        if (workoutName != null) {
            mNameEditText.setText(workoutName);
            mTimeEditText.setText(String.valueOf(workoutTime));
            mIntensitySpinner.setSelection(workoutIntensity);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Create the menu
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Get the values of the input fields
        String name = mNameEditText.getText().toString();
        int time;

        if (!TextUtils.isEmpty(mTimeEditText.getText())){

            time = Integer.parseInt(mTimeEditText.getText().toString());

        } else {

            time = 0;

        }

        int intensity = mIntensity;

        switch (item.getItemId()) {

            //If tapped on the save icon, insert the values into the database and start the catalog activity
            case R.id.action_save:

                //Before inserting, check if name and time field is not empty (intensity has default value
                // so it cannot be empty)
                if (TextUtils.isEmpty(name) || time == 0) {

                    //Notify the user about the error in a toast message
                    Toast.makeText(this, getString(R.string.missing_error), Toast.LENGTH_SHORT).show();

                } else {

                    //Insert the workout and open the CatalogActivity
                    Utils.insertWorkout(mDbHelper, name, time, intensity);
                    startActivity(new Intent(this, WorkoutCatalogActivity.class));

                    //Notify the user about the success
                    Toast.makeText(this, getString(R.string.save_success), Toast.LENGTH_SHORT).show();

                }

                return true;

            //If tapped on the delete icon, delete the Workout from the database and open the WorkoutCatalogActivity
            case R.id.action_delete:
                Utils.deleteWorkout(mDbHelper, name);
                startActivity(new Intent(this, WorkoutCatalogActivity.class));

                //Notify the user about the success
                Toast.makeText(this, getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
                return true;

            //If tapped on the back icon, navigate up to the parent activity
            case android.R.id.home:
                if (!TextUtils.isEmpty(name) || time != 0){

                    AlertDialog.Builder builder = new AlertDialog.Builder(WorkoutEditActivity.this);

                    builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            NavUtils.navigateUpFromSameTask(WorkoutEditActivity.this);
                        }
                    });
                    builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

                    builder.setMessage(R.string.save_error);

                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {

                    NavUtils.navigateUpFromSameTask(this);

                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Setup the dropdown spinner that allows the user to select the intensity of the workout
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter intensityAdapter = ArrayAdapter.createFromResource(this,
                R.array.intensity_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        intensityAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mIntensitySpinner.setAdapter(intensityAdapter);

        // Set the integer mIntensity to the constant values
        mIntensitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.intensity_high))) {
                        mIntensity = WorkoutEntry.INTENSITY_HIGH; // High intensity
                    } else if (selection.equals(getString(R.string.intensity_medium))) {
                        mIntensity = WorkoutEntry.INTENSITY_MEDIUM; // Medium intensity
                    } else {
                        mIntensity = WorkoutEntry.INTENSITY_LOW; // Low intensity
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mIntensity = WorkoutEntry.INTENSITY_MEDIUM; // Medium intensity as default
            }
        });

    }
}
