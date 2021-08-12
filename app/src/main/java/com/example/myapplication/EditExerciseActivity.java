package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.Model.exercise;
import com.example.myapplication.Model.sub_exercise;
import com.example.myapplication.adapter.ExerciseAdapter;
import com.example.myapplication.dialog.AddExerciseDialog;

import java.util.ArrayList;

public class EditExerciseActivity extends AppCompatActivity implements AddExerciseDialog.AddExerciseDialogListener {
    EditText note_exercise_et;
    RecyclerView exercise_rv;
    Button addExercise_btn;
    ExerciseAdapter exerciseAdapter;
    ArrayList<exercise> exerciseArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        exercise_rv = findViewById(R.id.rvExercise);
        note_exercise_et = findViewById(R.id.note_edit_exercise_et);
        addExercise_btn = findViewById(R.id.addExercise_btn);

        exerciseArrayList = new ArrayList<exercise>();

        exerciseAdapter = new ExerciseAdapter(exerciseArrayList);
        exercise_rv.setAdapter(exerciseAdapter);
        exercise_rv.setLayoutManager(new LinearLayoutManager(this));

        addExercise_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

    }

    private void openDialog() {
        AddExerciseDialog dialog = new AddExerciseDialog();
        dialog.show(getSupportFragmentManager(),"Add New Exercise");
    }


    @Override
    public void newData(String new_exercise_str) {
        exerciseArrayList.add(new exercise(new_exercise_str, new ArrayList<sub_exercise>()));
        exerciseAdapter.notifyDataSetChanged();
    }
}