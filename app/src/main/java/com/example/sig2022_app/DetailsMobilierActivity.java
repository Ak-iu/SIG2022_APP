package com.example.sig2022_app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsMobilierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_mobilier);
        fillText(getIntent().getExtras());
    }

    private void fillText(Bundle bundle) {
        String ds = bundle.getString("descriptio");
        if (!ds.equals("null"))
            ((TextView) findViewById(R.id.description)).setText(ds);

    }

}