package com.bhikadia.boilerplate.api;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by harsh on 11/12/15.
 */
public abstract class BaseApi implements Response.Listener<JSONObject>, Response.ErrorListener  {

    private final String TAG = BaseApi.class.getSimpleName();

    interface KEYS{
        final String ERROR = "error";
    }

    ApiListener apiListener;

    public void setApiListener(ApiListener apiListener) {
        this.apiListener = apiListener;
    }

    abstract JSONObject createPayload();
    abstract public void call();
    abstract void parse(JSONObject respJsonObject);

    Map<String , String > prepareHeader(){
        return null;
    }

    void handleError(JSONObject jsonObject){

    }

    void handleVolleyError(VolleyError volleyError){

    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        handleVolleyError(volleyError);
        if (apiListener != null)
            apiListener.onVolleyError(volleyError);
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (isError(jsonObject)){
            handleError(jsonObject);
            if (apiListener != null)
                apiListener.onError("Error!!");
        }else{
            parse(jsonObject);
            if (apiListener != null)
                apiListener.onSuccessful();
        }

    }

    boolean isError(JSONObject jsonObject){
        try{
            return  jsonObject.getBoolean(KEYS.ERROR);
        }catch (JSONException e){
            Log.e(TAG,"JSONException", e );
        }
        return true;
    }

    public interface ApiListener {
        public void onSuccessful(); // called after parsing
        public void onError(String errorMessage); // called if --> "error" : true
        public void onVolleyError(VolleyError error); // called on volley error
        public void onParsedData(Object object); // called inside parse() method with the parsed data in param
    }
}
