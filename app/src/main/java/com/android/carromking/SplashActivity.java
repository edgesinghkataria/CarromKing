package com.android.carromking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {
    private SharedPreferences sp;

    final String TAG = "com.android.carromking";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sp = getSharedPreferences(TAG, MODE_PRIVATE);

        new Handler().postDelayed(() -> {
            Intent i = sp.getBoolean("login", false) ?
                    new Intent(SplashActivity.this, MainActivity.class) :
                    new Intent(SplashActivity.this, SignUpActivity.class);
            startActivity(i);
            finish();
        }, 3000);


    }
}