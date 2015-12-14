package com.bhikadia.boilerplate.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import com.bhikadia.boilerplate.R;
import com.bhikadia.boilerplate.app.MyApplication;
import com.google.android.gms.auth.GoogleAuthUtil;

import org.json.JSONArray;

/**
 * Created by harsh on 11/12/15.
 */
public class AccountUtil {

    private String TAG = AccountUtil.class.getSimpleName();


    private Context context;

    public AccountUtil(Context context) {
        super();
        this.context = context;
    }

    public boolean hasAccount() {
        return getAccount() != null;
    }

    public Account getAccount() {
        Account retAccount[] = getSpecificAccounts(context.getString(R.string.account_type));
        if (retAccount != null) {
            return retAccount[0];
        }
        return null;
    }

    public Account[] getGoogleAccounts() {
        return getSpecificAccounts(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
    }

    public JSONArray getGoogleAccountsJsonArray() {
        Account[] accounts = getGoogleAccounts();
        JSONArray accountsArray = new JSONArray();
        if (accounts != null) {
            for (Account account : accounts) {
                accountsArray.put(account.name);
            }
        }
        return accountsArray;
    }

    public String[] getGoogleEmails() {
        Account[] accounts = getGoogleAccounts();
        if (accounts != null && accounts.length > 0) {
            String[] emails = new String[accounts.length];
            for (int i = 0; i < accounts.length; i++) {
                emails[i] = accounts[i].name;
            }
            return emails;
        }
        return null;
    }

    public Account[] getSpecificAccounts(String accountType) {
        Account accounts[] = AccountManager.get(context)
                .getAccountsByType(accountType);
        if (accounts.length != 0) {
            return accounts;
        } else {
            return null;
        }
    }

    public Account createAccount(String token) {
        Account account = new Account(context.getString(R.string.app_name), context.getString(R.string.account_type));

        AccountManager accountManager = AccountManager.get(context);
        accountManager.addAccountExplicitly(account, "", null);
        accountManager.setAuthToken(account, context.getString(R.string.auth_token_type), token);

        MyApplication.getInstance().getSyncUtil().setSyncSettings();
        return account;
    }

    public void deleteAccount() {
        Account account = getAccount();
        if (account != null) {
            AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
            accountManager.removeAccount(account, null, null , null);
        }
    }

    public boolean verifyGoogleAccounts() {
        Account[] accounts = getGoogleAccounts();
        String[] emails = getGoogleEmails();

        return accounts != null && accounts.length > 0 && emails != null && emails.length > 0;
    }

    public class NoGoogleAccountException extends Exception {
        public NoGoogleAccountException(String message) {
            super(message);
        }
    }
}
