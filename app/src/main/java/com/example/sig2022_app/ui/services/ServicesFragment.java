package com.example.sig2022_app.ui.services;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sig2022_app.ListeDegradationsActivity;
import com.example.sig2022_app.ListeSuggestionsActivity;
import com.example.sig2022_app.R;
import com.example.sig2022_app.WebAppInterface;
import com.example.sig2022_app.databinding.FragmentServicesBinding;
import com.example.sig2022_app.modele.Degradation;
import com.example.sig2022_app.tasks.GetAllDegradations_Task;
import com.example.sig2022_app.tasks.RetourGetAllDegradations;

import java.util.List;

public class ServicesFragment extends Fragment implements RetourGetAllDegradations {

    private FragmentServicesBinding binding;
    private WebView webView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentServicesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        webView = root.findViewById(R.id.map_degrad_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.clearCache(true);

        GetAllDegradations_Task task = new GetAllDegradations_Task(this);
        task.execute();

        root.findViewById(R.id.button_liste_degradations).setOnClickListener(l -> selectDegradation("all"));
        root.findViewById(R.id.button_liste_suggestions).setOnClickListener(l -> goListeSuggestions());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void retourDegradations(List<Degradation> degradations) {
        StringBuilder sb = new StringBuilder("{");
        for (Degradation deg : degradations) {
            sb.append(deg.toJsObject()).append(", ");
        }
        sb.append("}");

        webView.loadUrl("file:///android_asset/map_view_degradations.html");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:load_liste(" + sb + ");");
            }
        });
    }

    public void selectDegradation(String objectid) {
        Intent intent = new Intent(getContext(), ListeDegradationsActivity.class);
        intent.putExtra("id_equipement",objectid);
        Log.d("selectDegradation",objectid);
        startActivity(intent);
    }

    public void goListeSuggestions() {
        Intent intent = new Intent(getContext(), ListeSuggestionsActivity.class);
        startActivity(intent);
    }

}