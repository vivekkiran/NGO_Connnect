package com.example.preethi.ngo_connnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.preethi.NGO_Connect.R;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.preethi.ngo_connnect.Login.MyPREFERENCES;

public class NgoEventDetails extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    DatabaseConnection databaseConnection;
    EventDetailsAdapter ListAdapter;
    ArrayList<String> EventName_ArrayList = new ArrayList<String>();
    ArrayList<String> Amount_ArrayList = new ArrayList<String>();
    ArrayList<String> Organisation_ArrayList = new ArrayList<String>();
    ArrayList<String> Location_ArrayList = new ArrayList<String>();
    ArrayList<String> Date_ArrayList = new ArrayList<String>();
    ArrayList<String> Time_ArrayList = new ArrayList<String>();
    ArrayList<String> Description_ArrayList = new ArrayList<String>();
    ListView LISTVIEW;
    String eventName;
    int flag = 0;
    ResultSet rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_event_details);

        Bundle bundle = getIntent().getExtras();
        eventName = bundle.getString("eventName");
        System.out.println(eventName);
        LISTVIEW = (ListView) findViewById(R.id.lv);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        NgoEventDetails.DisplayEventDetails doGetEventsList = new NgoEventDetails.DisplayEventDetails();
        doGetEventsList.execute("");
    }

    public void collaborate(View view) {
        //Intent myIntent = new Intent(EventDetails.this, Join.class);
        //startActivity(myIntent);
    }

    public class DisplayEventDetails extends AsyncTask<String, String, String> {

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

                String query = "select event_name , organisation , cost , location , date , time , description from Events where event_name='"+eventName+"'";
                System.out.println(query);
                Statement stmt = con.createStatement();
                System.out.println("After Statement stmt");
                rs = stmt.executeQuery(query);
                System.out.println("Executing ResultSet statement");

                while (rs.next()) {
                    flag = 1;
                    EventName_ArrayList.add(rs.getString("event_name"));
                    Organisation_ArrayList.add(rs.getString("organisation"));
                    Amount_ArrayList.add(rs.getString("cost"));
                    Location_ArrayList.add(rs.getString("location"));
                    Date_ArrayList.add(rs.getString("date"));
                    Time_ArrayList.add(rs.getString("time"));
                    Description_ArrayList.add(rs.getString("description"));

                    //Log.i("Query   BloodGroup", rs.getString("BloodGroup"));
                    //Log.i("Query   DateOfRequests", rs.getString("DateOfRequest"));
                }
                if(flag == 0){
                    Toast.makeText(NgoEventDetails.this, "No details to show", Toast.LENGTH_SHORT).show();

                }

                ListAdapter = new EventDetailsAdapter(NgoEventDetails.this,
                        EventName_ArrayList,
                        Organisation_ArrayList,
                        Amount_ArrayList ,
                        Location_ArrayList,
                        Date_ArrayList,
                        Time_ArrayList,
                        Description_ArrayList
                );
            }catch (Exception ex){
                Log.e("ERROR", ex.getMessage());
            }
            return "";
        }

    }
}
