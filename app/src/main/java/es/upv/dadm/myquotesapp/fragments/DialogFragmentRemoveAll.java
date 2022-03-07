package es.upv.dadm.myquotesapp.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import es.upv.dadm.myquotesapp.R;
import es.upv.dadm.myquotesapp.adapters.FavouriteQuotesAdapter;
import es.upv.dadm.myquotesapp.databases.QuotationsDatabase;

public class DialogFragmentRemoveAll extends DialogFragment {
    public DialogFragmentRemoveAll() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
        builder.setIcon(android.R.drawable.stat_sys_warning);
        builder.setMessage(R.string.favourite_dialog_message_all);
        builder.setNegativeButton(android.R.string.no, null);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                getParentFragmentManager().setFragmentResult("remove_all", new Bundle());
            }
        });
        return builder.create();
    }
}
