package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.sub_exercise;
import com.example.myapplication.R;

import java.util.ArrayList;

public class subItemAdapter extends RecyclerView.Adapter<subItemAdapter.SubItemViewHolder> {
    private ArrayList<sub_exercise> subItemList;

    public subItemAdapter(ArrayList<sub_exercise> arrayList) {
        this.subItemList = arrayList;
    }

    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_exercise_set,parent,false);
        return new SubItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull subItemAdapter.SubItemViewHolder holder, int position) {
        sub_exercise subItem = subItemList.get(position);
        holder.et_reps.setText(String.valueOf(subItem.getReps()));
        holder.et_lbs.setText(String.valueOf(subItem.getLbs()));
        holder.et_set.setText(String.valueOf(subItem.getSet()));
    }

    @Override
    public int getItemCount() {
        return subItemList.size();
    }

    public class SubItemViewHolder extends RecyclerView.ViewHolder {
        EditText et_set,et_lbs,et_reps;
        public SubItemViewHolder(@NonNull View itemView) {
            super(itemView);
            et_set = itemView.findViewById(R.id.et_set);
            et_lbs = itemView.findViewById(R.id.et_lbs);
            et_reps = itemView.findViewById(R.id.et_reps);
        }
    }

    public void add(sub_exercise subExercise){
        subItemList.add(subExercise);
        notifyDataSetChanged();
    }
}
