package com.example.metronom;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Objects;

public class MainActivity3 extends AppCompatActivity implements View.OnClickListener {

    String TAG = "lifecycle111";
    DBHelper dbHelper;
    final SQLiteDatabase database = Objects.requireNonNull(dbHelper).getWritableDatabase();
    ListView lvList;
    FloatingActionButton fab;
    boolean isFabAction = false;
    int[] id_for_delete;
    int numer;
    String[] names;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Log.d(TAG, "MainActivity3 onCreate");

        lvList = findViewById(R.id.lvList);
        dbHelper = new DBHelper(this);

        @SuppressLint("Recycle") Cursor cursor = database.query(DBHelper.TABLE_TRACKS, null,null, null, null, null,null);

        cursor.moveToLast();
        numer = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_ID)));

        names = new String[numer];

        if (cursor.moveToFirst()){
            do {
                names[Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_ID))) - 1] = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME));
            } while (cursor.moveToNext());}

        else {
            Log.d(TAG, "Ничё нет");
            dbHelper.close();
        }

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(MainActivity3.this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        lvList.setAdapter(adapter);

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (!isFabAction){
                    lvList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    Log.d(TAG, "itemClick: position = " + position + ", id = " + id);
                    Intent intent = new Intent(MainActivity3.this, MainActivity.class);
                    intent.putExtra("name", names[(int) id]);

                    startActivity(intent);
                }
                else {
                    lvList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    SparseBooleanArray sbArray = lvList.getCheckedItemPositions();
                    id_for_delete = new int[sbArray.size()];

                    for (int i = 0; i < sbArray.size(); i++) {
                        int key = sbArray.keyAt(i);

                        if (sbArray.get(key)){
                            Log.d(TAG, names[key]);
                            id_for_delete[i] = key;
                        }
                    }
                }
            }
        });

        lvList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.d(TAG, "itemSelect: position = " + position + ", id = " + id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab) {
            isFabAction = !isFabAction;
            if (!isFabAction){
                for (int i = 0; i < id_for_delete[numer]; i++) {
                    database.delete("List", "id = " + id_for_delete[i], null);
                }
                lvList.removeAllViews();

                if (cursor.moveToFirst()){

                    do {
                        names[Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_ID))) - 1] = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME));
                    } while (cursor.moveToNext());}

                else {
                    Log.d(TAG, "Ничё нет");
                    dbHelper.close();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
                lvList.setAdapter(adapter);
            }

        }
    }
}