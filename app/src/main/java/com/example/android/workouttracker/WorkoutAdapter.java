package com.example.android.workouttracker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static com.example.android.workouttracker.Workout.HIGH_CALORIES;
import static com.example.android.workouttracker.Workout.LOW_CALORIES;
import static com.example.android.workouttracker.Workout.MEDIUM_CALORIES;

/**
 * Created by MátéZoltán on 2017. 07. 02..
 */

public class WorkoutAdapter extends ArrayAdapter<Workout> {

    //Public constructor of the WorkoutAdapter class
    public WorkoutAdapter(Context context, List<Workout> workouts){
        super(context, 0, workouts);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Create a ViewHolder instance
        ViewHolder holder;

        //If the convertView is null, create it and set the ViewHolder to it
        if (convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

            holder = new ViewHolder();

            //Get the Views of the ViewHolder
            holder.icon = (ImageView) convertView.findViewById(R.id.workout_icon);
            holder.name = (TextView) convertView.findViewById(R.id.workout_name);
            holder.time = (TextView) convertView.findViewById(R.id.workout_time);
            holder.calories = (TextView) convertView.findViewById(R.id.workout_calories);

            //Set the ViewHolder to the convertView
            convertView.setTag(holder);

        } else {

            //If it already exists, set the ViewHolder to it
            holder = (ViewHolder) convertView.getTag();

        }

        //Get the current Workout object and get its properties
        final Workout currentWorkout = getItem(position);
        Resources res = getContext().getResources();
        String currentName = currentWorkout.getWorkoutName();
        int currentIntensity = currentWorkout.getWorkoutIntensity();
        int currentTime = currentWorkout.getWorkoutTime();

        //Get the intensity icon ID and set it as image resource of the icon ImageView
        holder.icon.setImageResource(getIntensityIconId(currentIntensity));

        //Get the intensity icon background ID and set it as background color of the icon
        GradientDrawable circle = (GradientDrawable) holder.icon.getBackground();
        circle.setColor(ContextCompat.getColor(getContext(), getIntensityBackgroundColorId(currentIntensity)));

        //Set the text of the TextViews
        holder.name.setText(currentName);
        holder.time.setText(currentTime + " " + res.getString(R.string.time_unit));
        holder.calories.setText(getWorkoutCalories(currentIntensity, currentTime) + " " + res.getString(R.string.energy_unit));

        return convertView;

    }

    //Constructor for the ViewHolder
    private static class ViewHolder {
        ImageView icon;
        TextView name;
        TextView time;
        TextView calories;
    }

    /*
     * Get the intensity icon resource ID of the Workout
     * @param intensity: the intensity of the Workout(low - 0, medium - 1, high - 2)
     * @return int: the icon resource ID of the Workout
     */
    private int getIntensityIconId(int intensity){

        int iconId;

        switch (intensity){
            case 0:
                iconId = R.drawable.ic_low;
                break;
            case 1:
                iconId = R.drawable.ic_medium;
                break;
            case 2:
                iconId = R.drawable.ic_high;
                break;
            default:
                iconId = R.drawable.ic_medium;
        }
        return iconId;
    }

    /*
     * Get the calories burnt during the Workout
     * @param intensity: the intensity of the Workout(low - 0, medium - 1, high - 2)
     * @param time: the duration of the Workout
     * @return int: the calories burnt during the Workout
     */
    public static int getWorkoutCalories(int intensity, int time){

        int calorieMultiplier;

        switch (intensity){
            case 0:
                calorieMultiplier = LOW_CALORIES;   //3
                break;
            case 1:
                calorieMultiplier = MEDIUM_CALORIES;    //6
                break;
            case 2:
                calorieMultiplier = HIGH_CALORIES;  //9
                break;
            default:
                calorieMultiplier = MEDIUM_CALORIES;

        }

        return calorieMultiplier * time;

    }

    /*
     * Get the background color ID of the Workout
     * @param intensity: the intensity of the Workout(low - 0, medium - 1, high - 2)
     * @return int: the background ID the Workout (varies according to the intensity level)
     */
    public int getIntensityBackgroundColorId(int intensity){

        int backgroundId;

        switch (intensity){
            case 0:
                backgroundId = R.color.colorPrimary;
                break;
            case 1:
                backgroundId = R.color.colorPrimaryDark;
                break;
            case 2:
                backgroundId = R.color.colorAccent;
                break;
            default:
                backgroundId = R.color.colorPrimaryDark;
        }
        return backgroundId;
    }
}
