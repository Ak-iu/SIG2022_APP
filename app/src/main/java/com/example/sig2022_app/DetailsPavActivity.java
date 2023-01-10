package com.example.sig2022_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sig2022_app.tasks.GetDegradations_Task;
import com.example.sig2022_app.tasks.RetourGetDegradations;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailsPavActivity extends AppCompatActivity implements RetourGetDegradations {

    private String objectid;
    private TextView textViewDegradation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_pav);
        fillText(getIntent().getExtras());

        FloatingActionButton fab = findViewById(R.id.fab_degradation_pav);
        fab.setOnClickListener(view -> {
            signalerDegradation();
        });
    }

    private void fillText(Bundle bundle) {
        String gb = bundle.getString("garbage_types");
        if (!gb.equals("null"))
            ((TextView) findViewById(R.id.garbage_types)).setText(gb);

        String op = bundle.getString("opening_hours");
        if (!op.equals("null"))
            ((TextView) findViewById(R.id.opening_hours)).setText(op);

        String dm = bundle.getString("modele_colonne");
        if (!dm.equals("null"))
            ((TextView) findViewById(R.id.modele_colonne)).setText(dm);

        String jn = bundle.getString("jour_nettoyage");
        if (!jn.equals("null"))
            ((TextView) findViewById(R.id.jour_nettoyage)).setText(jn);

        String sn = bundle.getString("street_name");
        String cn = bundle.getString("city_name");
        String ad = sn + ", " + cn;
        ((TextView) findViewById(R.id.addresse)).setText(ad);

        objectid = bundle.getString("objectid");

        textViewDegradation = findViewById(R.id.textView_degradation);

        GetDegradations_Task task = new GetDegradations_Task(objectid, this);
        task.execute();
    }

    public void signalerDegradation() {
        Intent intent = new Intent(getBaseContext(), SignalerDegradationActivity.class);
        intent.putExtra("objectid", objectid);
        intent.putExtra("type", "pav");
        startActivity(intent);
        finish();
    }

    @Override
    public void updateTextDegradations(String texte) {
        textViewDegradation.setText(texte);
    }
}