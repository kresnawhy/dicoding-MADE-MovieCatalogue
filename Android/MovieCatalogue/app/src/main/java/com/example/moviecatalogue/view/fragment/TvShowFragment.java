package com.example.moviecatalogue.view.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.adapter.ListItemAdapter;
import com.example.moviecatalogue.api.MainViewModel;
import com.example.moviecatalogue.model.Item;
import com.example.moviecatalogue.view.activity.DetailActivity;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private RecyclerView rvTvShows;
    private ArrayList<Item> items = new ArrayList<>();
    private ProgressBar progressBar;
    private ListItemAdapter listTvShowAdapter;

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        rvTvShows = view.findViewById(R.id.rv_items);
        progressBar = view.findViewById(R.id.progressBar);
        setHasOptionsMenu(true);
        showRecyclerList();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);

        final MainViewModel mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    search(mainViewModel, query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(mainViewModel, newText);
                    return false;
                }
            });
        }
    }

    private void showRecyclerList(){
        rvTvShows.setLayoutManager(new LinearLayoutManager(getActivity()));
        listTvShowAdapter = new ListItemAdapter(items);
        listTvShowAdapter.notifyDataSetChanged();
        rvTvShows.setAdapter(listTvShowAdapter);

        MainViewModel mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        mainViewModel.setTvShow();

        showLoading(true);

        listTvShowAdapter.setOnItemClickCallback(new ListItemAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Item item) {
                showSelectedTvShow(item);
            }
        });

        mainViewModel.getItem().observe(Objects.requireNonNull(getActivity()), new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                if (items != null) {
                    listTvShowAdapter.setListItem(items);
                    showLoading(false);
                }
            }
        });
    }

    private void search(MainViewModel mainViewModel, String query) {
        mainViewModel.searchTvShow(query);
        showLoading(true);
        listTvShowAdapter.notifyDataSetChanged();

        if (getActivity() != null) {
            mainViewModel.getItem().observe(getActivity(), new Observer<ArrayList<Item>>() {
                @Override
                public void onChanged(ArrayList<Item> items) {
                    if (items != null) {
                        listTvShowAdapter.setListItem(items);
                        showLoading(false);
                    }
                }
            });
        }
    }

    private void showSelectedTvShow(Item item) {
        Intent tvshowDetail = new Intent(getContext(), DetailActivity.class);
        tvshowDetail.putExtra(DetailActivity.EXTRA_ITEM, (Parcelable) item);
        Objects.requireNonNull(getContext()).startActivity(tvshowDetail);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
