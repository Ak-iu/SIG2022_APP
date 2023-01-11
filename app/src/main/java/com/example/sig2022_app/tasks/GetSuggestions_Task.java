package com.example.sig2022_app.tasks;

import android.os.AsyncTask;

import com.example.sig2022_app.modele.Suggestion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetSuggestions_Task extends AsyncTask<Void, Void, String> {

    private final RetourGetSuggestions retour;

    public GetSuggestions_Task(RetourGetSuggestions retour) {
        this.retour = retour;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            URL obj = new URL(Api.URL_API + "/suggestions");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            } else {
                throw new IOException("Invalid response from server: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        List<Suggestion> suggestions = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("idSuggestion");
                String type = (String) jsonObject.get("type");
                double coordonnes_x = jsonObject.getDouble("coordonnes_x");
                double coordonnes_y = jsonObject.getDouble("coordonnes_y");
                Suggestion suggestion = new Suggestion(id,type,coordonnes_x,coordonnes_y);
                suggestions.add(suggestion);
            }
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }
        retour.retourSuggestions(suggestions);
    }

}
