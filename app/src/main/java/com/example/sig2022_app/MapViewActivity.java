package com.example.sig2022_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MapViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        String coords = getIntent().getExtras().getString("coords");

        WebView myWebView = findViewById(R.id.map_suggestion);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.clearCache(true);
        myWebView.loadUrl("file:///android_asset/map_suggestion.html");
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("map","javascript:load_marker('"+ coords +"');");
                view.loadUrl("javascript:load_marker('"+ coords +"');");
            }
        });
    }
}