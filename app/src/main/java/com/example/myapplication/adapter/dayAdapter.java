package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.template;
import com.example.myapplication.R;
import com.example.myapplication.dataBaseHelper.DataBaseHelper;

import java.util.ArrayList;

public class dayAdapter extends RecyclerView. Adapter<dayAdapter.ViewHolder> {
    ArrayList<template> templateArrayList;
    Context context;
    OnNoteListener onNoteListener;
    DataBaseHelper dataBaseHelper;
    public dayAdapter(ArrayList arrayList, Context context, OnNoteListener onNoteListener) {
        this.templateArrayList = arrayList;
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
        holder.day_item.setText(templateArrayList.get(position).getTemplateName());
    }

    @Override
    public int getItemCount() {
        return templateArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView day_item;
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


