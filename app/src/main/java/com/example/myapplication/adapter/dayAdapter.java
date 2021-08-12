package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class dayAdapter extends RecyclerView. Adapter<dayAdapter.ViewHolder> {
    ArrayList<String> day_ArrayList;
    Context context;
    OnNoteListener onNoteListener;
    public dayAdapter(ArrayList arrayList, Context context, OnNoteListener onNoteListener) {
        this.day_ArrayList = arrayList;
        this.context = context;
        this.onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_workout_day, parent, false);
        return new ViewHolder(view,onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.day_item.setText(day_ArrayList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return day_ArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button day_item;
        OnNoteListener onNoteListener;
        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            day_item = itemView.findViewById(R.id.item_workout_day);
            this.onNoteListener = onNoteListener;
            day_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNoteListener.onItemListener(getAdapterPosition());
                }
            });
        }


    }
    public interface OnNoteListener{
        public void onItemListener(int position);
    }
}


