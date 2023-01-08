package com.example.sig2022_app.tasks;

import android.os.AsyncTask;



import org.json.JSONObject;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Date;

public class PostDegradation_Task extends AsyncTask {

    private final String url_api = "http://192.168.1.42:8081";
    //private final String url_api = "http://192.168.1.78:8081";
    private final String nature;
    private final String id_equipement;
    private final String date;

    public PostDegradation_Task(String nature, String id_equipement, String date) {
        this.nature = nature;
        this.id_equipement = id_equipement;
        this.date = date;
    }


    @Override
    protected Object doInBackground(Object[] objects) {
        HttpURLConnection urlConnection = null;
        try {
            JSONObject postData = new JSONObject();
            postData.put("nature", nature);
            postData.put("date", date);
            postData.put("id_equipement", id_equipement);

            URL url = new URL(url_api+"/degradations");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(false);
            urlConnection.setChunkedStreamingMode(0);

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
            writer.write(postData.toString());
            writer.flush();

            int code = urlConnection.getResponseCode();
            if (code !=  201) {
                throw new IOException("Invalid response from server: " + code);
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

}