package com.example.android.workouttracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.workouttracker.data.WorkoutContract.WorkoutEntry;

/**
 * Created by MátéZoltán on 2017. 07. 02..
 */

public class WorkoutDBHelper extends SQLiteOpenHelper {

    //Name and version number of the database
    public static final String DB_NAME = "workouts.db";

    public static final int DB_VERSION = 1;

    //SQL command for delete entries
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + WorkoutEntry.TABLE_NAME;

    //Public constructor for the WorkoutDBHelper class
    public WorkoutDBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create table with the given columns
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + WorkoutEntry.TABLE_NAME + " (" +
                WorkoutEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WorkoutEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                WorkoutEntry.COLUMN_TIME + " INTEGER NOT NULL, " +
                WorkoutEntry.COLUMN_INTENSITY + " INTEGER NOT NULL DEFAULT " +
                WorkoutEntry.INTENSITY_MEDIUM + ");";

        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
