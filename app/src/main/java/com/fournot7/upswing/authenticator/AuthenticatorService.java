package com.fournot7.upswing.authenticator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by harsh on 11/12/15.
 */
public class AuthenticatorService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return new AccountAuthenticator(this).getIBinder();
    }
}
