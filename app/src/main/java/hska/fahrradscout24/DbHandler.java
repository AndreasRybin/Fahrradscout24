package hska.fahrradscout24;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by bakir on 07.01.2019.
 */

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
            "passwort text not null);";

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
            "fahrrad_id integer not null," +
            "erstelldatum text not null," +
            "ablaufdatum text not null," +
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
            "(art_gangschaltung_id primary key AUTOINCREMENT," +
            "art text not null);";

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

    }

    @Override
    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){
        Log.w(DbHandler.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        onCreate(database);
    }
}