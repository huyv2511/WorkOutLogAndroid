package com.example.myapplication.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myapplication.R;

public class AddExerciseDialog extends AppCompatDialogFragment {
    private EditText new_exercise_et;
    private AddExerciseDialogListener addExerciseDialogListener;


    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_ex_dialog,null);
        builder.setView(view)
                .setTitle("Add new exercise")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newExercise = new_exercise_et.getText().toString();
                addExerciseDialogListener.newData(newExercise);
            }
        });
        new_exercise_et= view.findViewById(R.id.new_exercise_dialog_et);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            addExerciseDialogListener = (AddExerciseDialogListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface AddExerciseDialogListener{
        void newData(String new_exercise_str);
    }
}
