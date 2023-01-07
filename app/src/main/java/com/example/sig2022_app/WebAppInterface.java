package com.example.sig2022_app;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;

import org.json.JSONException;
import org.json.JSONObject;

public class WebAppInterface {

    private final Context context;

    public WebAppInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void sendDetailsPAV(String message) {
        try {
            Intent intent = new Intent(context,DetailsPavActivity.class);
            JSONObject json_pav = new JSONObject(message);
            intent.putExtra("modele_colonne" , json_pav.getString("modele_colonne"));
            intent.putExtra("jour_nettoyage" , json_pav.getString("jour_nettoyage"));
            intent.putExtra("opening_hours" , json_pav.getString("opening_hours"));
            intent.putExtra("street_name" , json_pav.getString("street_name"));
            intent.putExtra("city_name" , json_pav.getString("city_name"));
            intent.putExtra("garbage_types", json_pav.getString("garbage_types"));
            context.startActivity(intent);
        } catch (JSONException e) {
            Log.d("WebAppInterface",e.getMessage());
        }
    }

    @JavascriptInterface
    public void sendDetailsMobilier(String message) {
        try {
            Intent intent = new Intent(context,DetailsMobilierActivity.class);
            JSONObject json_pav = new JSONObject(message);
            intent.putExtra("descriptio" , json_pav.getString("descriptio"));
            context.startActivity(intent);
        } catch (JSONException e) {
            Log.d("WebAppInterface",e.getMessage());
        }
    }
}
