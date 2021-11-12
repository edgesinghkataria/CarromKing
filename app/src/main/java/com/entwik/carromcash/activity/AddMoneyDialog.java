package com.entwik.carromcash.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.entwik.carromcash.R;

public class AddMoneyDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Add Money")
                .setMessage("Don't have enough money to play")
                .setPositiveButton("Add Money", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new WalletFragment()).addToBackStack(null).commit();
                    }
                })
                .create();
    }
}
