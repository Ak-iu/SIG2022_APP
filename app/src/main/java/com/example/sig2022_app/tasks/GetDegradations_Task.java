package com.example.sig2022_app.tasks;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetDegradations_Task extends AsyncTask<Void,Void,String> {


    //private final String url_api = "http://192.168.1.42:8081";
    private final String url_api = "http://192.168.1.78:8081";
    private final String id_equipement;

    private final RetourGetDegradations retour;

    public GetDegradations_Task(String id_equipement, RetourGetDegradations retour) {
        this.id_equipement = id_equipement;
        this.retour = retour;
    }

    @Override
    protected String doInBackground(Void...params) {
        HttpURLConnection urlConnection = null;
        try {
            JSONObject getData = new JSONObject();
            getData.put("id_equipement", id_equipement);

            URL obj = new URL(url_api+"/degradations?idEquipement="+id_equipement);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

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
    protected void onProgressUpdate(Void...params){
        retour.updateTextDegradations("Recherche des dégradations en cours...");
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("json",s);
        try {
            JSONArray jsonArray = new JSONArray(s);
            if (jsonArray.length() == 0) retour.updateTextDegradations("Aucune Degradation.");
            else{
                List<String> dates = new ArrayList<>();
                List<String> natures = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    dates.add(jsonObject.get("date").toString());
                    natures.add(jsonObject.get("nature").toString());
                }
                String degradationString = "Degradations:\n";
                for (int i = 0; i < dates.size(); i++) {
                    degradationString+= dates.get(i) + " : " + natures.get(i)+"\n";
                }
                Log.d("task",degradationString);
                retour.updateTextDegradations(degradationString);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

