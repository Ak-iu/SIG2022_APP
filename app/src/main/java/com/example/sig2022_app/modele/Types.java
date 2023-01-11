package com.example.sig2022_app.modele;

public abstract class Types {
    public static String getCode(String nom) {
        String type = "";
        switch (nom) {
            case "Tête d'arrosage":
                type = "1S117";
                break;
            case "Banc public":
                type = "3S111";
                break;
            case "Grille carrée arbre":
                type = "3S098";
                break;
            case "Bac à fleurs rectangulaire":
                type = "3S113";
                break;
            case "Jardinière ronde":
                type = "2S112";
                break;
            case "Jeu d'enfant rectangulaire":
                type = "3S115";
                break;
            case "Robinet ou vanne d'arrosage":
                type = "1S119";
                break;
            case "Armoire d'arrosage":
                type = "3S118";
                break;
            case "Jeu d'enfant rond":
                type = "2S114";
                break;
            case "Statue, Monument":
                type = "1S110";
                break;
            case "Grille ronde arbre":
                type = "2S098";
                break;
            case "Jardinière suspendue":
                type = "1S105";
                break;
            case "Colonne végétale":
                type = "3S096";
                break;
            case "Jardinière sur poteau":
                type = "1S106";
                break;
            case "Manège":
                type = "2S116";
                break;
            case "Portique pour végétation":
                type = "2S097";
                break;
            default:
                type = "";
        }
        return type;
    }

    public static String getNom(String code) {
        String nom = "";
        switch (code) {
            case "1S117":
                nom = "Tête d'arrosage";
                break;
            case "3S111":
                nom = "Banc public";
                break;
            case "3S098":
                nom = "Grille carrée arbre";
                break;
            case "3S113":
                nom = "Bac à fleurs rectangulaire";
                break;
            case "2S112":
                nom = "Jardinière ronde";
                break;
            case "3S115":
                nom = "Jeu d'enfant rectangulaire";
                break;
            case "1S119":
                nom = "Robinet ou vanne d'arrosage";
                break;
            case "3S118":
                nom = "Armoire d'arrosage";
                break;
            case "2S114":
                nom = "Jeu d'enfant rond";
                break;
            case "1S110":
                nom = "Statue, Monument";
                break;
            case "2S098":
                nom = "Grille ronde arbre";
                break;
            case "1S105":
                nom = "Jardinière suspendue";
                break;
            case "3S096":
                nom = "Colonne végétale";
                break;
            case "1S106":
                nom = "Jardinière sur poteau";
                break;
            case "2S116":
                nom = "Manège";
                break;
            case "2S097":
                nom = "Portique pour végétation";
                break;
            default:
                nom = "";
        }
        return nom;
    }
}
