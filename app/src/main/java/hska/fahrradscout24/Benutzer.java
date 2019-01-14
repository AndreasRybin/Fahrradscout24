package hska.fahrradscout24;

/**
 * Created by bakir on 10.01.2019.
 */

public class Benutzer {
    private int benutzer_id;
    private String benutzername;
    private String passwort;
    private String email;
    private String adresse;

    public Benutzer(int benutzer_id, String benutzername, String passwort, String email, String adresse) {
        this.benutzer_id = benutzer_id;
        this.benutzername = benutzername;
        this.passwort = passwort;
        this.email = email;
        this.adresse = adresse;
    }

    public int getBenutzer_id() {
        return benutzer_id;
    }

    public void setBenutzer_id(int benutzer_id) {
        this.benutzer_id = benutzer_id;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
