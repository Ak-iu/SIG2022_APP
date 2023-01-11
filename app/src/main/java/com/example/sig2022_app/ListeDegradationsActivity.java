package com.example.sig2022_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sig2022_app.modele.Degradation;
import com.example.sig2022_app.tasks.GetAllDegradations_Task;
import com.example.sig2022_app.tasks.GetDegradations_Task;
import com.example.sig2022_app.tasks.RetourGetAllDegradations;
import com.example.sig2022_app.tasks.RetourGetDegradations;

import java.util.List;

public class ListeDegradationsActivity extends AppCompatActivity implements RetourGetDegradations, RetourGetAllDegradations {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_degradations);
        String id_equipement = getIntent().getExtras().getString("id_equipement");
        if (id_equipement.equals("all")) {
            GetAllDegradations_Task task = new GetAllDegradations_Task(this);
            task.execute();
        } else {
            GetDegradations_Task task = new GetDegradations_Task(id_equipement, this);
            task.execute();
        }
    }

    @Override
    public void updateTextDegradations(String texte) {}

    @Override
    public void retourListe(List<Degradation> degradations) {
        ListView listView = findViewById(R.id.liste_degradations);
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.activity_liste_view, R.id.textView, degradations));
        listView.setOnItemClickListener(messageClickedHandler);
    }

    // Create a message handling object as an anonymous class.
    private final AdapterView.OnItemClickListener messageClickedHandler = (parent, v, position, id) -> {
        Log.d("liste degrad "+position,parent.getItemAtPosition(position).toString());
    };

    @Override
    public void retourDegradations(List<Degradation> degradations) {
        retourListe(degradations);
    }
}