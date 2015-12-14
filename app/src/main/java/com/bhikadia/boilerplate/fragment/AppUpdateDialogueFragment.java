package com.bhikadia.boilerplate.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.bhikadia.boilerplate.app.MyApplication;

/**
 * Created by harsh on 15/12/15.
 */
public class AppUpdateDialogueFragment extends DialogFragment {

    private AppUpdateFragmentListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Update available")
                .setMessage("Please update the app.")
                .setCancelable(!MyApplication.getInstance().getPrefManager().getCompulsoryUpdate())
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String playStore = "market://details?id=" + getContext().getPackageName();
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(playStore)));

                        if (mListener != null)
                            mListener.onAppUpdateClicked();
                    }
                });
        if (!MyApplication.getInstance().getPrefManager().getCompulsoryUpdate()){
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    MyApplication.getInstance().getPrefManager().setLastUpdateShowTime(System.currentTimeMillis() / 1000);

                    if (mListener != null)
                        mListener.onAppUpdateCancelClicked();
                }
            });
        }

        return builder.create();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (AppUpdateFragmentListener) activity;
        } catch (ClassCastException e) {
            MyApplication.getInstance().trackException(e);
        }
    }

    public interface AppUpdateFragmentListener {
        public void onAppUpdateClicked();

        public void onAppUpdateCancelClicked();
    }
}
