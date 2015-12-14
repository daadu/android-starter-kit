package com.bhikadia.boilerplate.api;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bhikadia.boilerplate.app.EndPoints;
import com.bhikadia.boilerplate.app.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by harsh on 15/12/15.
 */
public class UpdateApi extends BaseApi {
    private String TAG = ListApi.class.getSimpleName();

    private interface KEYS {
        final String UPDATE = "update";
        final String COMPULSORY = "compulsory";
    }

    public UpdateApi() {
    }

    @Override
    JSONObject createPayload() {
        return null;
    }

    @Override
    public void call() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                EndPoints.UPDATE_API,
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
            boolean shouldUpdate = respJsonObject.getBoolean(KEYS.UPDATE);
            boolean compulsoryUpdate = respJsonObject.getBoolean(KEYS.COMPULSORY);

            MyApplication.getInstance().getPrefManager().setShouldUpdate(shouldUpdate);
            MyApplication.getInstance().getPrefManager().setCompulsoryUpdate(compulsoryUpdate);
            MyApplication.getInstance().getPrefManager().setLastUpdateCheckTime(System.currentTimeMillis() / 1000);
        }catch (JSONException e){
            Log.e(TAG, "JSON Exception", e);
        }

        if (apiListener != null)
            apiListener.onParsedData(null);
    }
}
