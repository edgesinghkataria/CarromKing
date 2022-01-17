package com.entwik.carromcash.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.entwik.carromcash.R;

public class AddMoneyDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.pop_up_add_money,null);
        dialogBuilder.setView(dialogView);
        AlertDialog alert = dialogBuilder.create();
        Button addMoney = dialogView.findViewById(R.id.addMoney);
        addMoney.setOnClickListener(l->{
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new WalletFragment()).addToBackStack(null).commit();
            dismiss();
        });
        Button cancelTransaction = dialogView.findViewById(R.id.cancel);
        cancelTransaction.setOnClickListener(l->{
            dismiss();
        });
        return alert;

    }
}
