package hska.fahrradscout24;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by bakir on 07.01.2019.
 */
//TODO Getalldvs, getuserbyusername : photo ,
    //TODO loöschen von user ,

public class DbHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Fahrradscout";

    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String CREATE_BENUTZER_TABLE = "create table if not exists Benutzer" +
            "(benutzer_id integer primary key AUTOINCREMENT," +
            "benutzername text not null UNIQUE," +
            "adresse text not null UNIQUE," +
            "email text not null," +
            "telefon text not null," +
            "geburtsdatum text not null," +
            "profilebild blob," +
            "passwort text not null);";
    //TODO profilbild und geburtsdatum fehlt

    private static final String CREATE_FAHRRAD_TABLE = "create table if not exists Fahrrad" +
            "(fahrrad_id integer primary key AUTOINCREMENT," +
            "eigentuermer integer not null," +
            "hersteller text not null," +
            "preis real not null," +
            "groesse integer not null," +
            "farbe text not null," +
            "art_gangschaltung integer not null," +
            "art_fahrrad integer not null," +
            "art_bremsen integer not null);";
    private static final String CREATE_ANZEIGE_TABLE = "create table if not exists Anzeige" +
            "(anzeige_id integer primary key AUTOINCREMENT," +
           // "fahrrad_id integer not null," +
            "erstelldatum text not null," +
            "ablaufdatum text not null," +
           // "beschreibung text not null," +
            "preis integer not null);";
    private static final String CREATE_AUKTION_TABLE = "create table if not exists Auktion" +
            "(auktion_id integer primary key AUTOINCREMENT," +
            "fahrrad integer not null," +
            "erstelldatum text not null," +
            "ablaufdatum text not null," +
            "hoechstbieter integer not null);";
    private static final String CREATE_ART_FAHRRAD_TABLE = "create table if not exists Art_fahrrad" +
            "(art_fahrrad_id integer primary key AUTOINCREMENT," +
            "art text not null);";
    private static final String CREATE_ART_BREMSEN_TABLE = "create table if not exists Art_bremsen" +
            "(art_bremsen_id integer primary key AUTOINCREMENT," +
            "art text not null);";
    private static final String CREATE_ART_GANGSCHALTUNG_TABLE = "create table if not exists Art_gangschaltung" +
            "(art_gangschaltung_id integer primary key AUTOINCREMENT," +
            "art text not null);";
    private static final String ADD_USER_1= "insert into benutzer(benutzername,passwort,email,telefon,adresse,geburtsdatum)"+
            "values('ahmed','123456','ahmed@gmail.com','017632322529','musterStrasse 1 76313 Karlsruhe', '1993-02-01')";
    private static final String ADD_USER_2= "insert into benutzer(benutzername,passwort,email,telefon,adresse,geburtsdatum)"+
            "values('andy','789456','andy@gmail.com','085523529','musterStrasse 2 76313 Karlsruhe','1994-05-92')";
    private static final String ADD_ANZEIGE_1= "insert into anzeige(anzeige_id,erstelldatum,ablaufdatum,preis,beschreibung) " +
            "values(1,'18.01.2019','29.01.2019',200)";


    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_BENUTZER_TABLE);
        database.execSQL(CREATE_FAHRRAD_TABLE);
        database.execSQL(CREATE_AUKTION_TABLE);
        database.execSQL(CREATE_ANZEIGE_TABLE);
        database.execSQL(CREATE_ART_BREMSEN_TABLE);
        database.execSQL(CREATE_ART_FAHRRAD_TABLE);
        database.execSQL(CREATE_ART_GANGSCHALTUNG_TABLE);
        database.execSQL(ADD_USER_1);
        database.execSQL(ADD_USER_2);
        database.execSQL(ADD_ANZEIGE_1);


    }

    @Override
    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){
        Log.w(DbHandler.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        onCreate(database);
    }

    public long createBenutzer (String benutzerName, String adresse, String email, String telefon, String passwort) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("benutzername",benutzerName);
        values.put("adresse",adresse);
        values.put("email",email);
        values.put("telefon",telefon);
        values.put("passwort",passwort);
        return database.insert("benutzer",null,values);

    }

    public long createFahrrad (int eigentuermer, String hersteller, double preis, int groesse, String farbe, int art_gangschaltung, int art_bremsen, int art_fahrrad) {
        SQLiteDatabase database = this.getWritableDatabase();
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
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fahrrad_id", fahrrad_id);
        values.put("erstelldatum", erstelldatum);
        values.put("ablaufdatum", ablaufdatum);
        values.put("preis", preis);
        return database.insert("anzeige",null,values);

    }
    public long createAuktion (int fahrrad, String erstelldatum, String ablaufdatum, int hoechstbieter) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fahrrad",fahrrad);
        values.put("erstelldatum",erstelldatum);
        values.put("ablaufdatum",ablaufdatum);
        values.put("hoechstbieter",hoechstbieter);
        return database.insert("auktion",null,values);
    }
    public long createArtFahrrad (String art) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("art",art);
        return database.insert("art_fahrrad",null,values);
    }

    public long createArtBremsen (String art) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("art",art);
        return database.insert("art_bremsen",null,values);
    }
    public long createArtGangschaltung (String art) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("art",art);
        return database.insert("art_gangschaltung",null,values);
    }
    public int updateBenutzer (int id, String benutzerName, String adresse, String email, String telefon, String passwort) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("benutzername",benutzerName);
        values.put("adresse",adresse);
        values.put("email",email);
        values.put("telefon",telefon);
        values.put("passwort",passwort);
        return database.update("Benutzer",values,"benutzer_id="+id,null);
    }

    public int createFahrrad (int id, int eigentuermer, String hersteller, double preis, int groesse, String farbe, int art_gangschaltung, int art_bremsen, int art_fahrrad) {
        SQLiteDatabase database = this.getWritableDatabase();
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
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fahrrad_id",fahrrad_id);
        values.put("erstelldatum",erstelldatum);
        values.put("ablaufdatum",ablaufdatum);
        return database.update("Anzeige",values,"anzeige_id="+id,null);
    }
    public int updateAuktion (int id, int fahrrad, String erstelldatum, String ablaufdatum, int hoechstbieter) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fahrrad",fahrrad);
        values.put("erstelldatum",erstelldatum);
        values.put("ablaufdatum",ablaufdatum);
        values.put("hoechstbieter",hoechstbieter);
        return database.update("Auktion",values,"auktion_id="+id,null);
    }

    public int updateArtFahrrad (int id, String art) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("art",art);
        return database.update("Art_fahrrad",values,"art_fahrrad_id="+id,null);
    }

    public int updateArtBremsen (int id, String art) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("art",art);
        return database.update("Art_bremsen",values,"art_bremsen_id="+id,null);
    }

    public int updateArtGangschaltung (int id, String art) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("art",art);
        return database.update("Art_gangschaltung",values,"art_gangschaltung_id="+id,null);
    }

    public String getPassword(String benutzername){
        SQLiteDatabase database = this.getReadableDatabase();
        String passwort = null;
        Cursor cursor = database.query("Benutzer",new String[]{"passwort"} , "benutzername=?", new String[]{benutzername}, null, null, null, null   );
        if (cursor != null) {
            cursor.moveToFirst();
            passwort = cursor.getString(0);
            return passwort;
        }
        return passwort;

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Benutzer getUserByBenutzername(String benutzername){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + "benutzer " + "Where benutzername = '" + benutzername+ "'";
        Cursor cursor = database.rawQuery(query,null);
        if(cursor!=null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            //TODO getInt() verursacht out of bound exception bei der DB, warsch werden user nicht richtig hinzugefügt
            int id = cursor.getInt(cursor.getColumnIndex("benutzer_id"));
            String adresse = cursor.getString(cursor.getColumnIndex("adresse"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String telefon = cursor.getString(cursor.getColumnIndex("telefon"));
            String passwort = cursor.getString(cursor.getColumnIndex("passwort"));
            String geburtsdatum = cursor.getString(cursor.getColumnIndex("geburtsdatum"));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = format.parse(geburtsdatum);
                Benutzer benutzer = new Benutzer(id,benutzername,passwort,email,adresse, date );
                return benutzer;
            }
            catch (ParseException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    /* Method for fetching record from Database */
    public ArrayList<Advertisement> getAllAdvertisement() {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + "Anzeige";
        ArrayList<Advertisement> advertisement = new ArrayList<>();
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