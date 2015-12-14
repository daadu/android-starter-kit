package com.bhikadia.boilerplate.activity;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bhikadia.boilerplate.R;
import com.bhikadia.boilerplate.api.BaseApi;
import com.bhikadia.boilerplate.api.RegisterApi;

public class LoginActivity extends BaseActivity implements BaseApi.ApiListener {

    ProgressDialog progressDialog;

    @Override
    protected void onResume() {
        verifyIfLoggedOut();
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void register(View view){
        progressDialog = ProgressDialog.show(LoginActivity.this,  "Please wait ...", "Registering ...", true );
        progressDialog.setCancelable(false);

        RegisterApi registerApi = new RegisterApi();
        registerApi.setApiListener(this);
        registerApi.call();
    }

    @Override
    public void onSuccessful() {

    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(getApplicationContext(), "error : " + errorMessage, Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
    }

    @Override
    public void onVolleyError(VolleyError error) {
        Toast.makeText(getApplicationContext(), "Volley error", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
    }

    @Override
    public void onParsedData(Object object) {
        Account account = (Account) object;
        if(account != null){
            progressDialog.dismiss();
            verifyIfLoggedOut();
        }
    }
}
