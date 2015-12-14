package com.bhikadia.boilerplate.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by harsh on 14/12/15.
 */
public class SyncAdapterService extends Service {
    private static final Object sSyncAdpaterLock = new Object();
    private static SyncAdapter sSyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (sSyncAdpaterLock){
            if(sSyncAdapter == null){
                sSyncAdapter = new SyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
