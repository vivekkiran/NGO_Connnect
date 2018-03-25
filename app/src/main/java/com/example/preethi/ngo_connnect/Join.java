package com.example.preethi.ngo_connnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.preethi.NGO_Connect.R;

import org.w3c.dom.Text;

import java.sql.ResultSet;
import java.sql.Statement;

import static android.widget.Toast.*;

public class Join extends AppCompatActivity {
    String eventName, ngoName, usname;
    Button join;
    private String ev_name, ev_joinee, ev_email, ev_contact, ev_designation, ev_ngo, ev_type;//, nc_password, nc_confirm_password, nc_weblink, nc_facebook, nc_linkedin, nc_domain, nc_description;
    private TextView tv, tv1;
    private EditText joinee, email, contact, designation;
    RadioGroup joinAs;
    RadioButton vol, part;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        tv = (TextView) findViewById(R.id.event);
        tv1 = (TextView) findViewById(R.id.ngo);
        Bundle bundle = getIntent().getExtras();
        eventName = bundle.getString("eventName");
        ngoName = bundle.getString("ngoName");
        System.out.println("Event Name and details!");
        System.out.println(eventName);
        System.out.println(ngoName);
        tv.setText(eventName);
        tv1.setText(ngoName);


        joinee = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        contact = (EditText) findViewById(R.id.contact);
        designation = (EditText) findViewById(R.id.designation);
        joinAs = (RadioGroup) findViewById(R.id.joinAs);
        vol = (RadioButton) findViewById(R.id.volunteer);
        part = (RadioButton) findViewById(R.id.participant);
        join = (Button) findViewById(R.id.join);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent i = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(i , 1);
                Join.JoinForEvent joinerDetails = new Join.JoinForEvent();
                joinerDetails.execute("");

                /*Register.DoSignup doSignup = new Register.DoSignup();
                doSignup.execute();*/

            }
        });
    }


    public void done(View v) {
        //register();
        Join.JoinForEvent joinerDetails = new Join.JoinForEvent();
        joinerDetails.execute("");
        System.out.println("In done method!");
    }

    public void register() {
        intialize();
        if(!validate()){
            makeText(this, "Unsuccessful!", LENGTH_SHORT).show();
            System.out.println("In register method");

        }
    }

    public void intialize() {

        ev_name = tv.getText().toString().trim();
        ev_joinee = joinee.getText().toString().trim();
        ev_email = email.getText().toString().trim();
        ev_contact = contact.getText().toString().trim();
        ev_designation = designation.getText().toString().trim();
        ev_ngo = tv1.getText().toString().trim();
        ev_type = ((RadioButton)findViewById(joinAs.getCheckedRadioButtonId())).getText().toString();
        System.out.print("In initalize method");

    }

    public boolean validate() {
        boolean valid = true;

        if (ev_joinee.isEmpty()) {
            joinee.setError("Please enter your full name!");
            valid = false;
        }

        if(ev_email.isEmpty()) {
            email.setError("Enter your email!");
            valid = false;
        }

        if(ev_contact.isEmpty() || contact.length() < 10 ) {
            contact.setError("Please enter a valid number");
            valid = false;
        }

        if(ev_designation.isEmpty() ) {
            designation.setError("Please enter designation!");
            valid = false;
        }

        return valid;
    }

    public class JoinForEvent extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;
        Boolean isSuccess = false;
        String eventJoined = tv.getText().toString().trim();
        String ngoJoined = tv1.getText().toString().trim();
        String joineeName = joinee.getText().toString().trim();
        String emailid = email.getText().toString().trim();
        String contactno = contact.getText().toString().trim();
        String desig = designation.getText().toString().trim();
        String value = ((RadioButton) findViewById(joinAs.getCheckedRadioButtonId())).getText().toString();

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(Join.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        protected void onPostExecute(String r) {
            makeText(Join.this, r, LENGTH_SHORT).show();

            if (isSuccess) {
                Intent i = new Intent(Join.this, JoinAcknowledgement.class);
                startActivity(i);
                finish();
            }
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                java.sql.Connection con = DatabaseConnection.getConnection();
                System.out.println("Connection Established");
                String query1 = "select * from JoineeDetails where contact = '" + contactno + "' and email = '" + emailid + "'";
                String query = "insert into JoineeDetails(eventName, ngoName, joineeName, email, contact, designation, joinas) values( '" + eventJoined + "','" + ngoJoined + "','" + joineeName + "','" + emailid + "','" + contactno + "','" + desig + "','" + value + "')";
                System.out.println(query);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query1);
                if (rs.next()) {
                    makeText(getBaseContext(), "Already regitered!", LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), "You've already registered for the event. Thankyou!",
                    //      Toast.LENGTH_LONG).show();
                } else {
                    int flag = stmt.executeUpdate(query);
                    System.out.println(flag);
                    System.out.println(contactno);
                    isSuccess = true;
                    System.out.println("User joined!!");
                    MailOperation l = new MailOperation();
                    l.execute();

                }
            } catch (Exception ex) {
                Log.e("ERROR", ex.getMessage());
            }
            return "";
        }

    }


}
