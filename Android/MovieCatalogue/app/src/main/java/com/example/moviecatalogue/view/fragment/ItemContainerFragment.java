package com.example.moviecatalogue.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.adapter.SectionPagerAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemContainerFragment extends Fragment {


    public ItemContainerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(getActivity(), getChildFragmentManager(), "discover");
        ViewPager viewPager = view.findViewById(R.id.discover_view_pager);
        viewPager.setAdapter(sectionPagerAdapter);
        TabLayout tabs = view.findViewById(R.id.discover_tabs);
        tabs.setupWithViewPager(viewPager);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setElevation(0);
        }
    }
}
