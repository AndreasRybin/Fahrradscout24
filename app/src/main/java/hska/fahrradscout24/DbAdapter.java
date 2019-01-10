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
    public long createAnzeige (int fahrrad_id, String erstelldatum, String ablaufdatum) {
        ContentValues values = new ContentValues();
        values.put("fahrrad_id",fahrrad_id);
        values.put("erstelldatum",erstelldatum);
        values.put("ablaufdatum",ablaufdatum);
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

    /* Method for fetching record from Database */
    public ArrayList<Advertisement> getAllAdvertisement(){

        String query = "SELECT * FROM " + Anzeige;
        ArrayList<Advertisement> employees = new ArrayList<Advertisement>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                int code = c.getInt(c.getColumnIndex(KEY_CODE));
                String name = c.getString(c.getColumnIndex(KEY_NAME));
                String email = c.getString(c.getColumnIndex(KEY_EMAIL));
                String address = c.getString(c.getColumnIndex(KEY_ADDRESS));

                Advertisement emp = new Advertisement();
                emp.setCode(code);
                emp.setName(name);
                emp.setEmail(email);
                emp.setAddress(address);

                Log.v("DBHelper: ", "Name: " + name);
                Log.v("DBHelper: ", "Code: " + code);
                Log.v("DBHelper: ", "Email: " + email);
                Log.v("DBHelper: ", "Address: " + address);

                employees.add(emp);
            }
    }





}
