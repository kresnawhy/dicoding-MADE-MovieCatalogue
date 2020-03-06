package com.example.moviecatalogue.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.database.AppDatabase;
import com.example.moviecatalogue.model.Item;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_ITEM = "extra_item";
    private AppDatabase db;
    private ProgressBar progressBar;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "itemdb").allowMainThreadQueries().build();

        final Item item = getIntent().getParcelableExtra(EXTRA_ITEM);

        try {
            db.itemDAO().favoriteChecker(item.getTitle()).get(0).getTitle();
            isFavorite = true;
        } catch (Exception ignored) {
            isFavorite = false;
        }

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TextView detailTitle = findViewById(R.id.txt_title_detail);
        TextView detailDescription = findViewById(R.id.txt_description_detail);
        ImageView detailPoster = findViewById(R.id.img_poster_detail);
        ImageView Backdrop = findViewById(R.id.img_backdrop_detail);
        Button btnFavorite = findViewById(R.id.btn_favorite);
        progressBar = findViewById(R.id.progressBar);

        if (isFavorite) {
            btnFavorite.setText(R.string.delete_from_favorite);
        } else {
            btnFavorite.setText(R.string.add_to_favorite);
        }

        showLoading(true);

        if (item != null) {
            detailTitle.setText(item.getTitle());
            detailDescription.setText(item.getDescription());
            Glide.with(this)
                    .asBitmap()
                    .load(item.getPoster())
                    .transform(new RoundedCorners(30))
                    .into(detailPoster);
            Glide.with(this)
                    .asBitmap()
                    .load(item.getBackdrop())
                    .into(Backdrop);
            showLoading(false);
        }

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    deleteData(item);
                    refreshActivity();
                } else {
                    insertData(item);
                    refreshActivity();
                }
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void insertData(final Item item) {
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                return db.itemDAO().insertItem(item);
            }
        }.execute();
    }

    private void deleteData(Item item) {
        db.itemDAO().deleteItem(item);
    }

    private void refreshActivity() {
        Intent refresh = getIntent();
        startActivity(refresh);
        finish();
    }
}
