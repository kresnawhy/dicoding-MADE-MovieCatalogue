package com.example.favoriteapp.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.favoriteapp.R;
import com.example.favoriteapp.adapter.ListItemAdapter;
import com.example.favoriteapp.database.LoadCallback;
import com.example.favoriteapp.model.Item;
import com.example.favoriteapp.view.activity.DetailActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.favoriteapp.model.MappingHelper.mapCursorToArrayList;
import static com.example.favoriteapp.provider.ItemContract.EntryMovie.CONTENT_URI_MOVIE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment implements LoadCallback {
    private RecyclerView recyclerView;
    private ArrayList<Item> listItem;

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver dataObserver = new DataObserver(handler, getActivity());
        getActivity().getContentResolver().registerContentObserver(CONTENT_URI_MOVIE, true, dataObserver);
        new getData(getActivity(), this).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_list, container, false);
        recyclerView = view.findViewById(R.id.rv_favorites);
        return view;
    }

    private void showRecyclerList() {
        ListItemAdapter adapter = new ListItemAdapter(listItem);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickCallback(new ListItemAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Item data) {
                showSelectedMovie(data);
            }
        });
    }

    private void showSelectedMovie(Item item) {
        Intent movieDetail = new Intent(getContext(), DetailActivity.class);
        movieDetail.putExtra(DetailActivity.EXTRA_ITEM, (Parcelable) item);
        Objects.requireNonNull(getContext()).startActivity(movieDetail);
    }

    @Override
    public void postExecute(Cursor cursor){
        listItem = mapCursorToArrayList(cursor);
        showRecyclerList();
    }

    static class DataObserver extends ContentObserver {
        final Context context;

        DataObserver(Handler handler, Context context){
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange){
            super.onChange(selfChange);
            new getData(context, (LoadCallback) context).execute();
        }
    }

    private static class getData extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadCallback> weakCallback;

        private getData(Context context, LoadCallback loadCallback){
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(loadCallback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Cursor cursor = weakContext.get().getContentResolver().query(CONTENT_URI_MOVIE, null, null, null, null);
            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor){
            super.onPostExecute(cursor);
            weakCallback.get().postExecute(cursor);
        }
    }
}
