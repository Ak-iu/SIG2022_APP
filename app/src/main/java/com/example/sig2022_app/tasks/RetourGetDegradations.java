package com.example.sig2022_app.tasks;

import com.example.sig2022_app.modele.Degradation;

import java.util.List;

public interface RetourGetDegradations {
    void updateTextDegradations(String texte);

    void retourListe(List<Degradation> degradations);
}
