package com.example.sig2022_app.ui.suggestion;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.sig2022_app.R;
import com.example.sig2022_app.WebAppInterface;
import com.example.sig2022_app.databinding.FragmentSuggestionBinding;
import com.example.sig2022_app.modele.Types;
import com.example.sig2022_app.tasks.PostSuggestion_Task;

public class SuggestionFragment extends Fragment {

    private FragmentSuggestionBinding binding;

    private double lon;
    private double lat;
    private boolean modifie;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSuggestionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Spinner spinner = root.findViewById(R.id.spinner_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(root.getContext(), R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        WebView webView = root.findViewById(R.id.map_pick_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.clearCache(true);
        webView.loadUrl("file:///android_asset/map_pick_view.html");

        lon = 0;
        lat = 0;
        modifie = false;

        root.findViewById(R.id.radioButton_pos_actuelle).setOnClickListener(this::onPosActuelleClick);
        root.findViewById(R.id.radioButton_pos_manuelle).setOnClickListener(this::onPosManuelleClick);
        root.findViewById(R.id.button_valider).setOnClickListener(this::valider);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onPosActuelleClick(View view) {
        view.getRootView().findViewById(R.id.map_pick_webview).setVisibility(View.INVISIBLE);

        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(view.getContext(), "Permission d'accès au GPS refusée", Toast.LENGTH_LONG).show();
            view.findViewById(R.id.radioButton_pos_actuelle).setActivated(false);
            return;
        }

        LocationManager locationManager = (LocationManager) binding.getRoot().getContext().getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Toast.makeText(view.getContext(), "Le GPS n'est pas activé", Toast.LENGTH_LONG).show();
            view.findViewById(R.id.radioButton_pos_actuelle).setActivated(false);
            return;
        }
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        lon = lastKnownLocation.getLongitude();
        lat = lastKnownLocation.getLatitude();
        modifie = true;
    }

    public void onPosManuelleClick(View view) {
        view.getRootView().findViewById(R.id.map_pick_webview).setVisibility(View.VISIBLE);
    }

    public void updatePosMap(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
        modifie = true;
    }

    public void valider(View view) {
        View root = view.getRootView();
        if (!modifie) {
            Toast.makeText(view.getContext(), "Veuillez sélectionner une position", Toast.LENGTH_LONG).show();
            return;
        }
        String spinner_type = ((Spinner) root.findViewById(R.id.spinner_type)).getSelectedItem().toString();
        String type = Types.getCode(spinner_type);
        if (type.isEmpty()) {
            Toast.makeText(view.getContext(), "Veuillez sélectionner un type d'équipements", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(view.getContext(), "Suggestion enregistrée", Toast.LENGTH_LONG).show();
        PostSuggestion_Task task = new PostSuggestion_Task(type, lon, lat);
        task.execute();
    }
}