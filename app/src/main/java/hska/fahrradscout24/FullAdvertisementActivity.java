package hska.fahrradscout24;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class FullAdvertisementActivity extends Activity {

    String usernameId;
    Integer advertisementId;
    ArrayList<Advertisement> listOfAd = new ArrayList<Advertisement>();
    private DbHandler db;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_advertisement);
        //TODO ID FEHLT MIR
        //usernameId = getIntent().getStringExtra("position");

        String stringId = getIntent().getStringExtra("id");

        if (stringId != null){
            advertisementId = Integer.parseInt(stringId);
        }



        db = new DbHandler(FullAdvertisementActivity.this);


        Button deleteButton = (Button) findViewById(R.id.btn_fulladv_delete);
        Button discardButton = (Button) findViewById(R.id.btn_fulladv_discard);
        Button saveButton = (Button) findViewById(R.id.btn_fulladv_save);

        Advertisement e = new Advertisement();
        e = db.getAnzeige(advertisementId);

        TextView verkäuferFulladv = findViewById(R.id.tv_fulladv_show_verkäufer);
        TextView erstelldatumFulladv = findViewById(R.id.tv_fulladv_show_erstelldatum);
        TextView ablaufdatumFulladv = findViewById(R.id.tv_fulladv_show_ablaufdatum);
        EditText preisFulladv = findViewById(R.id.edit_fulladv_preis);

        verkäuferFulladv.setText("asd");
        erstelldatumFulladv.setText("qwe");
        ablaufdatumFulladv.setText("fhg");
        preisFulladv.setText("eee");




    }
}
