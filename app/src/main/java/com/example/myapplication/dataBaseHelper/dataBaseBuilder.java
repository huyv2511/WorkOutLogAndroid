package com.example.myapplication.dataBaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.adapter.subItemAdapter;

public class dataBaseBuilder extends SQLiteOpenHelper {
    public static final String DAY_NAME = "DAY_NAME";
    public static final String EXERCISES = "EXERCISES";
    public static final String ROUND = "round";
    public static final String REP = "REP";
    public static final String LBS = "LBS";

    public dataBaseBuilder(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "exercise_log.db", null, -1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + EXERCISES + " ( " + ROUND +" INTEGER, " + REP + " INTEGER,"+ LBS + " INTEGER, " + DAY_NAME + " TEXT ) ";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addOne(String day_name, int round, int rep, int lbs, subItemAdapter adapter){
        //create a database object
        SQLiteDatabase db = this.getWritableDatabase();
        //cv is like an object holding values
        ContentValues cv = new ContentValues();

        cv.put(DAY_NAME,day_name);
        cv.put(ROUND,round);
        cv.put(REP,rep);
        cv.put(LBS,lbs);

        long insert = db.insert(EXERCISES, null, cv);
        if(insert == -1){
            return false;
        } else{
            adapter.notifyDataSetChanged();
        }
        return true;
    }
}
