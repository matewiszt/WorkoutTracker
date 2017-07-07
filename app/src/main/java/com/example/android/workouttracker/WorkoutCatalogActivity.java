package com.example.android.workouttracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.workouttracker.data.WorkoutContract.WorkoutEntry;
import com.example.android.workouttracker.data.WorkoutDBHelper;

import java.util.ArrayList;

public class WorkoutCatalogActivity extends AppCompatActivity {

    //Create global variables
    WorkoutAdapter mAdapter;

    SQLiteOpenHelper mDbHelper;

    Cursor mCursor;

    ListView mListView;

    TextView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_catalog);

        //Get the ListView to populate the catalog
        mListView = (ListView) findViewById(R.id.workout_catalog_list);

        //Get the EmptyView
        mEmptyView = (TextView) findViewById(R.id.empty_view);

        //Create a new instance of the WorkoutDBHelper class
        mDbHelper = new WorkoutDBHelper(this);

        //Create an Adapter from the cursor
        createAdapterFromCursor();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Create a menu
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_add:

                //When tapping on the add icon, start the WorkoutEditActivity
                startActivity(new Intent(this, WorkoutEditActivity.class));
                return true;

            case R.id.action_delete_all:

                //When tapping on the delete all icon, delete all workouts from the list and refresh the adapter
                mDbHelper.getWritableDatabase().delete(WorkoutEntry.TABLE_NAME, null, null);
                createAdapterFromCursor();

                //Notify the user about the delete
                Toast.makeText(this, getString(R.string.all_delete_success), Toast.LENGTH_SHORT).show();
                return true;

            case android.R.id.home:

                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createAdapterFromCursor(){

        //Get all the workouts from the database
        mCursor = Utils.readAllWorkouts(mDbHelper);

        //Create an ArrayList of the cursor data
        ArrayList<Workout> workouts = Utils.extractData(mCursor);

        //Create a new WorkoutAdapter and set it to the ListView
        mAdapter = new WorkoutAdapter(this, workouts);
        mListView.setAdapter(mAdapter);

        //Set the EmptyView
        mListView.setEmptyView(mEmptyView);

        //Set an OnItemClickListener to handle the taps on list elements
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Get the current item and its properties
                Workout currentItem = (Workout) parent.getItemAtPosition(position);
                String name = currentItem.getWorkoutName();
                int time = currentItem.getWorkoutTime();
                int intensity = currentItem.getWorkoutIntensity();

                //Create an Intent to launch the WorkoutEditActivity putting the workout data to the intent
                Intent editIntent = new Intent(getApplicationContext(), WorkoutEditActivity.class);
                editIntent.putExtra("name", name);
                editIntent.putExtra("time", time);
                editIntent.putExtra("intensity", intensity);

                //Launch the WorkoutEditActivity
                startActivity(editIntent);
            }
        });

    }

}
