package com.example.moviecatalogue.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.view.fragment.FavoriteMovieFragment;
import com.example.moviecatalogue.view.fragment.FavoriteTvShowFragment;
import com.example.moviecatalogue.view.fragment.MovieFragment;
import com.example.moviecatalogue.view.fragment.TvShowFragment;

public class SectionPagerAdapter extends FragmentPagerAdapter {
    private final Context context;
    private final String menu;

    public SectionPagerAdapter(Context context, FragmentManager fm, String menu) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
        this.menu = menu;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_movie,
            R.string.tab_tv_show
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (menu.equalsIgnoreCase("discover")) {
            switch (position) {
                case 0:
                    fragment = new MovieFragment();
                    break;
                case 1:
                    fragment = new TvShowFragment();
                    break;
            }
        }
        if (menu.equalsIgnoreCase("favorite")) {
            switch (position) {
                case 0:
                    fragment = new FavoriteMovieFragment();
                    break;
                case 1:
                    fragment = new FavoriteTvShowFragment();
                    break;
            }
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.context.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
