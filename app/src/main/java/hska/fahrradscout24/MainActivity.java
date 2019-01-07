package hska.fahrradscout24;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize fresco
        Fresco.initialize(this);

        Button buttonLogin= (Button) findViewById(R.id.btnLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                // TODO Andi hier muss deine Activity anstatt ProfileActivity rein, AdvertisementActivity is iwie buggy
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
            };
        });
    }

    //TODO Andi die 2 Methoden bitte ausschneiden und in die Activity nach Login stecken
    //TODO Anfang ausschnitt
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public void profileClick(MenuItem menuItem){
        Intent intent = new Intent(this, ProfileActivity.class);
        //myIntent.putExtra("key", value); //Optional parameters
        //String value = intent.getStringExtra("key"); //if it's a string you stored.
        this.startActivity(intent);
    }
    //TODO Ende ausschnitt


}
