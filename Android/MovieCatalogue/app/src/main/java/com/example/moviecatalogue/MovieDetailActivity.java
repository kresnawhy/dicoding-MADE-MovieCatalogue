package com.example.moviecatalogue;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        TextView detailTitle = findViewById(R.id.txt_title_detail);
        TextView detailDescription = findViewById(R.id.txt_description_detail);
        ImageView detailPoster = findViewById(R.id.img_poster_detail);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        detailTitle.setText(movie.getTitle());
        detailDescription.setText(movie.getDescription());
        detailPoster.setImageResource(movie.getPoster());
    }
}
