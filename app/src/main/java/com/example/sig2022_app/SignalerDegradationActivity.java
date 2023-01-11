package com.example.sig2022_app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sig2022_app.tasks.PostDegradation_Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignalerDegradationActivity extends AppCompatActivity {

    private String objectid;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signaler_degradation);
        objectid = getIntent().getStringExtra("objectid");
        type = getIntent().getStringExtra("type");
        Button button = findViewById(R.id.valider);
        button.setOnClickListener(view -> signaler());
    }

    public void signaler() {
        String nature = ((EditText) findViewById(R.id.editText_degradation)).getText().toString();
        if (nature.isEmpty()) {
            Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.erreur_nature_vide, BaseTransientBottomBar.LENGTH_SHORT).show();
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar c = Calendar.getInstance();
            String date = sdf.format(c.getTime());

            PostDegradation_Task task = new PostDegradation_Task(nature, objectid, date, type);
            task.execute();

            finish();
        }
    }
}