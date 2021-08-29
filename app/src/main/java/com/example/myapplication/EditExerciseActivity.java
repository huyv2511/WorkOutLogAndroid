package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.exercise;
import com.example.myapplication.Model.sub_exercise;
import com.example.myapplication.Model.template;
import com.example.myapplication.adapter.ExerciseAdapter;
import com.example.myapplication.dataBaseHelper.DataBaseHelper;
import com.example.myapplication.dialog.AddExerciseDialog;

import java.util.ArrayList;

public class EditExerciseActivity extends AppCompatActivity implements AddExerciseDialog.AddExerciseDialogListener {
    EditText note_exercise_et;
    TextView template_name_Tv;
    RecyclerView exercise_rv;
    Button addExercise_btn;
    ImageButton homeButton;
    ExerciseAdapter exerciseAdapter;
    private template template;
    ArrayList<exercise> exerciseArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        exercise_rv = findViewById(R.id.rvExercise);
        addExercise_btn = findViewById(R.id.addExercise_btn);
        homeButton = findViewById(R.id.save_btn);
        template_name_Tv = findViewById(R.id.day_name_Tv);

        //we have to create a template object
        String template_id = getIntent().getStringExtra("template_id");
        String template_name = getIntent().getStringExtra("template_name");
        template = new template(template_id,template_name);

        template_name_Tv.setText(template_name.toUpperCase());

        exerciseArrayList = new ArrayList<exercise>();

        //now we are loading the data to our ArrayList
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
        exerciseArrayList = dataBaseHelper.loadExerciseList(template);

        exerciseAdapter = new ExerciseAdapter(this,exerciseArrayList);
        exercise_rv.setAdapter(exerciseAdapter);
        exercise_rv.setLayoutManager(new LinearLayoutManager(this));

        addExercise_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
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
        String template_name = template.getTemplateName();
        String template_id = template.getTemplateId();
        exercise e = new exercise(new_exercise_str,template_name,template_id,new ArrayList<sub_exercise>());

        exerciseArrayList.add(e);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());


        if(dataBaseHelper.addOneExercise(new_exercise_str,template)){
            Toast.makeText(this, "Successfully add new exercise", Toast.LENGTH_SHORT).show();
            exerciseAdapter.notifyDataSetChanged();
        } else{
            Toast.makeText(this, "FAIL!", Toast.LENGTH_SHORT).show();

        }
    }

}