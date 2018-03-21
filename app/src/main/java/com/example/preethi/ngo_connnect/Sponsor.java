package com.example.preethi.ngo_connnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.EventLog;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.preethi.NGO_Connect.R;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Sponsor extends AppCompatActivity {

    String eventName , ngoName;
    TextView tv;
    String contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor);

        try{
            Bundle b = getIntent().getExtras();
            eventName = b.getString("eventName");
            System.out.println(eventName);
        } catch(Exception ex){
            eventName = "error"; //Or some error status //
            System.out.println("Error in catch block");
        }

        tv = (TextView) findViewById(R.id.phonenum);
        Sponsor.DoGetContact doGetContact = new Sponsor.DoGetContact();
        doGetContact.execute("");

        TextView tv = (TextView) findViewById(R.id.paytm);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public class DoGetContact extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

        }
        protected void onPostExecute(String r) {
            tv.setText(contact);
        }
        @Override
        protected String doInBackground(String... params) {
            try {

                java.sql.Connection con = DatabaseConnection.getConnection();
                String query = "select phone from Events where event_name = '"+eventName+"'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);


                while (rs.next()) {
                    contact = rs.getString("phone");
                    System.out.println(contact);
                }

            }catch (Exception ex){
                Log.e("ERROR", ex.getMessage());
            }
            return "";
        }
    }
}
