package hska.fahrradscout24;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdvertisementActivity extends AppCompatActivity {

    String msg = "Android : AdvertisementActivity : ";
    GridView gridView;
    ArrayList<Advertisement> advertisementList;
    AdvertisementAdapter adapter;
    String username;
    private DbHandler db;
    Advertisement advertisementId;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisment);
        Log.d(msg, "The onCreate() event");
        username = getIntent().getStringExtra("username");

        gridView = (GridView) findViewById(R.id.gv_adv);

        db = new DbHandler(AdvertisementActivity.this);
        advertisementList = new ArrayList<Advertisement>();

        advertisementList = db.getAllAdvertisement();
        adapter = new AdvertisementAdapter(AdvertisementActivity.this, advertisementList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(),FullAdvertisementActivity.class);
                i.putExtra("position", Integer.toString(position));
                advertisementId = advertisementList.get(position);
                i.putExtra("id", Integer.toString(advertisementId.getId()));
                i.putExtra("username", username); //Optional parameters}
                startActivity(i);
            }
        });


    }

    /** Called when the activity is about to become visible. */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(msg, "The onStart() event");
    }

    /** Called when the activity has become visible. */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(msg, "The onResume() event");
        username = getIntent().getStringExtra("username");

        gridView = (GridView) findViewById(R.id.gv_adv);

        db = new DbHandler(AdvertisementActivity.this);
        advertisementList = new ArrayList<Advertisement>();

        advertisementList = db.getAllAdvertisement();
        adapter = new AdvertisementAdapter(AdvertisementActivity.this, advertisementList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(),FullAdvertisementActivity.class);
                i.putExtra("position", Integer.toString(position));
                advertisementId = advertisementList.get(position);
                i.putExtra("id", Integer.toString(advertisementId.getId()));
                i.putExtra("username", username); //Optional parameters}
                startActivity(i);
            }
        });

    }

    /** Called when another activity is taking focus. */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "The onPause() event");
    }

    /** Called when the activity is no longer visible. */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "The onStop() event");
    }

    /** Called just before the activity is destroyed. */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.tv_adv_id) {
            //TODO Start Activity für eine Anzeige
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false); // disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // remove the icon
        }
        return true;
    }
    public void profileClick(MenuItem menuItem) {
        Intent intentProfile = new Intent(this, ProfileActivity.class);


            intentProfile.putExtra("username", username); //Optional parameters}
            //myIntent.putExtra("key", value); //Optional parameters
            //String value = intent.getStringExtra("key"); //if it's a string you stored.
            this.startActivity(intentProfile);
    }
    public void addAnzeigeClick(MenuItem menuItem) {
        Intent intentAddAnzeige = new Intent(this, AddAnzeigenActivity.class);

        intentAddAnzeige.putExtra("username", username); //Optional parameters}
        //myIntent.putExtra("key", value); //Optional parameters
        //String value = intent.getStringExtra("key"); //if it's a string you stored.
        this.startActivity(intentAddAnzeige);
    }

    public void filterUser(MenuItem menuItem) {
        //filterusercode todo
        //Intent intentProfile = new Intent(this, ProfileActivity.class);


        //intentProfile.putExtra("username", username); //Optional parameters}
        //myIntent.putExtra("key", value); //Optional parameters
        //String value = intent.getStringExtra("key"); //if it's a string you stored.
        //this.startActivity(intentProfile);
    }
    public void filterColor(MenuItem menuItem) {

    }
    public void filterPrice(MenuItem menuItem) {
        advertisementList = new ArrayList<Advertisement>();

        //TEST
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insert minimum price");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                advertisementList = db.getAllAdvertisementByPrice(input.getText().toString());
                adapter = new AdvertisementAdapter(AdvertisementActivity.this, advertisementList);
                gridView.setAdapter(adapter);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
        //TEST

    }

}