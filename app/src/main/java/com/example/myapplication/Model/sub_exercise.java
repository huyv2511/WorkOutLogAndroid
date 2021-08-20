package com.example.myapplication.Model;

public class sub_exercise {
    private int set;
    private int lbs;
    private int reps;
    private String sub_name;
    private String day_name;

    public sub_exercise(int set, int lbs, int reps, String sub_name, String day_name) {
        this.set = set;
        this.lbs = lbs;
        this.reps = reps;
        this.sub_name = sub_name;
        this.day_name = day_name;
    }

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

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public String getDay_name() {
        return day_name;
    }

    public void setDay_name(String day_name) {
        this.day_name = day_name;
    }
}
