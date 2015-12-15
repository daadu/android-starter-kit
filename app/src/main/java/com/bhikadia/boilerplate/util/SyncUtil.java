package com.bhikadia.boilerplate.util;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;

import com.bhikadia.boilerplate.R;
import com.bhikadia.boilerplate.app.MyApplication;

/**
 * Created by harsh on 14/12/15.
 */
public class SyncUtil {

    private String TAG = SyncUtil.class.getSimpleName();


    private Context context;

    public SyncUtil(Context context) {
        super();
        this.context = context;
    }

    public void setSyncSettings() {
        Account account = MyApplication.getInstance().getAccountUtil().getAccount();
        ContentResolver.setSyncAutomatically(account, context.getString(R.string.authority), true);
    }

    public void syncImmediately(){
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL , true);

        Account grapprAccount = MyApplication.getInstance().getAccountUtil().getAccount();

        if(grapprAccount != null){
            ContentResolver.requestSync(grapprAccount, context.getString(R.string.authority), bundle);
        }
    }
}
