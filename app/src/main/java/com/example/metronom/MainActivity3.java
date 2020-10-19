package com.example.metronom;

import android.annotation.SuppressLint;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {

    String TAG = "lifecycle111";
    DBHelper dbHelper;
    ListView lvList;
    static boolean isSelectionMode = false;
    int count_of_tracks;
    String[] names;
    int[] temp;
    boolean[] acc;
    int[] count1;
    int[] count2;
    int[] ides;
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
        lvList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

            lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    if (!isSelectionMode) {
                        Log.d(TAG, "itemClick: position = " + position + ", id = " + id);


                        Intent intent = new Intent(MainActivity3.this, MainActivity.class);

                            intent.putExtra("name", tracks.get(position).name);
                            intent.putExtra("temp",  tracks.get(position).temp);
                            intent.putExtra("acc",  tracks.get(position).acc);
                            intent.putExtra("count1",  tracks.get(position).count1);
                            intent.putExtra("count2",  tracks.get(position).count2);
                            startActivity(intent);

                    }
                }
            });
        }
        /*lvList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                isSelectionMode = false;

            }

        });*/



    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 1, "Choose");
        menu.add(1, 2, 0, "Delete");
        menu.add(1, 3, 0, "Paste in List");
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            isSelectionMode = true;
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);

            for (int i = 0; i < lvList.getChildCount(); i++){
                View view = ((LinearLayout)lvList.getChildAt(i));
                Log.d(TAG, "i = " + i);
                CheckBox checkBox = view.findViewById(R.id.cbBox);
                LinearLayout.LayoutParams params_for_chBox = (LinearLayout.LayoutParams) checkBox.getLayoutParams();;
                params_for_chBox.width = 51;
                params_for_chBox.leftMargin = 15;
                checkBox.setLayoutParams(params_for_chBox);
                checkBox.setVisibility(View.VISIBLE);
                checkBox.startAnimation(animation);
            }



        } else if (item.getItemId() == 2) {
            isSelectionMode = false;
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale1);

            for (int i = 0; i < lvList.getChildCount(); i++){
                View view = ((LinearLayout)lvList.getChildAt(i));
                CheckBox checkBox = view.findViewById(R.id.cbBox);
                checkBox.startAnimation(animation);
                checkBox.setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
                checkBox.setVisibility(View.INVISIBLE);
            }

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
        // пункты меню с ID группы = 1 видны, если выбран режим редактирования
        menu.setGroupVisible(1, isSelectionMode);

        return super.onPrepareOptionsMenu(menu);
    }
    public int DeleteSomeItems(View v, SQLiteDatabase _database) {
        int result = getCheckedCount(tracks);
        SQLiteDatabase database = (dbHelper).getWritableDatabase();
        cursor = database.query(DBHelper.TABLE_TRACKS, null, null, null, null, null, null);

        int[] ides = getCheckedIdes(tracks);
        for (int i = 0; i < result; i++){
            cursor.moveToPosition(ides[i]);
            _database.delete("tracks", "_id = " + ides[i], null);
            int j = getTrackById(tracks, ides[i]);
            tracks.remove(j);
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
    private int getTrackById(ArrayList<Track> tracks, int id) {
        int fended_id = 0;
        for (int i = 0; i < tracks.size(); i++){
            if (tracks.get(i).id == id) fended_id = i;
        }
        return fended_id;
    }
    public void CreateTrack() {
        if (cursor.moveToLast()) {
            count_of_tracks = cursor.getCount();
            names = new String[count_of_tracks];
            acc = new boolean[count_of_tracks];
            count1 = new int[count_of_tracks];
            count2 = new int[count_of_tracks];
            temp = new int[count_of_tracks];
            ides = new int[count_of_tracks];

        } else {
            count_of_tracks = 1;
            names = new String[count_of_tracks];
            acc = new boolean[count_of_tracks];
            count1 = new int[count_of_tracks];
            count2 = new int[count_of_tracks];
            temp = new int[count_of_tracks];
            ides = new int[count_of_tracks];
        }


        if (cursor.moveToFirst()) {
            int j = 0;
            do {
                names[j] = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME));
                temp[j] = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_TEMP));
                acc[j] = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ACCENT)) != 0;
                count1[j] = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_COUNT1));
                count2[j] = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_COUNT2));
                ides[j] = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID));
                j++;
            } while (cursor.moveToNext());

            tracks = new ArrayList<>(cursor.getCount());

            for (int i = 0; i < cursor.getCount(); i++) {
                Track t = new Track(names[i], temp[i], acc[i], count1[i], count2[i],  i, false, ides[i]);
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
    public int[] getCheckedIdes(ArrayList<Track> t){
        int count_current = 0;
        int[] ides = new int[getCheckedCount(t)];

        for (int i = 0; i < t.size(); i++){
            if (t.get(i).box) {
                ides[count_current] = t.get(i).id;
                count_current++;
            }
        }

        return ides;
    }

}



