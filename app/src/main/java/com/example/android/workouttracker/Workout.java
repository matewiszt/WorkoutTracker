package com.example.android.workouttracker;

/**
 * Created by MátéZoltán on 2017. 07. 02..
 */

public class Workout {

    //Global variable for the name of the workout
    private String mWorkoutName;

    //Global variable for the time of the workout
    private int mWorkoutTime;

    //Global variable for the intensity of the workout
    private int mWorkoutIntensity;

    //Global constants for the different intensity calory multipliers
    public static int LOW_CALORIES = 3;

    public static int MEDIUM_CALORIES = 6;

    public static int HIGH_CALORIES = 9;

    /*
     * Public constructor for the Workout object
     * @param name: name of the workout
     * @param time: duration of the workout
     * @param intensity: intensity level of the workout
     */
    public Workout (String name, int time, int intensity){

        mWorkoutName = name;
        mWorkoutTime = time;
        mWorkoutIntensity = intensity;

    }

    /*
     * Get the name of the Workout
     * @return String: the name of the workout
     */
    public String getWorkoutName(){

        return mWorkoutName;

    }

    /*
     * Get the duration of the Workout
     * @return int: the time of the workout
     */
    public int getWorkoutTime(){

        return mWorkoutTime;

    }

    /*
     * Get the intensity level of the Workout
     * @return int: the level(low - 0, medium - 1, high - 2) of the workout
     */
    public int getWorkoutIntensity(){

        return mWorkoutIntensity;

    }

}
