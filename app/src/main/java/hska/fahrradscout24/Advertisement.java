package hska.fahrradscout24;

import android.graphics.Bitmap;

public class Advertisement {

    private int id, preis;
    private String erstelldatum, ablaufdatum;
    private Bitmap fahrradbild;

    public Advertisement(){

    }



    public Advertisement(int id, int preis, String erstelldatum, String ablaufdatum, Bitmap fahrradbild) {
        this.id = id;
        this.preis = preis;
        this.erstelldatum = erstelldatum;
        this.ablaufdatum = ablaufdatum;
        this.fahrradbild = fahrradbild;
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
}