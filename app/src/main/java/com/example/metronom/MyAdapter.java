package com.example.metronom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Track> objects;

    MyAdapter(Context context, ArrayList<Track> track) {
        ctx = context;
        objects = track;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @SuppressLint("SetTextI18n")
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_check, parent, false);
        }
        Track t = getTrack(position);

        // заполняем View в пункте списка данными из треков: наименование,

        ((TextView) view.findViewById(R.id.tvName)).setText(t.name);
        ((TextView) view.findViewById(R.id.tvTemp)).setText(t.temp + "");
        ((TextView) view.findViewById(R.id.tvCount1)).setText(t.count1 + "");
        ((TextView) view.findViewById(R.id.tvCount2)).setText(t.count2 + "");

        if (t.acc) ((TextView) view.findViewById(R.id.tvAccent)).setText("Accent: On");
        else ((TextView) view.findViewById(R.id.tvAccent)).setText("Accent: Off");

        CheckBox checkBox = (CheckBox) view.findViewById(R.id.cbBox);
        if (MainActivity3.isSelectionMode) {
            LinearLayout.LayoutParams params_for_chBox = (LinearLayout.LayoutParams) checkBox.getLayoutParams();;
            params_for_chBox.width = 51;
            params_for_chBox.leftMargin = 15;
            checkBox.setLayoutParams(params_for_chBox);
            checkBox.setVisibility(View.VISIBLE);
        }
        else {
            LinearLayout.LayoutParams params_for_chBox = (LinearLayout.LayoutParams) checkBox.getLayoutParams();;
            params_for_chBox.width = 1;
            params_for_chBox.leftMargin = 15;
            checkBox.setLayoutParams(params_for_chBox);
            checkBox.setVisibility(View.INVISIBLE);
        }
        // присваиваем чекбоксу обработчик
        checkBox.setOnCheckedChangeListener(myCheckChangeList);
        // пишем позицию
        checkBox.setTag(position);
        // заполняем данными из товаров: в корзине или нет
        checkBox.setChecked(t.box);
        return view;
    }

    // track по позиции
    Track getTrack(int position) {
        return ((Track) getItem(position));
    }


    // обработчик для чекбоксов
    OnCheckedChangeListener myCheckChangeList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // меняем данные товара (в корзине или нет)
            getTrack((Integer) buttonView.getTag()).box = isChecked;
        }
    };
}
