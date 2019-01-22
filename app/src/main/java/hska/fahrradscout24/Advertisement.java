package hska.fahrradscout24;

import android.graphics.Bitmap;

public class Advertisement {

    private int id;
    private int preis;
    private int benutzer_id;
    private String erstelldatum, ablaufdatum;
    private Bitmap fahrradbild;
    private String farbe;

    public Advertisement(){

    }



    public Advertisement(int id, int preis, String erstelldatum, String ablaufdatum, Bitmap fahrradbild, int benutzer_id, String farbe, int groesse) {
        this.id = id;
        this.preis = preis;
        this.erstelldatum = erstelldatum;
        this.ablaufdatum = ablaufdatum;
        this.fahrradbild = fahrradbild;
        this.benutzer_id = benutzer_id;
        this.farbe = farbe;
        this.groesse = groesse;
    }
    public Advertisement(int id, int preis, String erstelldatum, String ablaufdatum, int benutzer_id, String farbe, int groesse) {
        this.id = id;
        this.preis = preis;
        this.erstelldatum = erstelldatum;
        this.ablaufdatum = ablaufdatum;
        this.benutzer_id = benutzer_id;
        this.farbe = farbe;
        this.groesse = groesse;
    }

    public int getId() {
        return id;
    }


    public int getPreis() {
        return preis;
    }

    public String getErstelldatum() {
        return erstelldatum;
    }

    public String getAblaufdatum() {
        return ablaufdatum;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPreis(int preis) {
        this.preis = preis;
    }

    public void setErstelldatum(String erstelldatum) {
        this.erstelldatum = erstelldatum;
    }

    public void setAblaufdatum(String ablaufdatum) {
        this.ablaufdatum = ablaufdatum;
    }

    public Bitmap getFahrradbild() {
        return fahrradbild;
    }

    public void setFahrradbild(Bitmap fahrradbild) {
        this.fahrradbild = fahrradbild;
    }

    public int getBenutzer_id() {
        return benutzer_id;
    }

    public void setBenutzer_id(int benutzer_id) {
        this.benutzer_id = benutzer_id;
    }
    public String getFarbe() {
        return farbe;
    }

    public void setFarbe(String farbe) {
        this.farbe = farbe;
    }

    public int getGroesse() {
        return groesse;
    }

    public void setGroesse(int groesse) {
        this.groesse = groesse;
    }

    private int groesse;



}