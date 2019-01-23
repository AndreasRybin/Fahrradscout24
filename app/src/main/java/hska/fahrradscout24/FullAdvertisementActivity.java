package hska.fahrradscout24;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppComponentFactory;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class FullAdvertisementActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1; //load picture
    private static int RESULT_LOAD_IMAGE = 1; //load picture
    String usernameId;
    Integer advertisementId;
    ArrayList<Advertisement> listOfAd = new ArrayList<Advertisement>();
    private DbHandler db;
    String username;
    private Bitmap advertisement_picture;
    Advertisement advertisement = new Advertisement();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_advertisement);

        final Button deleteBtn = (Button) findViewById(R.id.btn_fulladv_delete);
        final Button saveBtn = (Button) findViewById(R.id.btn_fulladv_save);



        username = getIntent().getStringExtra("username");
        usernameId = getIntent().getStringExtra("position");

        String stringId = getIntent().getStringExtra("id");

        if (stringId != null){
            advertisementId = Integer.parseInt(stringId);
        }

        db = new DbHandler(FullAdvertisementActivity.this);


        advertisement = db.getAnzeige(advertisementId);

        TextView verkäuferFulladv = findViewById(R.id.tv_fulladv_show_verkäufer);
        TextView erstelldatumFulladv = findViewById(R.id.tv_fulladv_show_erstelldatum);
        TextView ablaufdatumFulladv = findViewById(R.id.tv_fulladv_show_ablaufdatum);
        TextView farbeFulladv = findViewById(R.id.tv_fulladv_farbe);
        TextView groesseFulladv = findViewById(R.id.tv_fulladv_groesse);
        EditText preisFulladv = findViewById(R.id.edit_fulladv_preis);
        ImageView imageFulladv = (ImageView) findViewById(R.id.iv_fulladv_image);

        Benutzer user = db.getUserByBenutzername(username);
        Benutzer verkäufer = db.getUserById(advertisement.getBenutzer_id());

        if (verkäufer != null)
        verkäuferFulladv.setText(verkäufer.getBenutzername());

        Bitmap picture = advertisement.getFahrradbild();
        if(picture != null){
            imageFulladv.setImageBitmap(picture);}

        erstelldatumFulladv.setText(advertisement.getErstelldatum());
        ablaufdatumFulladv.setText(advertisement.getAblaufdatum());
        farbeFulladv.setText(advertisement.getFarbe());
        groesseFulladv.setText(Integer.toString(advertisement.getGroesse()));
        preisFulladv.setText(Integer.toString(advertisement.getPreis()));

        //SHOW OR HIDE BUTTON IF YOU ARE OWNER OF ADVERTISEMENT
        if (verkäufer.getBenutzer_id() != user.getBenutzer_id())
        {
            //HIDE Buttons
            deleteBtn.setVisibility(View.INVISIBLE);
            saveBtn.setVisibility(View.INVISIBLE);
        }
        else
        {
            //SHOW Buttons
            deleteBtn.setVisibility(View.VISIBLE);
            saveBtn.setVisibility(View.VISIBLE);
        }

        //START OD ON CLICK FUNKTION
        ImageView btnClickLoadImage = (ImageView) findViewById(R.id.iv_fulladv_image);
        btnClickLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (ContextCompat.checkSelfPermission(FullAdvertisementActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(FullAdvertisementActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(FullAdvertisementActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    // Permission has already been granted
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }

            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(FullAdvertisementActivity.this)
                        .setTitle("Deleting Advertisement")
                        .setMessage("Do you really want to delete your advertisement?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                db.deleteAnzeige(advertisementId);
                                Toast.makeText(FullAdvertisementActivity.this, "Advertisement deleted!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(FullAdvertisementActivity.this,AdvertisementActivity.class);
                                startActivity(i);

                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }});

        final Advertisement finalAdvertisement = advertisement;
        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //get Data from texts

                EditText tvPreis = (EditText)findViewById(R.id.edit_fulladv_preis);

                db.updateAnzeige(advertisement.getId(),
                        advertisement.getErstelldatum(),
                        advertisement.getAblaufdatum(),
                        Integer.parseInt(tvPreis.getText().toString()),
                        advertisement_picture,
                        advertisement.getFarbe(),
                        advertisement.getGroesse());
                Toast.makeText(FullAdvertisementActivity.this,"Saving sucessful",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.iconFilter).setVisible(false);
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
        Intent intentProfile = new Intent(this, AddAnzeigenActivity.class);

        intentProfile.putExtra("username", username); //Optional parameters}
        //myIntent.putExtra("key", value); //Optional parameters
        //String value = intent.getStringExtra("key"); //if it's a string you stored.
        this.startActivity(intentProfile);
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

    }

    //getting awnser from permission question
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //String value = intent.getStringExtra("key"); //if it's a string you stored.
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //getting filepath
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            //getting image from path
            File locationOfFile = new
                    File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/images");
            File destination= new File(picturePath);
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(destination);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //converting image to bitmap
            Bitmap img = BitmapFactory.decodeStream(fileInputStream);

            ImageView imageView = (ImageView) findViewById(R.id.iv_fulladv_image);
            Bitmap picture = BitmapFactory.decodeFile(picturePath);
            //resize bitmap
            final int maxSize = 250;
            int outWidth;
            int outHeight;
            int inWidth = picture.getWidth();
            int inHeight = picture.getHeight();
            if(inWidth > inHeight){
                outWidth = maxSize;
                outHeight = (inHeight * maxSize) / inWidth;
            } else {
                outHeight = maxSize;
                outWidth = (inWidth * maxSize) / inHeight;
            }

            //Bitmap resizedBitmap = picture;
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(picture, outWidth, outHeight, true);
            imageView.setImageBitmap(resizedBitmap);
            advertisement_picture = resizedBitmap;
        }

    }
}
