package com.example.metronom;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class Saver extends AppCompatActivity implements View.OnClickListener {
    String TAG = "lifecycle111";
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch accent;
    EditText temp;
    EditText name;
    Button save, cancel;
    DBHelper dbHelper;
    String freq;

    @SuppressLint({"SetTextI18n", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saver);
        Log.d(TAG, "Saver onCreate");

        Intent intent = getIntent();

        freq = intent.getStringExtra("temp");
        boolean isAccentOn = intent.getBooleanExtra("accent", false);

        Log.d(TAG, freq + "");

        name = findViewById(R.id.Name);
        temp = findViewById(R.id.Temp);

        accent = findViewById(R.id.accent);

        save = findViewById(R.id.saveLIST);
        save.setOnClickListener(this);

        cancel = findViewById(R.id.cancel_button);
        cancel.setOnClickListener(this);

        dbHelper = new DBHelper(this);

        temp.setText(freq);
        accent.setChecked(isAccentOn);

    }

    @Override
    public void onClick(View view) {

        String name_of_track = name.getText().toString();
        String temp_of_track = temp.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        if (view.getId() == R.id.saveLIST){
            Log.d(TAG, name.getText().toString());

            contentValues.put(DBHelper.KEY_NAME, name_of_track);
            contentValues.put(DBHelper.KEY_TEMP, temp_of_track);

            database.insert(DBHelper.TABLE_TRACKS, null, contentValues);

            this.finish();
        }
        else if (view.getId() == R.id.cancel_button) {
            @SuppressLint("Recycle") Cursor cursor = database.query(DBHelper.TABLE_TRACKS, null,null, null, null, null,null);

            if (cursor.moveToFirst()){
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                int tempIndex = cursor.getColumnIndex(DBHelper.KEY_TEMP);
                do {
                    Log.d(TAG, "id = " + cursor.getString(idIndex) +
                            ", name = " + cursor.getString(nameIndex) +
                            ", temp = " + cursor.getString((tempIndex)));
                } while (cursor.moveToNext());}

            else {
                Log.d(TAG, "Ничё нет");
                dbHelper.close();
            }
            cursor.close();
            this.finish();
        }
    }
}