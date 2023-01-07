package com.example.sig2022_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailsPavActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_pav);
        fillText(getIntent().getExtras());
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
        String ad = sn+", "+cn;
        ((TextView) findViewById(R.id.addresse)).setText(ad);
    }

}