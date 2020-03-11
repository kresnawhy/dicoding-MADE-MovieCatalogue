package com.example.favoriteapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.favoriteapp.model.Item;

@Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ItemDAO itemDAO();
}
