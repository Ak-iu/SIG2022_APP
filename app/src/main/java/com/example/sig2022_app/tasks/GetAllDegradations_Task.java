package com.example.sig2022_app.tasks;

import android.os.AsyncTask;

import com.example.sig2022_app.modele.Degradation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetAllDegradations_Task extends AsyncTask<Void, Void, String> {

    private final RetourGetAllDegradations retour;

    public GetAllDegradations_Task(RetourGetAllDegradations retour) {
        this.retour = retour;
    }

    @Override
    protected String doInBackground(Void... params) {
        HttpURLConnection urlConnection = null;
        try {
            URL obj = new URL(Api.URL_API + "/all_degradations");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
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
                System.out.println("GET request did not work.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        List<Degradation> degradations = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String id_equipement = (String) jsonObject.get("idEquipement");
                String date = (String) jsonObject.get("date");
                String nature = (String) jsonObject.get("nature");
                String type = (String) jsonObject.get("type");
                Degradation degradation = new Degradation(id, id_equipement, date, nature, type);
                degradations.add(degradation);
            }
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }
        retour.retourDegradations(degradations);
    }
}
