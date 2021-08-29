package com.example.myapplication.dataBaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapplication.Model.exercise;
import com.example.myapplication.Model.sub_exercise;
import com.example.myapplication.Model.template;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String ID = "ID";
    public static final String TEMPLATE_ID = "TEMPLATE_ID";
    private static final String TAG = "DataBaseHelper";

    //For the first table TEMPLATE_TABLE containing all the templates
    public static final String TEMPLATE_TABLE = "TEMPLATE_TABLE";
    public static final String TEMPLATE_NAME = "TEMPLATE_NAME";

    //second table containing the exercises associating with the day name
    public static final String EXERCISES_NAME = "EXERCISES_NAME";

    //third table containing all the details from the exercises
    public static final String EXERCISES_TABLE = "EXERCISE_TABLE";
    public static final String REP = "REP";
    public static final String LBS = "LBS";
    public static final String TEMPLATE_EXERCISE_TABLE = "TEMPLATE_EXERCISE";
    public static final String DATA_BASE_HELPER = "DataBaseHelper";


    public DataBaseHelper(@Nullable Context context) {
        super(context, "workout_log.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TEMPLATE_TABLE + " (" + TEMPLATE_ID + " TEXT, " + TEMPLATE_NAME + " TEXT) ";
        String createTableStatement1 = "CREATE TABLE " + EXERCISES_TABLE + " (" + TEMPLATE_NAME + " TEXT, " + EXERCISES_NAME + " TEXT, " +
                            REP + " INTEGER,"+ LBS + " INTEGER, " +
                            ID +" TEXT, " + TEMPLATE_ID +" TEXT )";
        String createTableStatement2 = "CREATE TABLE " + TEMPLATE_EXERCISE_TABLE + " (" + TEMPLATE_NAME + " TEXT, " + EXERCISES_NAME + " TEXT, " + TEMPLATE_ID + " TEXT)";
        db.execSQL(createTableStatement);
        db.execSQL(createTableStatement1);
        db.execSQL(createTableStatement2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOneExercise(String new_exercise_str, template t){
        //now we add these exercises into the exercises table
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(EXERCISES_NAME,new_exercise_str);
        cv.put(TEMPLATE_NAME,t.getTemplateName());
        cv.put(TEMPLATE_ID,t.getTemplateId());

        long i = db.insert(TEMPLATE_EXERCISE_TABLE,null,cv);
        if(i==-1){
            return false;
        } else{
            return true;
        }
    }

    public boolean addOneSet(sub_exercise e,String template_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TEMPLATE_NAME,e.getDay_name());
        cv.put(EXERCISES_NAME,e.getSub_name());
        cv.put(REP,e.getReps());
        cv.put(LBS,e.getLbs());
        cv.put(ID,e.getId());
        cv.put(TEMPLATE_ID,template_id);
        long l = db.insert(EXERCISES_TABLE,null,cv);
        if(l==-1){
            return false;
        } else{
            return true;
        }
    }

    public boolean addOneTemplate(String newDay_str, String day_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TEMPLATE_ID, day_id);
        cv.put(TEMPLATE_NAME,newDay_str);

        long insert = db.insert(TEMPLATE_TABLE, null, cv);
        if(insert == -1){
            return false;
        } else{
            return true;
        }
    }

    public ArrayList<template> loadTemplate(){
        ArrayList<template> returnList = new ArrayList<template>();
        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "SELECT * FROM "+ TEMPLATE_TABLE ;
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(0);
                String t_name = cursor.getString(1);

                template t = new template(id,t_name);
                returnList.add(t);
            } while(cursor.moveToNext());
        }

        return returnList;
    }

    public ArrayList<exercise> loadExerciseList(template template){
        ArrayList<exercise> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "SELECT count(*) FROM " + TEMPLATE_EXERCISE_TABLE;
        Cursor cursor = db.rawQuery(queryString,null);

        if(cursor.moveToFirst()){
            int counter = cursor.getInt(0);

            if(counter ==0 ){
                Log.d(DATA_BASE_HELPER, "Database empty");
            }
            else{
                Log.d(DATA_BASE_HELPER,"NOT EMPTY!");
                String queryString1 = "SELECT " + EXERCISES_NAME +
                        " FROM " + TEMPLATE_EXERCISE_TABLE + " " +
                        "WHERE " + TEMPLATE_NAME +  " = \"" + template.getTemplateName() + "\"" +
                        " AND " + TEMPLATE_ID + " = " + "\"" + template.getTemplateId() + "\"";

                Cursor cursor1 = db.rawQuery(queryString1,null);
                if(cursor1.moveToFirst()){
                    do{
                        //work on adding exercise and designing your database here
                        // first we have to create sub_exercise object
                        String exercise_name = cursor1.getString(0);
                        ArrayList<sub_exercise> subExerciseArrayList = new ArrayList<>();
                        String queryString2 = "SELECT "+ LBS + ", " + REP + ", " + EXERCISES_NAME + ", " + TEMPLATE_NAME +  ", " +ID + ", " + TEMPLATE_ID
                                + " FROM " + EXERCISES_TABLE
                                + " WHERE " + TEMPLATE_NAME  + " = " + "\"" + template.getTemplateName() + "\""  +
                                "AND " +EXERCISES_NAME  +" = " + "\"" + exercise_name + "\"" ;
                        Cursor cursor2 = db.rawQuery(queryString2,null);
                        if(cursor2.moveToFirst()){
                            do{
                                sub_exercise e = new sub_exercise(cursor2.getInt(0),
                                                cursor2.getInt(1),
                                                cursor2.getString(2),
                                                cursor2.getString(3),
                                                cursor2.getString(4),
                                                cursor2.getString(5));

                                subExerciseArrayList.add(e);
                            } while(cursor2.moveToNext());

                            exercise newE = new exercise(exercise_name,template.getTemplateName(),template.getTemplateId(),subExerciseArrayList);
                            returnList.add(newE);
                        }

                    } while(cursor1.moveToNext());
                }
            }
        }

        return  returnList;
    }

    public void deleteOne(sub_exercise e){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EXERCISES_TABLE, ID + " = " +"\""+ e.getId() + "\"",null);

    }

    public int updateDatabase(sub_exercise e){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TEMPLATE_NAME,e.getDay_name());
        cv.put(EXERCISES_NAME,e.getSub_name());
        cv.put(REP,e.getReps());
        cv.put(LBS,e.getLbs());
        cv.put(ID,e.getId());

        String whereClause = ID  + " = " + "\"" + e.getId() + "\"" ;
        Log.d(TAG,e.getDay_name());
        Log.d(TAG,e.getSub_name());
        Log.d(TAG,e.getId());

        return db.update(EXERCISES_TABLE,cv,whereClause,new String[]{});

    }



    public void deleteExercise(exercise e){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = TEMPLATE_NAME + " = " + "\"" + e.getTemplate_name() + "\"" + " AND " + EXERCISES_NAME + " = " + "\"" + e.getExercise_name() + "\"";
        db.delete(TEMPLATE_EXERCISE_TABLE,whereClause,null);
        db.delete(EXERCISES_TABLE,whereClause,null);

    }

    public void deleteOneTemplate(template t) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = TEMPLATE_ID + " = " + "\"" + t.getTemplateId()+ "\"" ;
        Log.d(TAG,whereClause);
        if(db.delete(TEMPLATE_EXERCISE_TABLE,whereClause,new String[]{}) > 0
                || db.delete(TEMPLATE_TABLE,whereClause,new String[]{}) > 0
                || db.delete(EXERCISES_TABLE,whereClause,new String[]{}) >0){
            Log.d(TAG,"Successfully delete one day");
        } else{
            Log.d(TAG,"Unsuccessfully delete one day");
        }

    }
}

