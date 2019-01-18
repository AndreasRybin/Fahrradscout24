package hska.fahrradscout24;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MainActivity extends AppCompatActivity {

    private DbHandler db;
    private Benutzer user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init SQLlite DB
        db = new DbHandler(this);
        //initialize fresco
        Fresco.initialize(this);


        Button buttonLogin= (Button) findViewById(R.id.btnLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                EditText tvUsername = (EditText) findViewById(R.id.edit_text_login_username);
                EditText tvPassword = (EditText) findViewById(R.id.edit_text_login_password);
                String password = "";
                user = db.getUserByBenutzername(tvUsername.getText().toString());
                if(user != null){
                    password = user.getPasswort();}
                   String tvPasswordText = tvPassword.getText().toString();
                if(user == null || !(password.equals(tvPasswordText)) ){
                    Toast.makeText(MainActivity.this,"Login failed",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                startActivity(new Intent(MainActivity.this,AdvertisementActivity.class));}
            };
        });

        Button buttonSignIn= (Button) findViewById(R.id.btnSignIn);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                // TODO Andi hier muss deine Activity anstatt ProfileActivity rein, AdvertisementActivity is iwie buggy
                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
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
