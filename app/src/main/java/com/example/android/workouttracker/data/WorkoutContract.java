package com.example.android.workouttracker.data;

import android.provider.BaseColumns;

/**
 * Created by MátéZoltán on 2017. 07. 02..
 */

public final class WorkoutContract {

    /*
     * Public constructor of the WorkoutContract class
     * Empty because no instance of this will be created
     */
    public WorkoutContract(){}

    /* Entry class for the workouts table which contains the constants for the table name,
     * the column names and the intensity option values
     */
    public static final class WorkoutEntry implements BaseColumns {

        public static final String TABLE_NAME = "workouts";

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_TIME = "time";

        public static final String COLUMN_INTENSITY = "intensity";

        public static final int INTENSITY_LOW = 0;

        public static final int INTENSITY_MEDIUM = 1;

        public static final int INTENSITY_HIGH = 2;

    }

}
