package com.example.sig2022_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sig2022_app.modele.Suggestion;
import com.example.sig2022_app.tasks.GetSuggestions_Task;
import com.example.sig2022_app.tasks.RetourGetSuggestions;

import java.util.List;

public class ListeSuggestionsActivity extends AppCompatActivity implements RetourGetSuggestions {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_suggestions);

        GetSuggestions_Task task = new GetSuggestions_Task(this);
        task.execute();
    }

    @Override
    public void retourSuggestions(List<Suggestion> liste) {
        Log.d("suggestion",liste.toString());
        ListView listView = findViewById(R.id.liste_suggestions);
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.activity_liste_view, R.id.textView, liste));
        listView.setOnItemClickListener(messageClickedHandler);
    }

    private final AdapterView.OnItemClickListener messageClickedHandler = (parent, v, position, id) -> {
        Suggestion suggestion = (Suggestion) parent.getItemAtPosition(position);
        String msg = suggestion.getCoords_x()+";"+suggestion.getCoords_y();
        Log.d("suggestion click",msg);
        Intent intent = new Intent(this,MapViewActivity.class);
        intent.putExtra("coords",msg);
        startActivity(intent);
    };

}