package com.example.sig2022_app;

import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;

import androidx.fragment.app.Fragment;

import com.example.sig2022_app.ui.suggestion.SuggestionFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class WebAppInterface {

    private final Fragment fragment;

    public WebAppInterface(Fragment fragment) {
        this.fragment = fragment;
    }

    @JavascriptInterface
    public void sendDetailsPAV(String message) {
        try {
            Intent intent = new Intent(fragment.getContext(),DetailsPavActivity.class);
            JSONObject json_pav = new JSONObject(message);
            intent.putExtra("objectid" , json_pav.getString("objectid"));
            intent.putExtra("modele_colonne" , json_pav.getString("modele_colonne"));
            intent.putExtra("jour_nettoyage" , json_pav.getString("jour_nettoyage"));
            intent.putExtra("opening_hours" , json_pav.getString("opening_hours"));
            intent.putExtra("street_name" , json_pav.getString("street_name"));
            intent.putExtra("city_name" , json_pav.getString("city_name"));
            intent.putExtra("garbage_types", json_pav.getString("garbage_types"));
            fragment.getActivity().startActivity(intent);
        } catch (JSONException e) {
            Log.d("WebAppInterface",e.getMessage());
        }
    }

    @JavascriptInterface
    public void sendDetailsMobilier(String message) {
        try {
            Intent intent = new Intent(fragment.getContext(),DetailsMobilierActivity.class);
            JSONObject json_pav = new JSONObject(message);
            intent.putExtra("objectid" , json_pav.getString("objectid"));
            intent.putExtra("descriptio" , json_pav.getString("descriptio"));
            fragment.getActivity().startActivity(intent);
        } catch (JSONException e) {
            Log.d("WebAppInterface",e.getMessage());
        }
    }

    @JavascriptInterface
    public void updatePos(String message) {
        String[] split = message.split(",");
        double lon = Double.parseDouble(split[0]);
        double lat = Double.parseDouble(split[1]);
        Log.d("lonlat",lon+";"+lat);
        ((SuggestionFragment) fragment).updatePosMap(lon,lat);
    }
}
