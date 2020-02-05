package com.example.moviecatalogue.api;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviecatalogue.BuildConfig;
import com.example.moviecatalogue.model.Item;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class MainViewModel extends ViewModel {
    private String TMDB_API_KEY = BuildConfig.API_KEY;
    private String language = Locale.getDefault().getDisplayLanguage();
    private MutableLiveData<ArrayList<Item>> listItems = new MutableLiveData<>();

    public void setMovie() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Item> listMovies = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + TMDB_API_KEY;

        if (language.equalsIgnoreCase("INDONESIA")) {
            url += "&language=id-ID";
        }else if (language.equalsIgnoreCase("ENGLISH")) {
            url += "&language=en-US";
        }

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < 10; i++) {
                        JSONObject movies = list.getJSONObject(i);
                        Item item = new Item();
                        item.setPoster("http://image.tmdb.org/t/p/w185" + movies.getString("poster_path"));
                        item.setBackdrop("http://image.tmdb.org/t/p/w780" + movies.getString("backdrop_path"));
                        item.setTitle(movies.getString("title"));
                        item.setDescription(movies.getString("overview"));
                        listMovies.add(item);
                    }

                    listItems.postValue(listMovies);
                } catch (Exception e) {
                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", Objects.requireNonNull(error.getMessage()));
            }
        });
    }

    public void setTvShow() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Item> listTvShows = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + TMDB_API_KEY;

        if (language.equalsIgnoreCase("INDONESIA")) {
            url = "https://api.themoviedb.org/3/discover/tv?api_key=" + TMDB_API_KEY + "&language=id-ID";
        }else if (language.equalsIgnoreCase("ENGLISH")) {
            url = "https://api.themoviedb.org/3/discover/tv?api_key=" + TMDB_API_KEY + "&language=en-US";
        }

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < 10; i++) {
                        JSONObject tv_shows = list.getJSONObject(i);
                        Item item = new Item();
                        item.setPoster("http://image.tmdb.org/t/p/w185" + tv_shows.getString("poster_path"));
                        item.setBackdrop("http://image.tmdb.org/t/p/w780" + tv_shows.getString("backdrop_path"));
                        item.setTitle(tv_shows.getString("name"));
                        item.setDescription(tv_shows.getString("overview"));
                        listTvShows.add(item);
                    }

                    listItems.postValue(listTvShows);
                } catch (Exception e) {
                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", Objects.requireNonNull(error.getMessage()));
            }
        });
    }

    public LiveData<ArrayList<Item>> getItem() {
        return listItems;
    }
}
