package hska.fahrradscout24;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by bakir on 07.01.2019.
 */
//TODO Getalldvs, getuserbyusername : photo ,
//TODO loöschen von user ,

public class DbHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Fahrradscout2";

    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String CREATE_BENUTZER_TABLE = "create table if not exists Benutzer" +
            "(benutzer_id integer primary key AUTOINCREMENT," +
            "benutzername text not null UNIQUE," +
            "adresse text not null," +
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
            "preis integer not null," +
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
            "fahrradbild blob," +
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
            "values('ahmed','123457','ahmed@gmail.com','017632322529','musterStrasse 1 76313 Karlsruhe', '19.21.1993')";
    private static final String ADD_USER_2= "insert into benutzer(benutzername,passwort,email,telefon,adresse,geburtsdatum)"+
            "values('andy','789456','andy@gmail.com','085523529','musterStrasse 2 92981 Mannheim','29.06.1994')";
    private static final String ADD_ANZEIGE_1= "insert into anzeige(anzeige_id,erstelldatum,ablaufdatum,preis) " +
            "values(1,'26.01.2019','29.01.2019',200)";
    private static final String ADD_ANZEIGE_2= "insert into anzeige(anzeige_id,erstelldatum,ablaufdatum,preis) " +
            "values(2,'18.01.2019','29.01.2019',500)";
    private static final String ADD_ANZEIGE_3= "insert into anzeige(anzeige_id,erstelldatum,ablaufdatum,preis) " +
            "values(3,'18.01.2019','29.01.2019',400)";
    private static final String ADD_ANZEIGE_4= "insert into anzeige(anzeige_id,erstelldatum,ablaufdatum,preis) " +
            "values(4,'12.01.2019','29.01.2019',300)";
    private static final String ADD_ANZEIGE_5= "insert into anzeige(anzeige_id,erstelldatum,ablaufdatum,preis) " +
            "values(5,'15.01.2019','29.01.2019',800)";
    private static final String ADD_ANZEIGE_6= "insert into anzeige(anzeige_id,erstelldatum,ablaufdatum,preis) " +
            "values(6,'18.01.2019','29.01.2019',70)";
    private static final String ADD_ANZEIGE_7= "insert into anzeige(anzeige_id,erstelldatum,ablaufdatum,preis) " +
            "values(7,'22.01.2019','29.01.2019',90)";
    private static final String ADD_ANZEIGE_8= "insert into anzeige(anzeige_id,erstelldatum,ablaufdatum,preis) " +
            "values(8,'10.01.2019','29.01.2019',560)";
    private static final String ADD_ANZEIGE_9= "insert into anzeige(anzeige_id,erstelldatum,ablaufdatum,preis) " +
            "values(9,'11.01.2019','29.01.2019',330)";
    private static final String ADD_ANZEIGE_10= "insert into anzeige(anzeige_id,erstelldatum,ablaufdatum,preis) " +
            "values(10,'12.01.2019','29.01.2019',990)";
    private static final String ADD_ANZEIGE_11= "insert into anzeige(anzeige_id,erstelldatum,ablaufdatum,preis) " +
            "values(11,'16.01.2019','29.01.2019',2200)";
    private static final String ADD_ANZEIGE_12= "insert into anzeige(anzeige_id,erstelldatum,ablaufdatum,preis) " +
            "values(12,'18.01.2019','29.01.2019',30)";
    private static final String ADD_ANZEIGE_13= "insert into anzeige(anzeige_id,erstelldatum,ablaufdatum,preis) " +
            "values(13,'30.01.2019','20.02.2019',55)";
    private static final String ADD_ANZEIGE_14= "insert into anzeige(anzeige_id,erstelldatum,ablaufdatum,preis) " +
            "values(14,'18.01.2019','29.01.2019',220)";
    private static final String ADD_ANZEIGE_15= "insert into anzeige(anzeige_id,erstelldatum,ablaufdatum,preis) " +
            "values(15,'19.01.2019','30.01.2019',300)";


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
        database.execSQL(ADD_ANZEIGE_2);
        database.execSQL(ADD_ANZEIGE_3);
        database.execSQL(ADD_ANZEIGE_4);
        database.execSQL(ADD_ANZEIGE_5);
        database.execSQL(ADD_ANZEIGE_6);
        database.execSQL(ADD_ANZEIGE_7);
        database.execSQL(ADD_ANZEIGE_8);
        database.execSQL(ADD_ANZEIGE_9);
        database.execSQL(ADD_ANZEIGE_10);
        database.execSQL(ADD_ANZEIGE_11);
        database.execSQL(ADD_ANZEIGE_12);
        database.execSQL(ADD_ANZEIGE_13);
        database.execSQL(ADD_ANZEIGE_14);
        database.execSQL(ADD_ANZEIGE_15);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){
        Log.w(DbHandler.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        onCreate(database);
    }

    public void createBenutzer (String benutzerName, String adresse, String email, String telefon, String passwort, String geburtsdatum, Bitmap profileBild) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        byte[] profileBild_byte = getBitmapAsByteArray(profileBild);
        values.put("benutzername",benutzerName);
        values.put("adresse",adresse);
        values.put("email",email);
        values.put("telefon",telefon);
        values.put("passwort",passwort);
        values.put("geburtsdatum",geburtsdatum);
        values.put("profilebild",profileBild_byte);

        database.insert("benutzer",null,values);

    }

    public void createBenutzer (String benutzerName, String adresse, String email, String telefon, String passwort, String geburtsdatum) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("benutzername",benutzerName);
        values.put("adresse",adresse);
        values.put("email",email);
        values.put("telefon",telefon);
        values.put("passwort",passwort);
        values.put("geburtsdatum",geburtsdatum);

        database.insert("benutzer",null,values);

    }

    public void createFahrrad (int eigentuermer, String hersteller, double preis, int groesse, String farbe, int art_gangschaltung, int art_bremsen, int art_fahrrad) {
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
        database.insert("benutzer",null,values);

    }
    public void createAnzeige (Bitmap fahrradbild, String erstelldatum, String ablaufdatum, int preis) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        byte[] fahrradbild_byte = getBitmapAsByteArray(fahrradbild);
        values.put("fahrradbild", fahrradbild_byte);
        values.put("erstelldatum", erstelldatum);
        values.put("ablaufdatum", ablaufdatum);
        values.put("preis", preis);
        database.insert("anzeige",null,values);

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
    public void createArtFahrrad (String art) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("art",art);
        database.insert("art_fahrrad",null,values);
    }

    public void createArtBremsen (String art) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("art",art);
        database.insert("art_bremsen",null,values);
    }
    public void createArtGangschaltung (String art) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("art",art);
        database.insert("art_gangschaltung",null,values);
    }
    public void updateBenutzer (int id, String benutzerName, String adresse, String email, String telefon, String passwort, String geburtsdatum, Bitmap profileBild) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        byte[] profileBild_byte = getBitmapAsByteArray(profileBild);
        values.put("benutzername",benutzerName);
        values.put("adresse",adresse);
        values.put("email",email);
        values.put("telefon",telefon);
        values.put("passwort",passwort);
        values.put("geburtsdatum",geburtsdatum);
        values.put("profilebild",profileBild_byte);
        database.update("Benutzer",values,"benutzer_id="+id,null);
    }

    public void createFahrrad (int id, int eigentuermer, String hersteller, double preis, int groesse, String farbe, int art_gangschaltung, int art_bremsen, int art_fahrrad) {
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
        database.update("Fahrrad",values,"fahrrad_id="+id,null);
    }
    public int updateAnzeige (int id, String erstelldatum, String ablaufdatum, int preis, Bitmap fahrradbild) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        byte[] fahrradbild_byte = getBitmapAsByteArray(fahrradbild);
        values.put("fahrradbild", fahrradbild_byte);
        values.put("erstelldatum", erstelldatum);
        values.put("ablaufdatum", ablaufdatum);
        values.put("preis", preis);
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
            cursor.close();
            return passwort;
        }
        return passwort;

    }
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
            byte[] profileBild = cursor.getBlob(cursor.getColumnIndex("profilebild"));

            Benutzer benutzer = null;
            if(profileBild != null) {
                Bitmap profileBild_bitmap = BitmapFactory.decodeByteArray(profileBild, 0, profileBild.length);
                benutzer = new Benutzer(id,benutzername,passwort,email,adresse, geburtsdatum, telefon, profileBild_bitmap );
                cursor.close();
                return benutzer;
            }
            else{
                benutzer = new Benutzer(id,benutzername,passwort,email,adresse, geburtsdatum, telefon );}
            cursor.close();
            return benutzer;

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
                String erstelldatum = c.getString(c.getColumnIndex("erstelldatum"));
                String ablaufdatum = c.getString(c.getColumnIndex("ablaufdatum"));
                int preis = c.getInt(c.getColumnIndex("preis"));
                byte[] fahrradbild = c.getBlob(c.getColumnIndex("fahrradbild"));
                if (fahrradbild != null) {
                    Bitmap fahrradbild_bitmap = BitmapFactory.decodeByteArray(fahrradbild, 0, fahrradbild.length);

                    Advertisement emp = new Advertisement(anzeigeId, preis, erstelldatum, ablaufdatum, fahrradbild_bitmap);
                    advertisement.add(emp);

                }
                else {
                    Advertisement emp = new Advertisement(anzeigeId, preis, erstelldatum, ablaufdatum);
                    advertisement.add(emp);

                }
            }
            c.close();
        }
        return advertisement;
    }

    public Bitmap getProfileBild(int id){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT profilebild FROM " + "benutzer " + "Where benutzer_id = " + id ;
        Cursor cursor = database.rawQuery(query,null);
        byte[] profileBild = cursor.getBlob(cursor.getColumnIndex("profilebild"));
        Bitmap profileBild_bitmap = BitmapFactory.decodeByteArray(profileBild, 0, profileBild.length);
        cursor.close();
        return profileBild_bitmap;

    }

    public Bitmap getFahrradBild(int id){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT fahrradbild FROM " + "anzeige " + "Where anzeige_id = " + id ;
        Cursor cursor = database.rawQuery(query,null);
        byte[] profileBild = cursor.getBlob(cursor.getColumnIndex("fahrradbild"));
        Bitmap profileBild_bitmap = BitmapFactory.decodeByteArray(profileBild, 0, profileBild.length);
        cursor.close();
        return profileBild_bitmap;

    }

    public boolean deleteBenutzer(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete("benutzer", "benutzer_id="+ id,null) > 0;
    }

    public boolean deleteAnzeige(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete("anzeige", "anzeige_id="+ id,null) > 0;
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            if(bitmap != null){
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
            return outputStream.toByteArray();}
            else {return null;}

    }
}