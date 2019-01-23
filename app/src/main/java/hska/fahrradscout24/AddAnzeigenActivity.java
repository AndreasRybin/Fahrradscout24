package hska.fahrradscout24;

import android.Manifest;
import android.app.AlertDialog;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;


public class AddAnzeigenActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1; //load picture
    private static int RESULT_LOAD_IMAGE = 1; //load picture

    String username;
    DbHandler db;
    Bitmap bitmapimage;
    Benutzer user;
    CharSequence erstelldatum;
    String stringErstelldatum;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_anzeige);

        username = getIntent().getStringExtra("username");

        db = new DbHandler(AddAnzeigenActivity.this);
        user = db.getUserByBenutzername(username);


        final EditText preisadd = findViewById(R.id.edit_add_preis);
        final EditText farbeadd = findViewById(R.id.edit_add_farbe);
        final EditText groesseadd = findViewById(R.id.edit_add_groesse);
        final EditText ablaufdatumadd = findViewById(R.id.edit_add_ablaufdatum);
        ImageView image = findViewById(R.id.image_add);

        Date d = new Date();
        erstelldatum  = DateFormat.format("dd.MM.yyyy ", d.getTime());
        stringErstelldatum = erstelldatum.toString();




        Button createBtn = (Button) findViewById(R.id.btn_create);

        createBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //get Data from texts

                db.createAnzeige(
                        bitmapimage,
                        "23.01.2019",
                        ablaufdatumadd.getText().toString(),
                        Integer.parseInt(preisadd.getText().toString()),
                        user.getBenutzer_id(),
                        farbeadd.getText().toString(),
                        Integer.parseInt(groesseadd.getText().toString())
                        );
                Toast.makeText(AddAnzeigenActivity.this,"Creating sucessful",
                        Toast.LENGTH_SHORT).show();
            }
        });

        //load picture
        // do not execute this when in landscape
        //int display_mode = getResources().getConfiguration().orientation;

        //if (display_mode == Configuration.ORIENTATION_PORTRAIT) {

        ImageView clickLoadImage = (ImageView) findViewById(R.id.image_add);
        clickLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (ContextCompat.checkSelfPermission(AddAnzeigenActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(AddAnzeigenActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    } else {
                        ActivityCompat.requestPermissions(AddAnzeigenActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                } else {
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
            }
        });
        //}
        //end load picture
    }

    /*public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.iconFilter).setVisible(false);
        return true;
    }*/
    public void profileClick(MenuItem menuItem) {
        Intent intentProfile = new Intent(this, ProfileActivity.class);

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
    public void filterColor(MenuItem menuItem) { }
    public void filterPrice(MenuItem menuItem) { }

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

            ImageView imageView = (ImageView) findViewById(R.id.image_add);
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
            bitmapimage = resizedBitmap;
        }

    }

}


