package com.example.myapplication.dataBaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.Model.sub_exercise;
import com.example.myapplication.adapter.subItemAdapter;

public class DataBaseBuilder extends SQLiteOpenHelper {
    public static final String DAY_NAME = "DAY_NAME";
    public static final String EXERCISES = "EXERCISES";
    public static final String ROUND = "round";
    public static final String REP = "REP";
    public static final String LBS = "LBS";
    public static final String SUB_NAME = "SUB_NAME";
    public static final String DAY_EXERCISES = " DAY_EXERCISES ";

    public DataBaseBuilder(@Nullable Context context) {
        super(context, "workout_log.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //day_exercises table is going to contain the name of the day and all the workout name of that day
        //Exercises Table is going to contain all the details inside that day like rep set and lbs

        String createTableStatement = "CREATE TABLE " + EXERCISES + " (" + DAY_NAME + " TEXT, " + SUB_NAME + " TEXT, " +  ROUND +" INTEGER, " + REP + " INTEGER,"+ LBS + " INTEGER)";
        String createTableStatement1 = "CREATE TABLE " + DAY_EXERCISES + " (" + DAY_NAME + " TEXT, " + SUB_NAME + " TEXT)";
        db.execSQL(createTableStatement);
        db.execSQL(createTableStatement1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addOne(sub_exercise e, subItemAdapter adapter){
        //create a database object
        SQLiteDatabase db = this.getWritableDatabase();
        //cv is like an object holding values
        ContentValues cv = new ContentValues();

        cv.put(SUB_NAME, e.getSub_name());
        cv.put(DAY_NAME,e.getDay_name());
        cv.put(ROUND,e.getSet());
        cv.put(REP,e.getReps());
        cv.put(LBS,e.getLbs());

        long insert = db.insert(EXERCISES, null, cv);
        if(insert == -1){
            return false;
        } else{
            adapter.notifyDataSetChanged();
        }
        return true;
    }

    public boolean addOneNewExercise(String day_name, String sub_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DAY_NAME,day_name);
        cv.put(SUB_NAME,sub_name);
        long insert = db.insert(DAY_EXERCISES,null, cv);
        if(insert == -1){
            return false;
        } else{
            return true;
        }

    }

    public boolean deleteOne(sub_exercise e){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + EXERCISES + " WHERE " + DAY_NAME + " = " + e.getDay_name() + " AND " + SUB_NAME  + " = " + e.getSub_name() + " AND " + ROUND + " = " + e.getSet();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            return true;
        } else{
            return false;
        }

    }


}
