package com.example.metronom;

public class Track {
    String name;
    int temp;
    int position;
    int id;
    boolean box;

    Track(String _name, int _temp, int _position, boolean _box, int _id) {
        name = _name;
        temp = _temp;
        position = _position;
        box = _box;
        id = _id;
    }

}
