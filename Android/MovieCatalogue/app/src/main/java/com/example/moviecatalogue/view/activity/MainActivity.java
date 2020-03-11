package com.example.moviecatalogue.view.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.view.fragment.FavoriteContainerFragment;
import com.example.moviecatalogue.view.fragment.ItemContainerFragment;
import com.example.moviecatalogue.view.fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new ItemContainerFragment());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_discover:
                fragment = new ItemContainerFragment();
                break;
            case R.id.navigation_favorite:
                fragment = new FavoriteContainerFragment();
                break;
            case R.id.action_change_settings:
                fragment = new SettingFragment();
                break;
        }
        return loadFragment(fragment);
    }
}
