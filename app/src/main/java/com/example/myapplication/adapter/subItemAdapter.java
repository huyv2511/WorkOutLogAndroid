package com.example.myapplication.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.sub_exercise;
import com.example.myapplication.R;
import com.example.myapplication.dataBaseHelper.DataBaseHelper;

import java.util.ArrayList;

public class subItemAdapter extends RecyclerView.Adapter<subItemAdapter.SubItemViewHolder> {
    private ArrayList<sub_exercise> subItemList;
    private TextWatcher textWatcher;
    Context context;
    public subItemAdapter(Context context, ArrayList<sub_exercise> arrayList) {
        this.subItemList = arrayList;
        this.context = context;
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
        DataBaseHelper dataBaseHelper = new DataBaseHelper(holder.itemView.getContext());
        holder.et_reps.setText(String.valueOf(subItem.getReps()));
        holder.et_lbs.setText(String.valueOf(subItem.getLbs()));
        holder.et_lbs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String newLbs = holder.et_lbs.getText().toString();
                if(!newLbs.isEmpty()){
                    subItem.setLbs(Integer.parseInt(holder.et_lbs.getText().toString()));
                    dataBaseHelper.updateDatabase(subItem);
                }
            }
        });

        holder.et_reps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String newRep = holder.et_reps.getText().toString();
                if(!newRep.isEmpty()){
                    subItem.setReps(Integer.parseInt(holder.et_reps.getText().toString()));
                    dataBaseHelper.updateDatabase(subItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return subItemList.size();
    }

    public class SubItemViewHolder extends RecyclerView.ViewHolder {
        EditText et_lbs,et_reps;
        public SubItemViewHolder(@NonNull View itemView) {
            super(itemView);
            et_lbs = itemView.findViewById(R.id.et_lbs);
            et_reps = itemView.findViewById(R.id.et_reps);
        }
    }

    public void add(sub_exercise subExercise){
        subItemList.add(subExercise);
        notifyDataSetChanged();
    }
}
