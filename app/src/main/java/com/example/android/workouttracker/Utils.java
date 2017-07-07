package com.example.android.workouttracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.workouttracker.data.WorkoutContract.WorkoutEntry;

import java.util.ArrayList;

/**
 * Created by MátéZoltán on 2017. 07. 02..
 */

public class Utils {

    //Public constructor of the Utils class - never will be used
    public Utils(){}

    /*
     * Insert a Workout into the database
     * @param dbHelper: the SQLiteOpenHelper of the database
     * @param name: name of the Workout
     * @param time: duration of the Workout
     * @param intensity: intensity level of the Workout
     * @return long: the number of the newly inserted row
     */
    public static long insertWorkout(SQLiteOpenHelper dbHelper, String name, int time, int intensity){

        //Get the writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Put the values to a ContentValues
        ContentValues values = new ContentValues();
        values.put(WorkoutEntry.COLUMN_NAME, name);
        values.put(WorkoutEntry.COLUMN_TIME, time);
        values.put(WorkoutEntry.COLUMN_INTENSITY, intensity);

        //Insert the values into a new row
        long newRowNumber = db.insert(WorkoutEntry.TABLE_NAME, null, values);

        return newRowNumber;

    }

    /*
     * Read all Workouts from the database
     * @param dbHelper: the SQLiteOpenHelper of the database
     * @return Cursor: Cursor of the query results
     */
    public static Cursor readAllWorkouts(SQLiteOpenHelper dbHelper){

        //Get the readable database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //Query the database for all rows and columns
        Cursor cursor = db.query(WorkoutEntry.TABLE_NAME, null, null, null, null, null, null);

        return cursor;
    }

    /*
     * Extract the data from a Cursor
     * @param cursor: a cursor of query results
     * @return ArrayList<Workout>: the ArrayList of the resulting Workout objects
     */
    public static ArrayList<Workout> extractData(Cursor cursor){

        //Create a new ArrayList
        ArrayList<Workout> workouts = new ArrayList<>();

        try {
            //Get the indexes from the cursor
            int nameColumnIndex = cursor.getColumnIndex(WorkoutEntry.COLUMN_NAME);
            int timeColumnIndex = cursor.getColumnIndex(WorkoutEntry.COLUMN_TIME);
            int intensityColumnIndex = cursor.getColumnIndex(WorkoutEntry.COLUMN_INTENSITY);

            //Loop through the cursor rows to get the name, time and result of the workout
            while (cursor.moveToNext()) {

                String currentName = cursor.getString(nameColumnIndex);
                int currentTime = cursor.getInt(timeColumnIndex);
                int currentIntensity = cursor.getInt(intensityColumnIndex);

                //Create a Workout from the data and add it to the ArrayList
                Workout workout = new Workout(currentName, currentTime, currentIntensity);
                workouts.add(workout);
            }

        }

        finally {

            //Close the cursor
            cursor.close();
        }
        return workouts;
    }

    /*
     * Delete a Workout from the database
     * @param dbHelper: the SQLiteOpenHelper of the database
     * @param name: name of the Workout to delete
     */
    public static void deleteWorkout(SQLiteOpenHelper dbHelper, String name){

        //Get the writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Delete the record of the name parameter
        String selection = WorkoutEntry.COLUMN_NAME + " =?";

        String[] selectionArgs = {name};

        db.delete(WorkoutEntry.TABLE_NAME, selection, selectionArgs);
    }

}
