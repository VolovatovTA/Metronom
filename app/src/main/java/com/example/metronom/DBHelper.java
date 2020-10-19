package com.example.metronom;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static  final  int DATABASE_VERSION = 5;
    public static  final  String DATABASE_NAME = "List2";
    public static  final  String TABLE_TRACKS = "tracks";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_TEMP = "tempo";
    public static final String KEY_COUNT1 = "count1";
    public static final String KEY_COUNT2 = "count2";
    public static final String KEY_ACCENT = "acc";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_TRACKS + "(" + KEY_ID + " integer primary key,"
                + KEY_NAME + " text," + KEY_TEMP + " integer,"+ KEY_ACCENT + " integer," + KEY_COUNT1 + " integer," + KEY_COUNT2 + " integer" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_TRACKS);

        onCreate(sqLiteDatabase);
    }
}
