package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.exercise;
import com.example.myapplication.Model.sub_exercise;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private ArrayList<exercise> exercises;

    public ExerciseAdapter(ArrayList<exercise> exercises) {
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public ExerciseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_edit_exercise,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseAdapter.ViewHolder holder, int position) {
        holder.tvExName.setText(exercises.get(position).getExercise_name());

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.rvSubItem.getContext(),
                LinearLayoutManager.VERTICAL,
                false);


        subItemAdapter subItemAdapter = new subItemAdapter(exercises.get(position).getSub_exercises());

        holder.rvSubItem.setLayoutManager(layoutManager);
        holder.rvSubItem.setAdapter(subItemAdapter);
        holder.rvSubItem.setRecycledViewPool(viewPool);
        holder.add_set_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub_exercise subExercise = new sub_exercise(-1,-1,-1,holder.tvExName.getText().toString(),exercises.get(position).getExercise_name());
                subItemAdapter.add(subExercise);
            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull  RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull  RecyclerView.ViewHolder viewHolder, int direction) {
                int sub_position = viewHolder.getAdapterPosition();
                if(direction == ItemTouchHelper.LEFT){
                    exercises.get(position).getSub_exercises().remove(sub_position);
                    notifyDataSetChanged();
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(holder.rvSubItem);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvExName;
        RecyclerView rvSubItem;
        Button add_set_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExName = itemView.findViewById(R.id.tvExName);
            rvSubItem = itemView.findViewById(R.id.sub_exercise_rv);
            add_set_btn = itemView.findViewById(R.id.add_set_btn);

        }
    }




}
