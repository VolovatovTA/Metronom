package com.example.metronom;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {

    String TAG = "lifecycle111";
    DBHelper dbHelper;
    ListView lvList;
    boolean isSelectionMode = true;
    int count_of_tracks;
    String[] names;
    int[] temp;
    private android.view.Menu menu;
    ArrayList<Track> tracks;
    Cursor cursor;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Log.d(TAG, "MainActivity3 onCreate");

        lvList = findViewById(R.id.lvList);
        dbHelper = new DBHelper(this);

        SQLiteDatabase database = (dbHelper).getWritableDatabase();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cursor = database.query(DBHelper.TABLE_TRACKS, null, null, null, null, null, null);
        // create List
        CreateTrack();


            myAdapter = new MyAdapter(this, tracks);

            lvList.setAdapter(myAdapter);

           /* // Listening
            lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    if (!isSelectionMode) {
                        Log.d(TAG, "itemClick: position = " + position + ", id = " + id);

                        Intent intent = new Intent(MainActivity3.this, MainActivity.class);
                        if (cursor.moveToPosition((int) id)) {
                            intent.putExtra("name", cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)));
                            intent.putExtra("temp", cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_TEMP)));
                            startActivity(intent);
                        }
                    }
                }
            });
        }
        lvList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                isSelectionMode = false;

            }

        });*/

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 1, "Choose");
        menu.add(1, 2, 0, "Delete");
        menu.add(1, 3, 0, "Paste in List");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            isSelectionMode = true;
            lvList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        } else if (item.getItemId() == 2) {
            SQLiteDatabase database = (dbHelper).getWritableDatabase();
            int count = DeleteSomeItems(findViewById(R.id.toolbar), database);
            if (count != 0) {
                myAdapter.notifyDataSetChanged();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        // пункты меню с ID группы = 1 видны, если в CheckBox стоит галка
        menu.setGroupVisible(1, isSelectionMode);


        return super.onPrepareOptionsMenu(menu);
    }
    public int DeleteSomeItems(View v, SQLiteDatabase _database) {
        int result = 0;
        SQLiteDatabase database = (dbHelper).getWritableDatabase();
        cursor = database.query(DBHelper.TABLE_TRACKS, null, null, null, null, null, null);

        cursor.moveToFirst();


        for (int i = 0; i < cursor.getCount(); i++) {
            if (tracks.get(i).box){
                int id = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID));
                Log.d(TAG, "id = " + id);
                result += _database.delete("tracks", "_id = " + id, null);
            }
            cursor.moveToNext();


        }
        int[] delete = new int[getCheckedCount(tracks)];

        for (int i = 0; i < getCheckedCount(tracks); i++) {
            for (int j = 0; j < cursor.getCount(); j++) {

                if (tracks.get(j).box) {
                    delete[i] = j;
                }
            }
        }
        for (int i = getCheckedCount(tracks) - 1; i >= 0; i--){
            Log.d(TAG, "i = " + i);
            Log.d(TAG, "delete[i] = " + delete[i]);
            tracks.remove(delete[i]);
        }

        if (result == 0){
            Toast.makeText(this, "Ни одной записи не было удалено", Toast.LENGTH_LONG).show();

        } else if (result == 1){
            Toast.makeText(this, "Была удалена " + result + " запись", Toast.LENGTH_LONG).show();

        } else if (result < 5 & result > 1){
            Toast.makeText(this, "Было удалено " + result + " записи", Toast.LENGTH_LONG).show();

        } else if (result > 5){
            Toast.makeText(this, "Было удалено " + result + " записей", Toast.LENGTH_LONG).show();

        }
        cursor.close();
        return  result;
    }

    public void CreateTrack() {
        if (cursor.moveToLast()) {
            count_of_tracks = cursor.getCount();
            names = new String[count_of_tracks];
            temp = new int[count_of_tracks];

        } else {
            count_of_tracks = 1;
            names = new String[count_of_tracks];
            temp = new int[count_of_tracks];
        }


        if (cursor.moveToFirst()) {
            int j = 0;
            do {
                names[j] = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME));
                temp[j] = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_TEMP));
                j++;
            } while (cursor.moveToNext());

            tracks = new ArrayList<>(cursor.getCount());

            for (int i = 0; i < cursor.getCount(); i++) {
                Track t = new Track(names[i], temp[i], i, false);
                tracks.add(t);
            }

        }
    }
    public int getCheckedCount(ArrayList<Track> t){
        int count = 0;
        for (int i = 0; i < t.size(); i++){
            if (t.get(i).box) count++;
        }
        return count;
    }
}



