package com.example.sig2022_app.modele;

import androidx.annotation.NonNull;

public class Degradation {

    int id;
    String idEquipement;
    String date;
    String nature;
    String type;

    public Degradation(int id, String idEquipement, String date, String nature, String type) {
        this.id = id;
        this.idEquipement = idEquipement;
        this.date = date;
        this.nature = nature;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdEquipement() {
        return idEquipement;
    }

    public void setIdEquipement(String idEquipement) {
        this.idEquipement = idEquipement;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return id + ": {objectid: " + idEquipement + ", nature: '" + nature + "', date: '" + date + "', type: '" + type + "'}";
    }
}
