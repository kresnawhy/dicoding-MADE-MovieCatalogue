package com.example.moviecatalogue.database;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.moviecatalogue.model.Item;

import java.util.List;

@Dao
public interface ItemDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertItem(Item item);

    @Update
    int updateItem(Item item);

    @Delete
    void deleteItem(Item item);

    @Query("SELECT * FROM tableItem WHERE category = 'movie'")
    Item[] selectAllMovie();

    @Query("SELECT * FROM tableItem WHERE category = 'movie'")
    Cursor selectMovieProvider();

    @Query("SELECT * FROM tableItem WHERE category = 'tv_show'")
    Item[] selectAllTvShow();

    @Query("SELECT * FROM tableItem WHERE category = 'tv_show'")
    Cursor selectTvShowProvider();

    @Query("SELECT * FROM tableItem WHERE title = :movieTitle")
    List<Item> favoriteChecker(String movieTitle);

}
