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

public class PostReparer_Task extends AsyncTask<Integer, Void, Void> {
    @Override
    protected Void doInBackground(Integer... integers) {
        int id = integers[0];
        HttpURLConnection urlConnection = null;
        try {
            JSONObject postData = new JSONObject();
            postData.put("id", id);
            Log.d("post reparer",""+id);
            URL url = new URL(Api.URL_API + "/reparer");
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
            Log.d("post reparer",postData.toString());
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
