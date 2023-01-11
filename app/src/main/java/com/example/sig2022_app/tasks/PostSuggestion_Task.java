package com.example.sig2022_app.tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PostSuggestion_Task extends AsyncTask<Void,Void,Void> {


    private final String type;
    private final double coords_x;
    private final double coords_y;

    public PostSuggestion_Task(String type, double coords_x, double coords_y) {
        this.type = type;
        this.coords_x = coords_x;
        this.coords_y = coords_y;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpURLConnection urlConnection = null;
        try {
            JSONObject postData = new JSONObject();
            postData.put("type", type);
            postData.put("coords_x", coords_x);
            postData.put("coords_y", coords_y);

            URL url = new URL(Api.URL_API + "/suggestions");
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
            if (code != 201) {
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