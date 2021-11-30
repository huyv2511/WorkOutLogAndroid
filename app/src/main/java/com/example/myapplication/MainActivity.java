package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.Model.template;
import com.example.myapplication.adapter.dayAdapter;
import com.example.myapplication.dataBaseHelper.DataBaseHelper;

import java.util.ArrayList;
import java.util.UUID;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity implements dayAdapter.OnNoteListener {

    public static final String MAIN_ACTIVITY = "MainActivity";
    EditText day_et;
    ImageButton add_template_btn;
    RecyclerView day_recyclerView;
    dayAdapter day_adapter;
    ArrayList<template> templateList;
    DataBaseHelper dataBaseHelper;
    //on this activities, we have three functions
    //1. Add new template
    //2. Remove a template
    //3. Start a new template workout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        day_et = findViewById(R.id.day_name_et);
        add_template_btn = findViewById(R.id.add_day_btn);

        day_recyclerView = findViewById(R.id.day_recyclerView);
        templateList = new ArrayList<template>();

        //loading up data
        DataBaseHelper db = new DataBaseHelper(getApplicationContext());
        templateList = db.loadTemplate();

        day_adapter = new dayAdapter(templateList, getApplicationContext(),this);
        day_recyclerView.setAdapter(day_adapter);
        day_recyclerView.setLayoutManager(new GridLayoutManager(this,2));


        add_template_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String templateStr = day_et.getText().toString();

                if (!templateStr.isEmpty()) {
                    DataBaseHelper db = new DataBaseHelper(getApplicationContext());
                    String template_id = UUID.randomUUID().toString(); //create a template_id
                    boolean add_bool = db.addOneTemplate(templateStr,template_id);
                    if (add_bool) {
                        template t = new template(template_id,templateStr);
                        templateList.add(t);
                        day_adapter.notifyDataSetChanged();
                        day_et.setText("");
                    }
                    Toast.makeText(MainActivity.this, templateStr, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "It is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int itemPosition = viewHolder.getAdapterPosition();
                dataBaseHelper = new DataBaseHelper(getApplicationContext());
                switch(direction){
                    case ItemTouchHelper.LEFT:
                        dataBaseHelper.deleteOneTemplate(templateList.get(itemPosition));
                        templateList.remove(itemPosition);
                        day_adapter.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onChildDraw(@NonNull  Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.red))
                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_forever_24)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.green))
                        .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(day_recyclerView);
    }

    @Override
    public void onItemListener(int position) {
        Intent intent = new Intent(this, EditExerciseActivity.class);
        intent.putExtra("template_name",templateList.get(position).getTemplateName());
        intent.putExtra("template_id",templateList.get(position).getTemplateId());
        startActivity(intent);
    }
}


