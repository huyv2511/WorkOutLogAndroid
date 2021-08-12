package com.example.myapplication.dataBaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.adapter.dayAdapter;

import java.util.ArrayList;

public class dataBaseHelper extends SQLiteOpenHelper {

    public static final String DAY_TABLE = "DAY_TABLE";
    public static final String POSITION = "POSITION";
    public static final String DAY_NAME = "DAY_NAME";


    public dataBaseHelper(@Nullable Context context) {
        super(context, "workout_log.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + DAY_TABLE + " (" + POSITION + " INTEGER, " + DAY_NAME + " TEXT) ";
        db.execSQL(createTableStatement);
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
        }
        return true;
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
