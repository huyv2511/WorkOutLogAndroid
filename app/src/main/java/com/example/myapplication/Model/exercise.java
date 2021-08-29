package com.example.myapplication.Model;

import java.util.ArrayList;

public class exercise {
    private String exercise_name;
    private String template_name;
    private String template_id;
    private ArrayList<sub_exercise> sub_exercises;


    public exercise(String exercise_name, String template_name, String template_id, ArrayList<sub_exercise> sub_exercises) {
        this.exercise_name = exercise_name;
        this.template_name = template_name;
        this.template_id = template_id;
        this.sub_exercises = sub_exercises;
    }



    public String getExercise_name() {
        return exercise_name;
    }
    public ArrayList<sub_exercise> getSub_exercises() {
        return sub_exercises;
    }

    public void setSub_exercises(ArrayList<sub_exercise> sub_exercises) {
        this.sub_exercises = sub_exercises;
    }
    public void setExercise_name(String exercise_name) {
        this.exercise_name = exercise_name;
    }

    public String getTemplate_name() {
        return template_name;
    }

    public void setTemplate_name(String template_name) {
        this.template_name = template_name;
    }

    public String getTemplate_id() {
        return template_id;
    }

}
