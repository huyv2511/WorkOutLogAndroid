package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.adapter.dayAdapter;
import com.example.myapplication.dataBaseHelper.DataBaseHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements dayAdapter.OnNoteListener {

    EditText day_et;
    Button add_day_btn, clearBtn;
    RecyclerView day_recyclerView;
    dayAdapter day_adapter;
    ArrayList<String> day_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        day_et = findViewById(R.id.day_name_et);
        add_day_btn = findViewById(R.id.add_day_btn);
        clearBtn = findViewById(R.id.clear_btn);
        day_recyclerView = findViewById(R.id.day_recyclerView);
        day_list = new ArrayList<String>();

        //loading up data
        DataBaseHelper db = new DataBaseHelper(getApplicationContext());
        day_list = db.loadDay();

        day_adapter = new dayAdapter(day_list, getApplicationContext(),this);
        day_recyclerView.setAdapter(day_adapter);
        day_recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean clear_bool = db.clearAll();
                if (clear_bool) {
                    Toast.makeText(MainActivity.this, "Data Cleared", Toast.LENGTH_SHORT).show();
                    day_list.clear();
                    day_adapter.notifyDataSetChanged();
                }
            }
        });

        add_day_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day_str = day_et.getText().toString();

                if (!day_str.isEmpty()) {
                    DataBaseHelper db = new DataBaseHelper(getApplicationContext());
                    boolean add_bool = db.addOne(day_str, day_adapter);
                    if (add_bool) {
                        Toast.makeText(MainActivity.this, "addOne successful!", Toast.LENGTH_SHORT).show();
                        day_list.add(day_str);
                        day_adapter.notifyDataSetChanged();
                    }
                    Toast.makeText(MainActivity.this, day_str, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "It is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemListener(int position) {
        Intent intent = new Intent(this, EditExerciseActivity.class);
        intent.putExtra("day_name",day_list.get(position));
        startActivity(intent);
    }
}


