package com.example.myapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.exercise;
import com.example.myapplication.Model.sub_exercise;
import com.example.myapplication.R;
import com.example.myapplication.dataBaseHelper.DataBaseHelper;

import java.util.ArrayList;
import java.util.UUID;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {
    public static final String EXERCISE_ADAPTER_TAG = "ExerciseAdapterTag";
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private ArrayList<exercise> exercises;
    private Context context;
    private DataBaseHelper dataBaseHelper;
    public ExerciseAdapter(Context context,ArrayList<exercise> exercises) {
        this.exercises = exercises;
        this.context = context;
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
        dataBaseHelper = new DataBaseHelper(context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.rvSubItem.getContext(),
                LinearLayoutManager.VERTICAL,
                false);


        subItemAdapter subItemAdapter = new subItemAdapter(context,exercises.get(position).getSub_exercises());

        holder.rvSubItem.setLayoutManager(layoutManager);
        holder.rvSubItem.setAdapter(subItemAdapter);
        holder.rvSubItem.setRecycledViewPool(viewPool);
        holder.add_set_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = UUID.randomUUID().toString();
                sub_exercise subExercise = new sub_exercise(0,0,holder.tvExName.getText().toString().toLowerCase(),
                        exercises.get(position).getDay_name(), id);
                Log.d(EXERCISE_ADAPTER_TAG,id);
                Log.d(EXERCISE_ADAPTER_TAG,exercises.get(0).getDay_name());
//                Log.d(EXERCISE_ADAPTER_TAG,exercises.get(position).getDay_name());
                if(dataBaseHelper.addOneToExerciseTable(subExercise)){
                    Toast.makeText(v.getContext(), "Successfully added to exercise table", Toast.LENGTH_SHORT).show();
                    subItemAdapter.add(subExercise);
                } else{
                    Toast.makeText(v.getContext(), "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseHelper.deleteExercise(exercises.get(position));
                exercises.remove(position);
                notifyDataSetChanged();
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
                    dataBaseHelper = new DataBaseHelper(context);
                    dataBaseHelper.deleteOne(exercises.get(position).getSub_exercises().get(sub_position));
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
        Button add_set_btn,deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deleteBtn = itemView.findViewById(R.id.deleteExerciseBtn);
            tvExName = itemView.findViewById(R.id.tvExName);
            rvSubItem = itemView.findViewById(R.id.sub_exercise_rv);
            add_set_btn = itemView.findViewById(R.id.add_set_btn);

        }
    }




}
