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
import com.example.myapplication.adapter.dayAdapter;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    //For the first table day_table containing all the templates
    public static final String DAY_TABLE = "DAY_TABLE";
    public static final String POSITION = "POSITION";
    public static final String DAY_NAME = "DAY_NAME";

    //second table containing the exercises associating with the day name
    public static final String EXERCISES_NAME = "EXERCISES_NAME";

    //third table containing all the details from the exercises
    public static final String EXERCISES_TABLE = "EXERCISES";
    public static final String ROUND = "round";
    public static final String REP = "REP";
    public static final String LBS = "LBS";
    public static final String DAY_EXERCISES_TABLE = "DAY_EXERCISES";
    public static final String DATA_BASE_HELPER = "DataBaseHelper";


    public DataBaseHelper(@Nullable Context context) {
        super(context, "workout_log.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + DAY_TABLE + " (" + POSITION + " INTEGER, " + DAY_NAME + " TEXT) ";
        String createTableStatement1 = "CREATE TABLE " + EXERCISES_TABLE + " (" + DAY_NAME + " TEXT, " + EXERCISES_NAME + " TEXT, " +  ROUND +" INTEGER, " + REP + " INTEGER,"+ LBS + " INTEGER)";
        String createTableStatement2 = "CREATE TABLE " + DAY_EXERCISES_TABLE + " (" + DAY_NAME + " TEXT, " + EXERCISES_NAME + " TEXT)";
        db.execSQL(createTableStatement);
        db.execSQL(createTableStatement1);
        db.execSQL(createTableStatement2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean clearAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query_str = "DELETE FROM " + DAY_TABLE;
        Cursor cursor = db.rawQuery(query_str, null);
        if(!cursor.moveToFirst()){
            return true;
        }
        return false;
    }

    public boolean addOne_dayEx_table(String new_exercise_str, String day_name){
        //now we add these exercises into the exercises table
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(EXERCISES_NAME,new_exercise_str);
        cv.put(DAY_NAME,day_name);

        long i = db.insert(DAY_EXERCISES_TABLE,null,cv);
        if(i==-1){
            return false;
        } else{
            return true;
        }
    }

    public boolean addOneToExerciseTable(sub_exercise e) {
        SQLiteDatabase  db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DAY_NAME,e.getDay_name());
        cv.put(EXERCISES_NAME,e.getSub_name());
        cv.put(ROUND,e.getSet());
        cv.put(REP,e.getReps());
        cv.put(LBS,e.getLbs());

        long l = db.insert(EXERCISES_TABLE,null,cv);
        if(l==-1){
            return false;
        } else{
            return true;
        }
    }

    public boolean addOne(String newDay_str, dayAdapter adapter){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(POSITION,999);
        cv.put(DAY_NAME,newDay_str);

        long insert = db.insert(DAY_TABLE, null, cv);
        if(insert == -1){
            return false;
        } else{
            adapter.notifyDataSetChanged();
            return true;
        }

    }

    public ArrayList<exercise> loadExerciseList(String day_name_str){
        ArrayList<exercise> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "SELECT count(*) FROM " + DAY_EXERCISES_TABLE;
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            int counter = cursor.getInt(0);

            if(counter ==0 ){
                Log.d(DATA_BASE_HELPER, "Database empty");
            }
            else{
                Log.d(DATA_BASE_HELPER,"NOT EMPTY!");
                String queryString1 = "SELECT * FROM " + DAY_EXERCISES_TABLE+ " WHERE " + DAY_NAME +  " = \"" + day_name_str + "\"";
                Cursor cursor1 = db.rawQuery(queryString1,null);
                if(cursor1.moveToFirst()){
                    do{
                        //work on adding exercise and designing your database here
                        // first we have to create sub_exercise object
                        ArrayList<sub_exercise> subExerciseArrayList = new ArrayList<>();
//                        String queryString2 = "SELECT " + DAY_NAME + ", " + EXERCISES_NAME + ", " + ROUND + ", " + REP + ", " + LBS
//                                + " FROM " + EXERCISES_TABLE
//                                + " WHERE " + DAY_NAME  + " = " +day_name_str;
                        String queryString2 = "SELECT " + ROUND + ", " + LBS + ", " + REP + ", " + EXERCISES_NAME + ", " + DAY_NAME
                                + " FROM " + EXERCISES_TABLE
                                + " WHERE " + DAY_NAME  + " = " + "\"" + day_name_str + "\"";
                        Cursor cursor2 = db.rawQuery(queryString2,null);
                        if(cursor2.moveToFirst()){
                            do{
                                sub_exercise e = new sub_exercise(cursor2.getInt(0),
                                                cursor2.getInt(1),
                                                cursor2.getInt(2),
                                                cursor2.getString(3),
                                                cursor2.getString(4));
                                subExerciseArrayList.add(e);
                            } while(cursor1.moveToNext());
                        }
                        exercise newE = new exercise(day_name_str,subExerciseArrayList);
                        returnList.add(newE);
                    } while(cursor.moveToNext());
                }
            }
        }

        return  returnList;
    }

    public ArrayList<String> loadDay(){
        ArrayList<String> returnList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();

        String queryString = "SELECT * FROM "+ DAY_TABLE ;
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                returnList.add(cursor.getString(1));

            } while(cursor.moveToNext());
        }

        return returnList;
    }
}
//    java.lang.RuntimeException: Unable to start activity ComponentInfo{com.example.myapplication/com.example.myapplication.EditExerciseActivity}:
//    android.database.sqlite.SQLiteException: no such column: legs (code 1 SQLITE_ERROR): , while compiling: SELECT EXERCISES_NAME FROM DAY_EXERCISES WHERE DAY_NAME = legs
