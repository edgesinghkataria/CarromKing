package com.android.carromking.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class CategoryAdapter extends FragmentStateAdapter {

    public CategoryAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        String[] lobbies = {"beginner", "silver", "gold", "diamond"};
        return new LeagueFragment(lobbies[position]);
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}