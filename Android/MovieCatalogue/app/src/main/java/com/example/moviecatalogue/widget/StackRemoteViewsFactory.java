package com.example.moviecatalogue.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.database.AppDatabase;
import com.example.moviecatalogue.model.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final List<Bitmap> widgetItems = new ArrayList<>();
    private final Context context;
    private ArrayList<Item> listItem;

    public StackRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        AppDatabase db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "itemdb").allowMainThreadQueries().build();
        listItem = new ArrayList<>(Arrays.asList(db.itemDAO().selectAllMovie()));
        for (int i = 0; i < listItem.size(); i++) {
            Bitmap bitmap = null;
            try {
                bitmap= Glide.with(context)
                        .asBitmap()
                        .load(listItem.get(i).getPoster())
                        .into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).get();
            } catch (Exception e){
                e.getMessage();
            }
            widgetItems.add(bitmap);
        }
        listItem = new ArrayList<>(Arrays.asList(db.itemDAO().selectAllTvShow()));
        for (int i = 0; i < listItem.size(); i++) {
            Bitmap bitmap = null;
            try {
                bitmap= Glide.with(context)
                        .asBitmap()
                        .load(listItem.get(i).getPoster())
                        .into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).get();
            } catch (Exception e){
                e.getMessage();
            }
            widgetItems.add(bitmap);
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return widgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        rv.setImageViewBitmap(R.id.imageView, widgetItems.get(position));
        Bundle extras = new Bundle();
        extras.putInt(ImageBannerWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
