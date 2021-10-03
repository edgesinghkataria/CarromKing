package com.android.carromking.activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.carromking.R;


public class WithdrawSuccessfulFragment extends Fragment {

    private homeIconHighlight listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.withdrawal_successful, container, false);

        Button keepPlaying = v.findViewById(R.id.keepPlaying);
        keepPlaying.setOnClickListener(v1 -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            listener.highlightHomeIcon();
        });
        return v;
    }

    public interface homeIconHighlight{
        void highlightHomeIcon();
    }

    public void HighLightHomeIcon(homeIconHighlight listener){
        this.listener = listener;
    }


}
