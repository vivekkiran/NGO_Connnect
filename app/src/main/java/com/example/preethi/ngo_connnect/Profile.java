package com.example.preethi.ngo_connnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.preethi.NGO_Connect.R;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.preethi.ngo_connnect.Login.MyPREFERENCES;

public class Profile extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    DatabaseConnection databaseConnection;
    ProfileAdapter ListAdapter;
    ArrayList<String> UserName_ArrayList = new ArrayList<String>();
    ArrayList<String> Description_ArrayList = new ArrayList<String>();
    ArrayList<String> Address_ArrayList = new ArrayList<String>();
    ArrayList<String> Email_ArrayList = new ArrayList<String>();
    ArrayList<String> Contact_ArrayList = new ArrayList<String>();
    ArrayList<String> Experience_ArrayList = new ArrayList<String>();
    ArrayList<String> Website_ArrayList = new ArrayList<String>();
    ArrayList<String> Facebook_ArrayList = new ArrayList<String>();
    ArrayList<String> Linkedin_ArrayList = new ArrayList<String>();

    ListView LISTVIEW;
    String eventName;
    int flag = 0;
    Button editprofile;

    ResultSet rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        LISTVIEW = (ListView) findViewById(R.id.profile);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Profile.DisplayProfileDetails doGetEventsList = new Profile.DisplayProfileDetails();
        doGetEventsList.execute("");

        editprofile = (Button) findViewById(R.id.editpro);
    }

    public void editProfile(View view) {
        Intent myIntent = new Intent(Profile.this, Sponsor.class);
        startActivity(myIntent);
    }

    public class DisplayProfileDetails extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

        }
        protected void onPostExecute(String r) {
            // Log.i("EventDetails","eee");
            LISTVIEW.setAdapter(ListAdapter);
        }
        @Override
        protected String doInBackground(String... params) {
            try {

                java.sql.Connection con = DatabaseConnection.getConnection();
                System.out.println("Connection Established");

                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                String email = sharedpreferences.getString("Email","");
                System.out.println(email);


                String query = "select name , description , address , email , phone , experience , weblink , facebook , linkedin from RegisterNgo where email = '"+email+"' ";
                System.out.println(query);
                Statement stmt = con.createStatement();
                System.out.println("After Statement stmt");
                rs = stmt.executeQuery(query);
                System.out.println("Executing ResultSet statement");

                while (rs.next()) {
                    /*
                    System.out.println("In while loop");
                    String event = rs.getString("event_name");
                    String ngo = rs.getString("organisation");
                    String location = rs.getString("location");
                    String date = rs.getString("date");
                    System.out.println(event);
                    */
                    flag = 1;
                    UserName_ArrayList.add(rs.getString("name"));
                    Description_ArrayList.add(rs.getString("description"));
                    Address_ArrayList.add(rs.getString("address"));
                    Email_ArrayList.add(rs.getString("email"));
                    Contact_ArrayList.add(rs.getString("phone"));
                    Experience_ArrayList.add(rs.getString("experience"));
                    Website_ArrayList.add(rs.getString("weblink"));
                    Facebook_ArrayList.add(rs.getString("facebook"));
                    Linkedin_ArrayList.add(rs.getString("linkedin"));

                    //Log.i("Query   BloodGroup", rs.getString("BloodGroup"));
                    //Log.i("Query   DateOfRequests", rs.getString("DateOfRequest"));
                }
                if(flag == 0){
                    Toast.makeText(Profile.this, "No details to show", Toast.LENGTH_SHORT).show();

                }

                ListAdapter = new ProfileAdapter(Profile.this ,
                        UserName_ArrayList,
                        Description_ArrayList,
                        Address_ArrayList ,
                        Email_ArrayList,
                        Contact_ArrayList,
                        Experience_ArrayList,
                        Website_ArrayList,
                        Facebook_ArrayList ,
                        Linkedin_ArrayList
                );
            }catch (Exception ex){
                Log.e("ERROR", ex.getMessage());
            }
            return "";
        }

    }
}
