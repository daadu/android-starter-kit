package com.bhikadia.boilerplate.api;

import android.accounts.Account;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bhikadia.boilerplate.app.EndPoints;
import com.bhikadia.boilerplate.app.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by harsh on 11/12/15.
 */
public class RegisterApi extends BaseApi {
    private String TAG = RegisterApi.class.getSimpleName();

    private interface KEYS{
        final String TOKEN = "token";
    }

    public RegisterApi() {
    }

    @Override
    JSONObject createPayload() {
        return null;
    }

    @Override
    public void call() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                EndPoints.REGISTER_API,
                createPayload(),
                this,
                this
        );

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest, TAG);
    }

    @Override
    void parse(JSONObject respJsonObject) {
        try{

            String token = respJsonObject.getString(KEYS.TOKEN);
            if (!MyApplication.getInstance().getAccountUtil().hasAccount()){
                Account account = MyApplication.getInstance().getAccountUtil().createAccount(token);

                if (apiListener != null)
                    apiListener.onParsedData(account);
            }

        }catch (JSONException e){
            Log.e(TAG, "JSON Exception", e);
        }
    }
}
