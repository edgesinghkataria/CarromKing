package com.entwik.carromcash.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.entwik.carromcash.R;
import com.entwik.carromcash.utils.InfoDialogType;

public class InfoDialog extends DialogFragment {

    InfoDialogType dialogType;

    InfoDialog(InfoDialogType dialogType) {
        this.dialogType = dialogType;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String title = "", message = "";
        switch (dialogType) {
            case UNPLAYED:
                title = getString(R.string.unplayed_title);
                message = getString(R.string.unplayed_msg);
                break;
            case WINNING:
                title = getString(R.string.winning_title);
                message = getString(R.string.winning_msg);
                break;
            case CASH_BONUS:
                title = getString(R.string.cash_bonus_title);
                message = getString(R.string.cash_bonus_msg);
        }

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();

    }
}
