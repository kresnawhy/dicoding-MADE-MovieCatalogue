package com.example.favoriteapp.database;

import android.database.Cursor;

public interface LoadCallback {
    void postExecute(Cursor cursor);
}
