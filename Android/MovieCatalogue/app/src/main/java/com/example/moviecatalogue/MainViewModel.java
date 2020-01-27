package com.example.moviecatalogue;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();

    void setMovie() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();
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
                        Movie movie = new Movie();
                        movie.setPoster("http://image.tmdb.org/t/p/w185" + movies.getString("poster_path"));
                        movie.setBackdrop("http://image.tmdb.org/t/p/w780" + movies.getString("backdrop_path"));
                        movie.setTitle(movies.getString("title"));
                        movie.setDescription(movies.getString("overview"));
                        listItems.add(movie);
                    }

                    listMovies.postValue(listItems);
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

    void setTvShow() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();
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
                        JSONObject movies = list.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setPoster("http://image.tmdb.org/t/p/w185" + movies.getString("poster_path"));
                        movie.setBackdrop("http://image.tmdb.org/t/p/w780" + movies.getString("backdrop_path"));
                        movie.setTitle(movies.getString("name"));
                        movie.setDescription(movies.getString("overview"));
                        listItems.add(movie);
                    }

                    listMovies.postValue(listItems);
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

    LiveData<ArrayList<Movie>> getMovie() {
        return listMovies;
    }
}
