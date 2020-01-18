package com.example.moviecatalogue;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvMovies;
    private ArrayList<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMovies = findViewById(R.id.rv_movies);
        rvMovies.setHasFixedSize(true);

        movies.addAll(getListMovies());
        showRecyclerList();


    }

    public ArrayList<Movie> getListMovies() {
        String[] dataTitle = getResources().getStringArray(R.array.data_title);
        String[] dataDescription = getResources().getStringArray(R.array.data_description);
        String[] dataPoster = getResources().getStringArray(R.array.data_poster);

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
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        ListMovieAdapter listMovieAdapter = new ListMovieAdapter(movies);
        rvMovies.setAdapter(listMovieAdapter);
    }
}
