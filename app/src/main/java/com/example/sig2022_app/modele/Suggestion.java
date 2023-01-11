package com.example.sig2022_app.modele;

import androidx.annotation.NonNull;

public class Suggestion {
    private int idSuggestion;
    private String type;
    private double coords_x;
    private double coords_y;

    public Suggestion(int idSuggestion, String type, double coords_x, double coords_y) {
        this.idSuggestion = idSuggestion;
        this.type = type;
        this.coords_x = coords_x;
        this.coords_y = coords_y;
    }

    public int getIdSuggestion() {
        return idSuggestion;
    }

    public String getType() {
        return type;
    }

    public double getCoords_x() {
        return coords_x;
    }

    public double getCoords_y() {
        return coords_y;
    }

    @NonNull
    @Override
    public String toString() {
        return "ID: "+idSuggestion+", Type: "+Types.getNom(type)+"\ncoordonées: ["+coords_x+","+coords_y+"]";
    }
}
