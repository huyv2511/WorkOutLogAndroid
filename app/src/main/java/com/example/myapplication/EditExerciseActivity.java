package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.exercise;
import com.example.myapplication.Model.sub_exercise;
import com.example.myapplication.adapter.ExerciseAdapter;
import com.example.myapplication.dataBaseHelper.DataBaseHelper;
import com.example.myapplication.dialog.AddExerciseDialog;

import java.util.ArrayList;

public class EditExerciseActivity extends AppCompatActivity implements AddExerciseDialog.AddExerciseDialogListener {
    EditText note_exercise_et;
    TextView day_name_tv;
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
        day_name_tv = findViewById(R.id.day_name_Tv);
        day_name_tv.setText(getIntent().getStringExtra("day_name").toUpperCase());

        exerciseArrayList = new ArrayList<exercise>();

        //now we are loading the data to our ArrayList
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
        exerciseArrayList = dataBaseHelper.loadExerciseList(day_name_tv.getText().toString().toLowerCase());

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
    //we are going to call the loadExercise method and loadSub exercise methods right here
    //in each exercise element in array list, there is an arraylist of sets in that exercises we call it sub_exercise


    private void openDialog() {
        AddExerciseDialog dialog = new AddExerciseDialog();
        dialog.show(getSupportFragmentManager(),"Add New Exercise");
    }


    @Override
    public void newData(String new_exercise_str) {
        //we need to add the day_name. The day_name is the problem
        exerciseArrayList.add(new exercise(new_exercise_str,day_name_tv.getText().toString().toLowerCase(), new ArrayList<sub_exercise>()));
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
        if(dataBaseHelper.addOne_dayEx_table(new_exercise_str,day_name_tv.getText().toString().toLowerCase())){
            Toast.makeText(this, "Successfully add new exercise", Toast.LENGTH_SHORT).show();
            exerciseAdapter.notifyDataSetChanged();
        } else{
            Toast.makeText(this, "FAIL!", Toast.LENGTH_SHORT).show();

        }
    }

}