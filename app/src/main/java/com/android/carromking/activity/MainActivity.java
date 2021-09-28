package com.android.carromking.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;

import com.android.carromking.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FFFFFF"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().hide();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
//      this is to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }


        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment;

            if (item.getItemId() == R.id.nav_home) {
                selectedFragment = new HomeFragment();
                bottomNav.getMenu().findItem(R.id.nav_home).setChecked(true);

            } else if (item.getItemId() == R.id.nav_wallet) {
                WalletFragment walletFragment = new WalletFragment();
                walletFragment.HighLightHomeIconThree(new WalletFragment.homeIconHighlightThree() {
                    @Override
                    public void highlightHomeIconThree() {
                        bottomNav.setSelectedItemId(R.id.nav_home);
                    }
                });
                selectedFragment = walletFragment;
            } else {
                selectedFragment = new ProfileFragment();
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();

            return true;
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment());
    }
}