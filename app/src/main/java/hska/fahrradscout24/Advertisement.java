package hska.fahrradscout24;

public class Advertisement {

    int id, fahrrad_id, preis;
    String erstelldatum, ablaufdatum;

    public Advertisement() {
    }

    public Advertisement(int id, int fahrrad_id, String erstelldatum, String ablaufdatum, int preis) {
        this.id = id;
        this.fahrrad_id = fahrrad_id;
        this.preis = preis;
        this.erstelldatum = erstelldatum;
        this.ablaufdatum = ablaufdatum;
    }

    public int getId() {
        return id;
    }

    public int getFahrrad_id() {
        return fahrrad_id;
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

    public void setFahrrad_id(int fahrrad_id) {
        this.fahrrad_id = fahrrad_id;
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
}