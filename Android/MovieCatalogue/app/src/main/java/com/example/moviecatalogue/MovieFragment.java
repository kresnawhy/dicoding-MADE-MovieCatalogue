package com.example.moviecatalogue;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private RecyclerView rvMovies;
    private ArrayList<Movie> movies = new ArrayList<>();

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        rvMovies = view.findViewById(R.id.rv_movies);
        rvMovies.setHasFixedSize(true);

        showRecyclerList();

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movies.addAll(getListMovies());
    }

    private ArrayList<Movie> getListMovies() {
        String[] dataTitle = getResources().getStringArray(R.array.data_title_movie);
        String[] dataDescription = getResources().getStringArray(R.array.data_description_movie);
        String[] dataPoster = getResources().getStringArray(R.array.data_poster_movie);

        ArrayList<Movie> listMovie = new ArrayList<>();
        for (int i = 0; i < dataTitle.length; i++) {
            Movie movie = new Movie();
            movie.setTitle(dataTitle[i]);
            movie.setDescription(dataDescription[i]);
            movie.setPoster(dataPoster[i]);

            listMovie.add(movie);
        }
        return listMovie;
    }

    private void showRecyclerList(){
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        ListMovieAdapter listMovieAdapter = new ListMovieAdapter(movies);
        rvMovies.setAdapter(listMovieAdapter);
    }

}
