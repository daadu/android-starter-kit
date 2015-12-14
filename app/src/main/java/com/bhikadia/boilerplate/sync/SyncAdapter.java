package com.bhikadia.boilerplate.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 * Created by harsh on 14/12/15.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    final static private String TAG = SyncAdapter.class.getSimpleName();
    Context context;
    public static SyncListener syncListener;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        this.context = context;
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, final SyncResult syncResult) {

        // perform sync operations here

        if (syncListener != null)
            syncListener.onSyncCompleted();
    }

    public interface SyncListener {
        public void onSyncCompleted();
    }
}
