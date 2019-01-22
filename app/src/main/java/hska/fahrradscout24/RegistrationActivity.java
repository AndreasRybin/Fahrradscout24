package hska.fahrradscout24;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {
    private Benutzer user;
    private DbHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        db = new DbHandler(this);

        Button buttonSignIn= (Button) findViewById(R.id.btnSignIn_reg);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                    EditText tvUsername = (EditText)findViewById(R.id.edit_text_username_reg);
                    EditText tvPassword = (EditText)findViewById(R.id.edit_text_password_reg);
                    EditText tvMail = (EditText)findViewById(R.id.edit_text_mail_reg);
                    EditText tvPhone = (EditText)findViewById(R.id.edit_text_phone_reg);
                    EditText tvAdress= (EditText)findViewById(R.id.edit_text_adress_reg);
                    EditText tvBirthdate = (EditText)findViewById(R.id.tvSelectedDate_reg);

                    if("".equals(tvUsername.getText().toString()) ||
                       "".equals(tvPassword.getText().toString()) ||
                       "".equals(tvMail.getText().toString()) ||
                       "".equals(tvPhone.getText().toString()) ||
                       "".equals(tvAdress.getText().toString()) ||
                       "".equals(tvBirthdate.getText().toString())){
                        Toast.makeText(RegistrationActivity.this,"No Input should be empty",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                    String username = tvUsername.getText().toString();
                    user = db.getUserByBenutzername(username);
                    if(user != null){
                        Toast.makeText(RegistrationActivity.this,"Username already exists",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent intentRegistration = new Intent(RegistrationActivity.this, AdvertisementActivity.class);
                       db.createBenutzer (tvUsername.getText().toString(),
                                          tvAdress.getText().toString(),
                                          tvMail.getText().toString(),
                                          tvPhone.getText().toString(),
                                          tvPassword.getText().toString(),
                                          tvBirthdate.getText().toString());
                        intentRegistration.putExtra("username", tvUsername.getText().toString());
                        //String value = intent.getStringExtra("key"); //if it's a string you stored.
                        startActivity(intentRegistration);

                    }}
            };
        });

        ImageButton selectDate = findViewById(R.id.btnDate);
        final TextView date = findViewById(R.id.tvSelectedDate_reg);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegistrationActivity.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                String strday = Integer.toString(day);
                                String strmonth = Integer.toString(month+1);
                                String stryear = Integer.toString(year);
                                if(day<10){
                                    strday = "0"+strday;
                                }
                                if(month+1 <10){
                                    strmonth = "0"+strmonth;
                                }
                                date.setText(strday + "." + strmonth + "." + stryear);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });
    }
}
