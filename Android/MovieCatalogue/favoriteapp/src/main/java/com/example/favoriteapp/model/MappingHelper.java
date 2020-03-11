package com.example.favoriteapp.model;

import android.database.Cursor;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<Item> mapCursorToArrayList(Cursor itemCursor){
        ArrayList<Item> listItem = new ArrayList<>();

        while (itemCursor.moveToNext()){
            listItem.add(new Item(itemCursor));
        }
        return listItem;
    }
}
