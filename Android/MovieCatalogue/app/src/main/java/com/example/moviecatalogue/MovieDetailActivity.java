package com.example.moviecatalogue;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.Objects;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TextView detailTitle = findViewById(R.id.txt_title_detail);
        TextView detailDescription = findViewById(R.id.txt_description_detail);
        ImageView detailPoster = findViewById(R.id.img_poster_detail);
        ImageView Backdrop = findViewById(R.id.img_backdrop_detail);
        progressBar = findViewById(R.id.progressBar);

        showLoading(true);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        if (movie != null) {
            detailTitle.setText(movie.getTitle());
            detailDescription.setText(movie.getDescription());
            Glide.with(this)
                    .asBitmap()
                    .load(movie.getPoster())
                    .transform(new RoundedCorners(30))
                    .into(detailPoster);
            Glide.with(this)
                    .asBitmap()
                    .load(movie.getBackdrop())
                    .into(Backdrop);
            showLoading(false);
        }
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
