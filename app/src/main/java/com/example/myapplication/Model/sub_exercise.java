package com.example.myapplication.Model;

public class sub_exercise {
    private int set;
    private int lbs;
    private int reps;

    public sub_exercise(int set, int lbs, int reps) {
        this.set = set;

        this.lbs = lbs;
        this.reps = reps;
    }

    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public int getLbs() {
        return lbs;
    }

    public void setLbs(int lbs) {
        this.lbs = lbs;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}
