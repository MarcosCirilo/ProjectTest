package com.meseems.mereach.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Asus on 24/08/2016.
 */
public class DatabaseController {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private Context mContext;

    public DatabaseController(Context context){
        mContext = context;
        databaseHelper = new DatabaseHelper(context, DatabaseStructure.DB_NAME, DatabaseStructure.DB_VERSION);
        addTables();
    }

    private void addTables() {
        databaseHelper.addTable(DatabaseStructure.CREATE_SERVER);
    }

    public void open(){
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        database.close();
    }

    public void insertServer(String url) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseStructure.SERVER_URL, url);;


        database.insert(DatabaseStructure.TABLE_SERVER, null, contentValue);
    }

    public Cursor getAllServers(){
        String[] columns = new String[] {DatabaseStructure.ID, DatabaseStructure.SERVER_URL};

        Cursor cursor = database.query(DatabaseStructure.TABLE_SERVER, columns, null,
                null, null, null, null);
        return cursor;
    }

    public boolean deleteServer(String url){
        return database.delete(DatabaseStructure.TABLE_SERVER, DatabaseStructure.SERVER_URL + " like " + "\'"+url+"\'", null) > 0;
    }

}
