package com.example.sig2022_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sig2022_app.tasks.GetDegradations_Task;
import com.example.sig2022_app.tasks.RetourGetDegradations;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class DetailsMobilierActivity extends AppCompatActivity implements RetourGetDegradations {

    private String objectid;
    private TextView textViewDegradation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_mobilier);
        fillText(getIntent().getExtras());

        FloatingActionButton fab = findViewById(R.id.fab_degradation_mobilier);
        fab.setOnClickListener(view -> {
            signalerDegradation();
        });

        textViewDegradation = findViewById(R.id.textView_degradation);

        GetDegradations_Task task = new GetDegradations_Task(objectid,this);
        task.execute();
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

    @Override
    public void updateTextDegradations(String texte) {
        Log.d("mobilier task",texte);
        textViewDegradation.setText(texte);
    }
}