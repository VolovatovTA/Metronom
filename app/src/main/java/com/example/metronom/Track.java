package com.example.metronom;

public class Track {
    String name;
    int temp;
    boolean acc;
    int count1;
    int count2;
    int position;
    int id;
    boolean box;

    Track(String _name, int _temp, boolean _acc, int _count1, int _count2, int _position, boolean _box, int _id) {
        name = _name;
        temp = _temp;
        acc = _acc;
        count1 = _count1;
        count2 = _count2;
        position = _position;
        box = _box;
        id = _id;
    }

}
