package com.example.moviecatalogue.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.adapter.ListItemAdapter;
import com.example.moviecatalogue.database.AppDatabase;
import com.example.moviecatalogue.model.Item;
import com.example.moviecatalogue.view.activity.DetailActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment {
    private RecyclerView recyclerView;

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_list, container, false);
        recyclerView = view.findViewById(R.id.rv_favorites);
        showRecyclerList();
        return view;
    }

    private void showRecyclerList() {
        AppDatabase db = Room.databaseBuilder(Objects.requireNonNull(getActivity()).getApplicationContext(), AppDatabase.class, "itemdb").allowMainThreadQueries().build();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<Item> list = new ArrayList<>(Arrays.asList(db.itemDAO().selectAllMovie()));
        ListItemAdapter listItemAdapter = new ListItemAdapter(list);
        listItemAdapter.setOnItemClickCallback(new ListItemAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Item item) {
                showSelectedMovie(item);
            }
        });
        recyclerView.setAdapter(listItemAdapter);
    }

    private void showSelectedMovie(Item item) {
        Intent movieDetail = new Intent(getContext(), DetailActivity.class);
        movieDetail.putExtra(DetailActivity.EXTRA_ITEM, (Parcelable) item);
        Objects.requireNonNull(getContext()).startActivity(movieDetail);
    }
}
