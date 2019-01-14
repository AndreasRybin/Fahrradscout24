package hska.fahrradscout24;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by bakir on 07.01.2019.
 */

public class DbAdapter {
    DbHandler dbHandler;
    private SQLiteDatabase database;
    public DbAdapter(Context context)
    {
        dbHandler = new DbHandler(context);
        database = dbHandler.getWritableDatabase();
    }

    public long createBenutzer (String benutzerName, String adresse, String email, String telefon, String passwort) {
        ContentValues values = new ContentValues();
        values.put("benutzername",benutzerName);
        values.put("adresse",adresse);
        values.put("email",email);
        values.put("telefon",telefon);
        values.put("passwort",passwort);
        return database.insert("benutzer",null,values);

    }

    public long createFahrrad (int eigentuermer, String hersteller, double preis, int groesse, String farbe, int art_gangschaltung, int art_bremsen, int art_fahrrad) {
        ContentValues values = new ContentValues();
        values.put("eigentuermer",eigentuermer);
        values.put("hersteller",hersteller);
        values.put("preis",preis);
        values.put("groesse",groesse);
        values.put("farbe",farbe);
        values.put("art_gangschaltung",art_gangschaltung);
        values.put("art_bremsen",art_bremsen);
        values.put("art_fahrrad",art_fahrrad);
        return database.insert("benutzer",null,values);

    }
    public long createAnzeige (int fahrrad_id, String erstelldatum, String ablaufdatum, int preis) {
        ContentValues values = new ContentValues();
        values.put("fahrrad_id", fahrrad_id);
        values.put("erstelldatum", erstelldatum);
        values.put("ablaufdatum", ablaufdatum);
        values.put("preis", preis);
        return database.insert("anzeige",null,values);

    }
    public long createAuktion (int fahrrad, String erstelldatum, String ablaufdatum, int hoechstbieter) {
        ContentValues values = new ContentValues();
        values.put("fahrrad",fahrrad);
        values.put("erstelldatum",erstelldatum);
        values.put("ablaufdatum",ablaufdatum);
        values.put("hoechstbieter",hoechstbieter);
        return database.insert("auktion",null,values);
    }
    public long createArtFahrrad (String art) {
        ContentValues values = new ContentValues();
        values.put("art",art);
        return database.insert("art_fahrrad",null,values);
    }

    public long createArtBremsen (String art) {
        ContentValues values = new ContentValues();
        values.put("art",art);
        return database.insert("art_bremsen",null,values);
    }
    public long createArtGangschaltung (String art) {
        ContentValues values = new ContentValues();
        values.put("art",art);
        return database.insert("art_gangschaltung",null,values);
    }
    public int updateBenutzer (int id, String benutzerName, String adresse, String email, String telefon, String passwort) {
        ContentValues values = new ContentValues();
        values.put("benutzername",benutzerName);
        values.put("adresse",adresse);
        values.put("email",email);
        values.put("telefon",telefon);
        values.put("passwort",passwort);
        return database.update("Benutzer",values,"benutzer_id="+id,null);
    }

    public int createFahrrad (int id, int eigentuermer, String hersteller, double preis, int groesse, String farbe, int art_gangschaltung, int art_bremsen, int art_fahrrad) {
        ContentValues values = new ContentValues();
        values.put("eigentuermer",eigentuermer);
        values.put("hersteller",hersteller);
        values.put("preis",preis);
        values.put("groesse",groesse);
        values.put("farbe",farbe);
        values.put("art_gangschaltung",art_gangschaltung);
        values.put("art_bremsen",art_bremsen);
        values.put("art_fahrrad",art_fahrrad);
        return database.update("Fahrrad",values,"fahrrad_id="+id,null);
    }
    public int updateAnzeige (int id, int fahrrad_id, String erstelldatum, String ablaufdatum) {
        ContentValues values = new ContentValues();
        values.put("fahrrad_id",fahrrad_id);
        values.put("erstelldatum",erstelldatum);
        values.put("ablaufdatum",ablaufdatum);
        return database.update("Anzeige",values,"anzeige_id="+id,null);
    }
    public int updateAuktion (int id, int fahrrad, String erstelldatum, String ablaufdatum, int hoechstbieter) {
        ContentValues values = new ContentValues();
        values.put("fahrrad",fahrrad);
        values.put("erstelldatum",erstelldatum);
        values.put("ablaufdatum",ablaufdatum);
        values.put("hoechstbieter",hoechstbieter);
        return database.update("Auktion",values,"auktion_id="+id,null);
    }

    public int updateArtFahrrad (int id, String art) {
        ContentValues values = new ContentValues();
        values.put("art",art);
        return database.update("Art_fahrrad",values,"art_fahrrad_id="+id,null);
    }

    public int updateArtBremsen (int id, String art) {
        ContentValues values = new ContentValues();
        values.put("art",art);
        return database.update("Art_bremsen",values,"art_bremsen_id="+id,null);
    }

    public int updateArtGangschaltung (int id, String art) {
        ContentValues values = new ContentValues();
        values.put("art",art);
        return database.update("Art_gangschaltung",values,"art_gangschaltung_id="+id,null);
    }

    public String getPassword(String benutzername){

        String passwort = null;
        Cursor cursor = database.query("Benutzer",new String[]{"passwort"} , "benutzername=?", new String[]{benutzername}, null, null, null, null   );
        if (cursor != null) {
            cursor.moveToFirst();
            passwort = cursor.getString(0);
            return passwort;
        }
        return passwort;

    }
    public Benutzer getUserByBenutzername(String benutzername){
        String query = "SELECT * FROM " + "benutzer" + "Where benutzername = '" + benutzername+ "'";
        SQLiteDatabase database = dbHandler.getReadableDatabase();
        Cursor cursor = database.rawQuery(query,null);
        if(cursor!=null) {
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            String adresse = cursor.getString(2);
            String email = cursor.getString(3);
            String telefon = cursor.getString(4);
            String passwort = cursor.getString(5);
            Benutzer benutzer = new Benutzer(id,benutzername,passwort,email,adresse);
            return benutzer;
        }

        return null;
    }

    /* Method for fetching record from Database */
    public ArrayList<Advertisement> getAllAdvertisement() {

        String query = "SELECT * FROM " + "Anzeige";
        ArrayList<Advertisement> advertisement = new ArrayList<>();
        SQLiteDatabase database = dbHandler.getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                int anzeigeId = c.getInt(c.getColumnIndex("anzeige_id"));
                int fahrradId = c.getInt(c.getColumnIndex("fahrrad_id"));
                String erstelldatum = c.getString(c.getColumnIndex("erstelldatum"));
                String ablaufdatum = c.getString(c.getColumnIndex("ablaufdatum"));
                int preis = c.getInt(c.getColumnIndex("preis"));

                Advertisement emp = new Advertisement(anzeigeId, fahrradId, erstelldatum, ablaufdatum, preis);

                advertisement.add(emp);
            }
        }
        return advertisement;
    }





}
