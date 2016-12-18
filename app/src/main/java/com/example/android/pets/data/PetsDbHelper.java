package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class PetsDbHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "pets.db";

    public PetsDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_TABLE = "CREATE TABLE "+
                DbContract.PetsEntry.TABLE_NAME +" ("+
                DbContract.PetsEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                DbContract.PetsEntry.COLUMN_PET_NAME +" TEXT NOT NULL, "+
                DbContract.PetsEntry.COLUMN_PET_BREED +" TEXT, "+
                DbContract.PetsEntry.COLUMN_PET_GENDER +" INTEGER NOT NULL, "+
                DbContract.PetsEntry.COLUMN_PET_WEIGHT +" INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
