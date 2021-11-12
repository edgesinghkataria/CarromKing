package com.entwik.carromcash.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.entwik.carromcash.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("FirebaseNotifications", "FirebaseNotifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Successful";
                        if (!task.isSuccessful()) {
                            msg = "failed";
                        }
                    }
                });

        SharedPreferences sp = getSharedPreferences(getString(R.string.TAG), Context.MODE_PRIVATE);
        boolean isNew = sp.getBoolean("isNew", false);
        if (isNew) {
            new BonusDialog().show(getSupportFragmentManager(), "bonus dialog");
        }

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FFFFFF"));
        assert getSupportActionBar() != null;
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().hide();

        bottomNav = findViewById(R.id.bottom_navigation);
//      this is to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment(bottomNav)).commit();
        }

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment;

            if (item.getItemId() == R.id.nav_home) {
                selectedFragment = new HomeFragment(bottomNav);

            } else if (item.getItemId() == R.id.nav_wallet) {
                selectedFragment = new WalletFragment();
            } else {
                selectedFragment = new ProfileFragment();
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();

            return true;
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(getString(R.string.TAG), "onOptionsItemSelected: Hello");
        if (item.getItemId() == android.R.id.home) {
            getSupportFragmentManager().popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment(bottomNav));
    }
}