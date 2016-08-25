package com.meseems.mereach.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Asus on 24/08/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    ArrayList<String> tables;
    DatabaseHelper(Context context, String name, int version) {
        super(context, name, null, version);
        tables = new ArrayList<String>();
    }

    public void addTable(String tableCreator){
        tables.add(tableCreator);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for(String item : tables){
            Log.d("DataaseHelper", "Adding" + item);
            db.execSQL(item);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
