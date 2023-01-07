package com.example.sig2022_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailsMobilierActivity extends AppCompatActivity {

    private String objectid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_mobilier);
        fillText(getIntent().getExtras());

        FloatingActionButton fab = findViewById(R.id.fab_degradation_mobilier);
        fab.setOnClickListener(view -> {
            signalerDegradation();
        });

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

}