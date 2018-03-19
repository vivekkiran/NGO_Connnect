package com.example.preethi.ngo_connnect;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.preethi.NGO_Connect.R;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.example.preethi.ngo_connnect.Login.MyPREFERENCES;
import static droidninja.filepicker.FilePickerConst.REQUEST_CODE;

public class AddEvent extends AppCompatActivity {

    Button addevent , uploadpicture;
    SharedPreferences sharedpreferences;
    private String name, amount, venue, date, time, description;
    private EditText ev_name, ev_amount, ev_venue, ev_date, ev_time, ev_description;
    ImageView imagev;
    ImageView ev_poster;

    String emailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        System.out.println("In Add Event Class");

        imagev = (ImageView) findViewById(R.id.image);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        addevent = (Button) findViewById(R.id.addevent);
        ev_name = (EditText) findViewById(R.id.event_name);
        ev_amount = (EditText) findViewById(R.id.amount);
        ev_venue = (EditText) findViewById(R.id.venue);
        ev_date = (EditText) findViewById(R.id.date);
        ev_time = (EditText) findViewById(R.id.time);
        ev_description = (EditText) findViewById(R.id.description);
        ev_poster = (ImageView) findViewById(R.id.image);

        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent i = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(i , 1);
                AddEvent.DoAddEvent addevent = new AddEvent.DoAddEvent();
                addevent.execute("");
                /*Register.DoSignup doSignup = new Register.DoSignup();
                doSignup.execute();*/

            }
        });


        /*
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            emailId = bundle.getString("emailid");
        }
        System.out.println(emailId);
        */

    }

    public void addEvent(View view) {
        Intent intent = new Intent(AddEvent.this, Testing.class);
        startActivity(intent);
    }

    public void uploadPoster(View v) {
        /*
        Intent i = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i , 1);
        */
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imagev.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void done(View v) {
        register();
        AddEvent.DoAddEvent addevent = new AddEvent.DoAddEvent();
        addevent.execute();
        System.out.print("In done method");


    }

    public void register() {
        intialize();
        if(!validate()){
            Toast.makeText(this, "Signup has Failed", Toast.LENGTH_SHORT).show();
            System.out.println("In register method");

        }
    }

    public void signupSuccess() {
        Intent myIntent = new Intent(AddEvent.this, Testing.class);
        startActivity(myIntent);
        System.out.print("In signupsuccess method");
    }

    public boolean validate() {
        boolean valid = true;

        if(name.isEmpty() ) {
            ev_name.setError("Please Enter valid name");
            valid = false;
        }

        if(venue.isEmpty()) {
            ev_venue.setError("Please enter the venue");
            valid = false;
        }

        if(date.isEmpty()) {
            ev_date.setError("Please enter the date ");
            valid = false;
        }

        if(time.isEmpty()) {
            ev_time.setError("Please Enter time");
            valid = false;
        }

        if(description.isEmpty()) {
            ev_description.setError("Please enter your domain");
            valid = false;
        }



        System.out.print("In validate method");

        return valid;
    }

    public void intialize() {

        name = ev_name.getText().toString().trim();
        amount = ev_amount.getText().toString().trim();
        venue = ev_venue.getText().toString().trim();
        date = ev_date.getText().toString().trim();
        time = ev_time.getText().toString().trim();
        description = ev_description.getText().toString().trim();

        System.out.print("In initalize method");

    }


    public class DoAddEvent extends AsyncTask<String,Void,String> {

        String z = "";
        Boolean isSuccess = false;
        String name = ev_name.getText().toString().trim();
        String amount = ev_amount.getText().toString().trim();
        String venue = ev_venue.getText().toString().trim();
        String date = ev_date.getText().toString().trim();
        String description = ev_description.getText().toString().trim();
        private ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(AddEvent.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(String r) {

            Toast.makeText(AddEvent.this, r, Toast.LENGTH_SHORT).show();

            if (isSuccess) {
                Intent i = new Intent(AddEvent.this, Testing.class);
                startActivity(i);
                finish();
            }
            if (pDialog.isShowing())
                pDialog.dismiss();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                java.sql.Connection con = DatabaseConnection.getConnection();
                System.out.println("In Thread");
                System.out.println("Connection Established to database");
                if (con == null) {
                    z = "Error in connection with SQL server";
                    System.out.println("Connection Error!!");
                } else {
                    String query = "select * from Events where event_name LIKE '" + name + "'";
                    System.out.println(query);
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        z = "User Already Exists";
                        isSuccess = false;

                    } else {

                        int flag = stmt.executeUpdate("insert into Events (event_name, cost , location , date , time ,  description , poster) values('" + name + "' , '" + amount + "' , '" + venue + "' , '" + date + "' , '" + time + "' , '" + description + "'  , '"+ imagev +"' );");
                        System.out.println(flag);
                        z = "SignUp successfull";
                        isSuccess = true;
                        System.out.println("Added user!!");
                    }
                }
            } catch (Exception ex) {
                isSuccess = false;
                z = "Exceptions";
                Log.e("ERROR", ex.getMessage());

            }

            return z;
        }
    }




}
