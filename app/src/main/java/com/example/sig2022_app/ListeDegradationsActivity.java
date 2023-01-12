package com.example.sig2022_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sig2022_app.modele.Degradation;
import com.example.sig2022_app.tasks.GetAllDegradations_Task;
import com.example.sig2022_app.tasks.GetDegradations_Task;
import com.example.sig2022_app.tasks.PostReparer_Task;
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

    @Override
    public void retourDegradations(List<Degradation> degradations) {
        retourListe(degradations);
    }

    // Lorsqu'un élement de la liste est selectionné on demande une confirmation
    private final AdapterView.OnItemClickListener messageClickedHandler = (parent, v, position, id) -> {
        Degradation deg = (Degradation) parent.getItemAtPosition(position);
        Log.d("liste degrad "+position,deg.toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Réparation");
        builder.setMessage("Confirmer la réparation correspondant à cette nature: \""+deg.getNature()+"\" ?");
        builder.setPositiveButton("Oui", (dialogInterface, i) -> {
            new PostReparer_Task().execute(deg.getId());
        });
        builder.setNegativeButton("Non", (dialogInterface, i) -> Log.d("liste dergad","non"));
        builder.create().show();
    };
}