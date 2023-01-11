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

public class GetDegradations_Task extends AsyncTask<Void, Void, String> {

    private final String id_equipement;
    private final RetourGetDegradations retour;

    public GetDegradations_Task(String id_equipement, RetourGetDegradations retour) {
        this.id_equipement = id_equipement;
        this.retour = retour;
    }

    @Override
    protected String doInBackground(Void... params) {
        HttpURLConnection urlConnection = null;
        try {
            JSONObject getData = new JSONObject();
            getData.put("id_equipement", id_equipement);

            URL obj = new URL(Api.URL_API + "/degradations?idEquipement=" + id_equipement);
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
    protected void onProgressUpdate(Void... params) {
        retour.updateTextDegradations("Recherche des dégradations en cours...");
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONArray jsonArray = new JSONArray(s);
            if (jsonArray.length() == 0) retour.updateTextDegradations("Aucune Degradation.");
            else {
                List<Degradation> degradations = new ArrayList<>();
                List<String> dates = new ArrayList<>();
                List<String> natures = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String id_equipement = (String) jsonObject.get("idEquipement");
                    String date = (String) jsonObject.get("date");
                    String nature = (String) jsonObject.get("nature");
                    String type = (String) jsonObject.get("type");
                    degradations.add(new Degradation(id, id_equipement, date, nature, type));
                    dates.add(date);
                    natures.add(nature);
                }
                StringBuilder degradationString = new StringBuilder("Degradations:\n");
                for (int i = 0; i < dates.size(); i++) {
                    degradationString.append(dates.get(i)).append(" : ").append(natures.get(i)).append("\n");
                }
                retour.updateTextDegradations(degradationString.toString());
                retour.retourListe(degradations);
            }
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}

