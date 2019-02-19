package com.example.predator.l5t1;

public class Workout extends Part {

    String workoutName;
    int timerTime;

    public Workout(String name, int duration)
    {
        this.workoutName = name;
        this.timerTime = duration;
    }

    @Override
    public String getName() {
        return workoutName;
    }

    @Override
    public int getTime() {
        return timerTime;
    }
}
