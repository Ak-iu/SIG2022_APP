package com.example.sig2022_app;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class WebAppInterface {
    @JavascriptInterface
    public void sendDetailsPAV(String message) {
        Log.d("test",message);
    }

    @JavascriptInterface
    public void sendDetailsMobilier(String message) {
        Log.d("test",message);
    }
}
