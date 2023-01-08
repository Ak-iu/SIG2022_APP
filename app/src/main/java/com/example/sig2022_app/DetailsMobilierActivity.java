package com.example.sig2022_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DetailsMobilierActivity extends AppCompatActivity {

    private String objectid;
    private TextView textViewDegradation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_mobilier);
        fillText(getIntent().getExtras());

        FloatingActionButton fab = findViewById(R.id.fab_degradation_mobilier);
        fab.setOnClickListener(view -> {
            signalerDegradation();
        });


        textViewDegradation = findViewById(R.id.textView_degradation);

        GetDegradations_Task task = new GetDegradations_Task(objectid);
        task.execute();

    }

    private void fillText(Bundle bundle) {
        String ds = bundle.getString("descriptio");
        if (!ds.equals("null"))
            ((TextView) findViewById(R.id.description)).setText(ds);
        objectid = bundle.getString("objectid");
    }

    public void signalerDegradation() {
        Intent intent = new Intent(getBaseContext(),SignalerDegradationActivity.class);
        intent.putExtra("objectid",objectid);
        startActivity(intent);
        finish();
    }



    public class GetDegradations_Task extends AsyncTask<Void,Void,String> {


        private final String url_api = "http://192.168.1.42:8081";
        private final String nature;
        private final String id_equipement;
        private final String date;


        public GetDegradations_Task(String id_equipement) {
            this.nature = null;
            this.id_equipement = id_equipement;
            this.date = null;
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
            textViewDegradation.setText("Recherche des dégradations en cours...");
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("json",s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                if (jsonArray.length() == 0) textViewDegradation.setText("Aucune Degradation.");
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
                    textViewDegradation.setText(degradationString);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}