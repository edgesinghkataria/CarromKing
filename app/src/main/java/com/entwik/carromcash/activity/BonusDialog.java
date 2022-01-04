package com.entwik.carromcash.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.entwik.carromcash.R;

public class BonusDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.pop_up_money_reward,null);
        dialogBuilder.setView(dialogView);
        Button gotIt = (Button) dialogView.findViewById(R.id.btnGotIt);
        AlertDialog alert = dialogBuilder.create();
        gotIt.setOnClickListener(l->{
            alert.dismiss();
        });

        alert.show();

        // Hide after some seconds
        final Handler handler  = new Handler();
        final Runnable runnable = () -> {
            if (alert.isShowing()) {
                alert.dismiss();
            }
        };

        alert.setOnDismissListener(dialog -> handler.removeCallbacks(runnable));

        handler.postDelayed(runnable, 5000);

        return alert;
    }
}
