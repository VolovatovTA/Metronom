package com.example.metronom;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static  final  int DATABASE_VERSION = 2;
    public static  final  String DATABASE_NAME = "List";
    public static  final  String TABLE_TRACKS = "tracks";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_TEMP = "tempo";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_TRACKS + "(" + KEY_ID + " integer primary key," + KEY_NAME + " text," + KEY_TEMP + " text" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_TRACKS);

        onCreate(sqLiteDatabase);
    }
}
