package com.example.predator.l5t1;

public class Pause extends Part {


    String workoutName;
    int timerTime;

    public Pause(String name, int duration)
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

