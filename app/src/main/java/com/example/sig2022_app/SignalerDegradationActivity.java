package com.example.sig2022_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import com.example.sig2022_app.tasks.PostDegradation_Task;

public class SignalerDegradationActivity extends AppCompatActivity {

    private String objectid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signaler_degradation);
        objectid = getIntent().getStringExtra("objectid");
        Button button = findViewById(R.id.valider);
        button.setOnClickListener(view ->signaler());
    }

    public void signaler() {
        String nature = ((EditText) findViewById(R.id.editText_degradation)).getText().toString();
        if (nature.isEmpty()) {
            Snackbar.make(findViewById(R.id.myCoordinatorLayout),R.string.erreur_nature_vide, BaseTransientBottomBar.LENGTH_SHORT).show();
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.d("Signaler",objectid+","+nature+","+LocalDate.now());
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar c = Calendar.getInstance();
            String date = sdf.format(c.getTime());

            PostDegradation_Task task = new PostDegradation_Task(nature,objectid,date);
            task.execute();
            finish();
        }
    }
}