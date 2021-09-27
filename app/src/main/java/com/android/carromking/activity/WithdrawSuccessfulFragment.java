package com.android.carromking.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
        keepPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                listener.highlightHomeIcon();
            }
        });
        return v;
    }

    public interface homeIconHighlight{
        void highlightHomeIcon();
    }

    public void HighLightHomeIcon(homeIconHighlight listener){
        this.listener = listener;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).setTitle("Withdraw Balance");
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
}
