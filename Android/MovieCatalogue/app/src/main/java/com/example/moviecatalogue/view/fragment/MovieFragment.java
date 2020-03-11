package com.example.moviecatalogue.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalogue.model.Item;
import com.example.moviecatalogue.adapter.ListItemAdapter;
import com.example.moviecatalogue.api.MainViewModel;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.view.activity.DetailActivity;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private RecyclerView rvMovies;
    private ArrayList<Item> items = new ArrayList<>();
    private ProgressBar progressBar;
    private SearchView searchView;
    private ListItemAdapter listMovieAdapter;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        rvMovies = view.findViewById(R.id.rv_items);
        progressBar = view.findViewById(R.id.progressBar);
        searchView = view.findViewById(R.id.search_view);

        showRecyclerList();
        searchAction();

        return view;
    }

    private void showRecyclerList(){
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        listMovieAdapter = new ListItemAdapter(items);
        listMovieAdapter.notifyDataSetChanged();
        rvMovies.setAdapter(listMovieAdapter);

        MainViewModel mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        mainViewModel.setMovie();

        showLoading(true);

        listMovieAdapter.setOnItemClickCallback(new ListItemAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Item item) {
                showSelectedMovie(item);
            }
        });

        mainViewModel.getItem().observe(Objects.requireNonNull(getActivity()), new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                if (items != null) {
                    listMovieAdapter.setListItem(items);
                    showLoading(false);
                }
            }
        });
    }

    private void showSearchList(String query) {
        MainViewModel mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        mainViewModel.searchMovie(query);
        showLoading(true);
        listMovieAdapter.notifyDataSetChanged();

        if (getActivity() != null) {
            mainViewModel.getItem().observe(getActivity(), new Observer<ArrayList<Item>>() {
                @Override
                public void onChanged(ArrayList<Item> items) {
                    if (items != null) {
                        listMovieAdapter.setListItem(items);
                        showLoading(false);
                    }
                }
            });
        }
    }

    private void searchAction() {
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showSearchList(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                showSearchList(newText);
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                showRecyclerList();
                return false;
            }
        });
    }

    private void showSelectedMovie(Item item) {
        Intent movieDetail = new Intent(getContext(), DetailActivity.class);
        movieDetail.putExtra(DetailActivity.EXTRA_ITEM, (Parcelable) item);
        Objects.requireNonNull(getContext()).startActivity(movieDetail);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
