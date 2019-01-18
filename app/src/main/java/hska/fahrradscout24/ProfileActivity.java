package hska.fahrradscout24;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    //load picture
    private static int RESULT_LOAD_IMAGE = 1; //load picture
    private DbHandler db;
    private Benutzer user;
    private String username;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //String value = intent.getStringExtra("key"); //if it's a string you stored.
        username = getIntent().getStringExtra("username");
        //init SQLlite DB
        db = new DbHandler(this);
        if(username != "" && username != null){
        user = db.getUserByBenutzername(username);
        }
        //get Textviews
        TextView tvUsername = (TextView)findViewById(R.id.tv_username_profile);
        EditText tvAddress = (EditText)findViewById(R.id.edit_text_adress);
        EditText tvBirthDate = (EditText)findViewById(R.id.tvSelectedDate);
        EditText tvMail = (EditText)findViewById(R.id.edit_text_mail);
        EditText tvPhone = (EditText)findViewById(R.id.edit_text_phone);
        EditText tvPassword = (EditText)findViewById(R.id.edit_text_password);
        ImageView imageView = (ImageView) findViewById(R.id.imgProfilePic);
        Switch   swNotifications = (Switch) findViewById(R.id.switchNotification);

        if(user != null) {
            //TODO geburtsdatum, Phone und Notifications einbinden
            // format blob into bitmap
            //byte[] byteArray = DBcursor.getBlob(columnIndex);
            // Bitmap picture = BitmapFactory.decodeByteArray(byteArray, 0 ,byteArray.length);
            //resize bitmap
            //final int maxSize = 120;
            //int outWidth;
            //int outHeight;
            //int inWidth = picture.getWidth();
            //int inHeight = picture.getHeight();
            //if(inWidth > inHeight){
            //    outWidth = maxSize;
            //    outHeight = (inHeight * maxSize) / inWidth;
            //} else {
            //    outHeight = maxSize;
            //    outWidth = (inWidth * maxSize) / inHeight;
            //}
            //Bitmap resizedBitmap = picture;
            //Bitmap resizedBitmap = Bitmap.createScaledBitmap(picture, outWidth, outHeight, true);
            //imageView.setImageBitmap(resizedBitmap);
            tvUsername.setText(user.getBenutzername());
            tvAddress.setText(user.getAdresse());
            //etBirthDate.setText(user.getGeburtsdatum());
            tvMail.setText(user.getEmail());
            tvPassword.setText(user.getPasswort());
        }

        //start calendar button
        ImageButton selectDate = findViewById(R.id.btnDate);
        final TextView date = findViewById(R.id.tvSelectedDate);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileActivity.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                String strday = Integer.toString(day);
                                String strmonth = Integer.toString(month+1);
                                if(day<10){
                                    strday = "0"+strday;
                                }
                                if(month+1 <10){
                                    strmonth = "0"+strmonth;
                                }
                                date.setText(strday + "." + strmonth + "." + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });
        //end calendarbutton


        //load picture
        // do not execute this when in landscape
        //int display_mode = getResources().getConfiguration().orientation;

        //if (display_mode == Configuration.ORIENTATION_PORTRAIT) {
        ImageButton btnClickLoadImage = (ImageButton) findViewById(R.id.btnProfilePic);
            btnClickLoadImage.setOnClickListener(new View.OnClickListener() {

                                                  @Override
                                                  public void onClick(View arg0) {
                                                      if (ContextCompat.checkSelfPermission(ProfileActivity.this,
                                                              Manifest.permission.READ_EXTERNAL_STORAGE)
                                                              != PackageManager.PERMISSION_GRANTED) {

                                                          // Permission is not granted
                                                          // Should we show an explanation?
                                                          if (ActivityCompat.shouldShowRequestPermissionRationale(ProfileActivity.this,
                                                                  Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                                              // Show an explanation to the user *asynchronously* -- don't block
                                                              // this thread waiting for the user's response! After the user
                                                              // sees the explanation, try again to request the permission.
                                                          } else {
                                                              // No explanation needed; request the permission
                                                              ActivityCompat.requestPermissions(ProfileActivity.this,
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
        ImageView clickLoadImage = (ImageView) findViewById(R.id.imgProfilePic);
        clickLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (ContextCompat.checkSelfPermission(ProfileActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(ProfileActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    } else {
                        ActivityCompat.requestPermissions(ProfileActivity.this,
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
    //start switch listener
        Switch notifications = (Switch) findViewById(R.id.switchNotification);
        if(notifications != null){
        notifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ImageView imageNotifications = (ImageView) findViewById(R.id.imgNotifications);
                if(isChecked) {
                    //do stuff when Switch is ON
                    imageNotifications.setImageDrawable(getResources().getDrawable(R.drawable.ic_notifications_active_black_24dp));
                } else {
                    //do stuff when Switch if OFF
                    imageNotifications.setImageDrawable(getResources().getDrawable(R.drawable.ic_notifications_black_24dp));
                }
                // do something, the isChecked will be
                // true if the switch is in the On position
            }
        });}
        //end switch listener
        //start save changes
        Button btnProfileSave = (Button) findViewById(R.id.btn_profile_save);

        btnProfileSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //get Data from texts
                EditText tvBirthdate = (EditText) findViewById(R.id.tvSelectedDate);
                EditText tvAdress = (EditText) findViewById(R.id.edit_text_adress);
                EditText tvMail = (EditText) findViewById(R.id.edit_text_mail);
                EditText tvPhone = (EditText) findViewById(R.id.edit_text_phone);
                EditText tvPassword = (EditText)findViewById(R.id.edit_text_password);

                //tvPhone.getText();
                //todo speichern, return code abwarten und toast ausgeben
                Toast.makeText(ProfileActivity.this,"Saving sucessful",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //discard changes
        final Button btnProfileDiscard = (Button) findViewById(R.id.btn_profile_discard);
        btnProfileDiscard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //get Textviews
                EditText tvAddress = (EditText)findViewById(R.id.edit_text_adress);
                EditText tvBirthDate = (EditText)findViewById(R.id.tvSelectedDate);
                EditText tvMail = (EditText)findViewById(R.id.edit_text_mail);
                EditText tvPhone = (EditText)findViewById(R.id.edit_text_phone);
                EditText tvPassword = (EditText)findViewById(R.id.edit_text_password);
                Switch   swNotifications = (Switch) findViewById(R.id.switchNotification);

                if(user != null) {
                    //TODO geburtsdatum, Phone und Notifications einbinden
                    tvAddress.setText(user.getAdresse());
                    //tvBirthDate.setText(user.getGeburtsdatum());
                    tvMail.setText(user.getEmail());
                    tvPassword.setText(user.getPasswort());
                }

                Toast.makeText(ProfileActivity.this,"Changes discarted",
                        Toast.LENGTH_SHORT).show();
            }
        });


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

            ImageView imageView = (ImageView) findViewById(R.id.imgProfilePic);
            Bitmap picture = BitmapFactory.decodeFile(picturePath);
            //resize bitmap
            final int maxSize = 120;
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
            //TODO save picture in orientation change
        }

        }




}
