package com.example.metronom;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.Date;


public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener, SoundPool.OnLoadCompleteListener, View.OnClickListener, TextView.OnEditorActionListener {
    private static final int START = 12345;
    String TAG = "lifecycle111";
    final int MAX_STREAMS = 3;
    // Кнопки пуска и акцента
    ToggleButton play;
    ToggleButton accent;
    //
    Button button1p, button5p, button10p, button1m, button5m, button10m, button_to_save_LIB, button_to_save_LIST;
    SeekBar seekBar;
    SoundPool sp1, sp2;
    EditText editTextNumber;
    TextView tvCount1, tvCount2, name_of_metronom;
    TabLayout tabLayout;
    long freq;
    long minfreq = 30, maxfreq = 300;
    boolean accentOn;
    int soundIdShot1, soundIdShot2;
    int number_share = 4, number_sounds = 4, count = 1;
    int l = 0;
    Button tap;
    final int N1 = 2, N2 = 4, N3 = 8, N4 = 16;
    final int n1 = 101, n2 = 102, n3 = 103, n4 = 104, n6 = 106, n8 = 108, n12 = 112, n16 = 116;
    int N = N2;
    Handler h;
    int date_average = 1;
    Date date1 = null, date2 = null, date3 = null, date4 = null, date5 = null;

    @Override
    protected void onStart() {
        Log.d(TAG, "MainActivity onStart");
        super.onStart();
        /*Intent intent_from_3 = getIntent();
        freq = intent_from_3.getIntExtra("temp", (int) freq);*/

    }
    @Override
    protected void onResume() {
        Log.d(TAG, "MainActivity onResume");
        super.onResume();
    }
    @Override
    protected void onStop() {
        Log.d(TAG, "MainActivity onStop");
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        Log.d(TAG, "MainActivity onDestroy");
        super.onDestroy();
    }
    @SuppressLint({"HandlerLeak", "SetTextI18n", "WrongViewCast"})

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "freq = " + freq);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*

        Log.d(TAG, "freq = " + intent_from_3.getIntExtra("temp", (int) freq));
        Log.d(TAG, "MainActivity onCreate");
*/        Log.d(TAG, "freq = " + freq);

        getSupportActionBar().setCustomView(R.layout.action_bar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tabLayout.selectTab(tab);

        tabLayout.setOnClickListener(this);

        play = findViewById(R.id.play);
        tap = findViewById(R.id.tap);

        button1p = findViewById(R.id.bn1p);
        button5p = findViewById(R.id.bn5p);
        button10p = findViewById(R.id.bn10p);
        button1m = findViewById(R.id.bn1m);
        button5m = findViewById(R.id.bn5m);
        button10m = findViewById(R.id.bn10m);
        button_to_save_LIB = findViewById(R.id.saveLIB);
        button_to_save_LIST = findViewById(R.id.saveLIST);
        seekBar = findViewById(R.id.freq);
        editTextNumber = findViewById(R.id.editTextNumber);
        tvCount1 = findViewById(R.id.count1);
        tvCount2 = findViewById(R.id.count2);
        accent = findViewById(R.id.accent);
        name_of_metronom = findViewById(R.id.Name);

        tap.setOnClickListener(this);
        button1p.setOnClickListener(this);
        button5p.setOnClickListener(this);
        button10p.setOnClickListener(this);
        button1m.setOnClickListener(this);
        button5m.setOnClickListener(this);
        button10m.setOnClickListener(this);
        button_to_save_LIB.setOnClickListener(this);
        button_to_save_LIST.setOnClickListener(this);
        Log.d(TAG, "freq = " + freq);

        registerForContextMenu(tvCount1);
        registerForContextMenu(tvCount2);

        editTextNumber.setOnEditorActionListener(this);
        seekBar.setOnSeekBarChangeListener(this);

        play.setOnCheckedChangeListener(this);
        accent.setOnCheckedChangeListener(this);

        sp1 = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        sp2 = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);

        sp1.setOnLoadCompleteListener(this);
        sp2.setOnLoadCompleteListener(this);

        soundIdShot1 = sp1.load(this, R.raw.m61, 1);
        soundIdShot2 = sp2.load(this, R.raw.m62, 1);

        Log.d(TAG, "freq = " + freq);

        seekBar.setMax((int) (maxfreq - minfreq));
        Log.d(TAG, "freq = " + freq);
        Intent intent_from_3 = getIntent();
        String name = "";
        if (intent_from_3.getIntExtra("temp", 0) != 0){
            freq = intent_from_3.getIntExtra("temp", (int) freq);
            freq = freq - 60;
            name = intent_from_3.getStringExtra("name");
            number_share = intent_from_3.getIntExtra("count2", 4);
            number_sounds = intent_from_3.getIntExtra("count1", 4);
            accentOn = intent_from_3.getBooleanExtra("acc", false);

        }


        Log.d(TAG, "freq = " + freq);

        seekBar.setProgress((int) (freq + minfreq));
        Log.d(TAG, "freq = " + freq);


        editTextNumber.setText(Long.toString(freq));
        name_of_metronom.setText(name);
        tvCount1.setText(number_sounds + "");
        tvCount2.setText(number_share + "");
        accent.setChecked(accentOn);

        // Ядро метронома, воспроизводящее звук
        h = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == START) {
                    if (accentOn & count == 1) sp1.play(soundIdShot1, 1, 1, 1, 0, 1);
                    else {
                        sp2.play(soundIdShot2, 1, 1, 1, 0, 1);
                        if (count == number_sounds) count = 0;
                    }
                    count++;
                    if (play.isChecked())
                        h.sendEmptyMessageDelayed(START, 240000 / freq / number_share);
                }
            }
        };
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        switch (compoundButton.getId()) {
            case R.id.play:
                if (b){
                    count = 1;
                    h.sendEmptyMessage(START);
                }
                else h.removeMessages(START);
                break;
            case R.id.accent:
                accentOn = b;
                count = 1;
                number_sounds = Integer.parseInt((String) tvCount1.getText());
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        freq = minfreq + seekBar.getProgress();
        editTextNumber.setText(Long.toString(freq));
        Log.d(TAG, "onProgressChanged");

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.d(TAG, "onStartTrackingTouch");

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        freq = minfreq + seekBar.getProgress();
        editTextNumber.setText(freq + "");
        Log.d(TAG, "onStopTrackingTouch");

    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int i, int i1) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()){
            case R.id.count2:
                menu.add(0, N1, 0 , "2");
                menu.add(0, N2, 0 , "4");
                menu.add(0, N3, 0 , "8");
                menu.add(0, N4, 0 , "16");
                break;
            case R.id.count1:
                menu.clear();
                switch (N) {
                    case N1:
                        menu.add(0, n2, 0 , "2");
                        break;
                    case N2:
                        menu.add(0, n2, 0 , "2");
                        menu.add(0, n3, 0 , "3");
                        menu.add(0, n4, 0 , "4");
                        break;
                    case N3:
                        menu.add(0, n4, 0 , "4");
                        menu.add(0, n6, 0 , "6");
                        menu.add(0, n8, 0 , "8");
                        break;
                    case N4:
                        menu.add(0, n8, 0 , "8");
                        menu.add(0, n12, 0 , "12");
                        menu.add(0, n16, 0 , "16");
                        break;
                }
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case N1:
                N = N1;
                tvCount2.setText("2");
                tvCount1.setText("2");
                break;
            case N2:
                N = N2;
                tvCount2.setText("4");
                tvCount1.setText("4");
                break;
            case N3:
                N = N3;
                tvCount2.setText("8");
                tvCount1.setText("8");
                break;
            case N4:
                N = N4;
                tvCount2.setText("16");
                tvCount1.setText("16");
                break;
            case n1:
                tvCount1.setText("1");
                break;
            case n2:
                tvCount1.setText("2");
                break;
            case n3:
                tvCount1.setText("3");
                break;
            case n4:
                tvCount1.setText("4");
                break;
            case n6:
                tvCount1.setText("6");
                break;
            case n8:
                tvCount1.setText("8");
                break;
            case n12:
                tvCount1.setText("12");
                break;
            case n16:
                tvCount1.setText("16");
                break;

        }
        number_sounds = Integer.parseInt((String) tvCount1.getText());
        number_share = Integer.parseInt((String) tvCount2.getText());
        count = 1;

        return false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.bn1p:
                if (Integer.parseInt(String.valueOf(editTextNumber.getText())) < maxfreq) freq++;
                break;
            case R.id.bn5p:
                if (Integer.parseInt(String.valueOf(editTextNumber.getText())) < maxfreq) freq = freq + 5;
                break;
            case R.id.bn10p:
                if (Integer.parseInt(String.valueOf(editTextNumber.getText())) < maxfreq) freq = freq + 10;
                break;
            case R.id.bn1m:
                if (Integer.parseInt(String.valueOf(editTextNumber.getText())) > minfreq) freq--;
               break;
            case R.id.bn5m:
                if (Integer.parseInt(String.valueOf(editTextNumber.getText())) > minfreq) freq = freq - 5;
                break;
            case R.id.bn10m:
                if (Integer.parseInt(String.valueOf(editTextNumber.getText())) > minfreq) freq = freq - 10;
                break;
            case R.id.tap:
                if ( date1!= null) {
                    date5 = new Date();

                    if ((int) (date5.getTime() - date1.getTime()) > 6000){
                        l = 0;
                    }
                }
                switch (l){
                    case 0:
                        date1 = new Date();
                        l = l + 1;
                        break;
                    case 1:
                        date2 = new Date();
                        l = l + 1;
                        break;
                    case 2:
                        date3 = new Date();
                        l = l + 1;
                        break;
                    case 3:
                        date4 = new Date();
                        l = l + 1;
                        break;
                }

                if (l == 4) {
                    date_average = (int) ((date2.getTime()-date1.getTime())/3 + (date3.getTime()-date2.getTime())/3 + (date4.getTime()-date3.getTime())/3);

                    if (60000 / date_average > minfreq & 60000 / date_average < maxfreq){
                        editTextNumber.setText(freq + "");
                        freq = 60000 / date_average;
                    }
                    else Snackbar.make(view, "Чёт ты разтапался или наобород недотапался", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    l = 0;
                }
                break;
            case R.id.saveLIST:
                Intent intent1 = new Intent(this, Saver.class);
                intent1.putExtra("temp", freq);
                intent1.putExtra("accent", accentOn);
                intent1.putExtra("number_share", number_share);
                intent1.putExtra("number_sounds", number_sounds);
                startActivity(intent1);
                break;
            case R.id.saveLIB:

                break;
            case R.id.tabLibrary:
                Intent intent = new Intent(this, Library.class);
                startActivity(intent);
                break;
        }
        editTextNumber.setText(freq + "");
        seekBar.setProgress(Integer.parseInt(Long.toString(freq - minfreq)));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        Log.d(TAG, "onEditorAction");

        if (Integer.parseInt(String.valueOf(editTextNumber.getText())) > maxfreq){
            editTextNumber.setText(maxfreq + "");
        }
        else if (Integer.parseInt(String.valueOf(editTextNumber.getText())) < minfreq){
            editTextNumber.setText(minfreq + "");
        }
        freq = Integer.parseInt(String.valueOf(editTextNumber.getText()));
        seekBar.setProgress(Integer.parseInt(Long.toString(freq - minfreq)));

            return  false;
    }
}
