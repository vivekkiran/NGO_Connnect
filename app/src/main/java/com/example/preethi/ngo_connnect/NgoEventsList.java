package com.example.preethi.ngo_connnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.preethi.NGO_Connect.R;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class NgoEventsList extends AppCompatActivity {


    private TextView mTextMessage;

    SharedPreferences sharedpreferences;

    DatabaseConnection databaseConnection;
    CustomAdapter ListAdapter ;
    ArrayList<String> Event_Arraylist = new ArrayList<String>();
    ArrayList<String> Category_ArrayList = new ArrayList<String>();
    ArrayList<android.media.Image> Poster_ArrayList = new ArrayList<Image>();
    ListView LISTVIEW;
    ImageView poster_event;
    TextView engo , event_name;
    Button knowmore;
    String eventname , domain;
    Image poster;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_ngo:
                    Intent intent1 = new Intent(getApplicationContext(), Ngos.class);
                    startActivity(intent1);
                    return true;
                case R.id.navigation_addevent:
                    Intent intent2 = new Intent(getApplicationContext(), AddEvent.class);
                    startActivity(intent2);
                    return true;
                case R.id.navigation_profile:
                    Intent intent3 = new Intent(getApplicationContext(), Profile.class);
                    startActivity(intent3);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_events_list);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        LISTVIEW = (ListView) findViewById(R.id.eventslistview);
        NgoEventsList.DoGetEvents doGetEventsList = new NgoEventsList.DoGetEvents();
        doGetEventsList.execute("");

        knowmore = (Button) findViewById(R.id.km);
        event_name = (TextView) findViewById(R.id.eventname);
    }

    public void Click(View view) {
        Intent myIntent = new Intent(NgoEventsList.this, NgoEventDetails.class);
        // @SuppressLint("WrongViewCast")
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.eventname);
        String getEventName = autoCompleteTextView.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString("eventName", getEventName);
        myIntent.putExtras(bundle);
        startActivity(myIntent);
    }


    public class DoGetEvents extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

        }
        protected void onPostExecute(String r) {
            LISTVIEW.setAdapter(ListAdapter);
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                java.sql.Connection con = DatabaseConnection.getConnection();
                System.out.println("Connection Established");
                String query = "select event_name , organisation from Events";
                System.out.println(query);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                Event_Arraylist.clear();
                Category_ArrayList.clear();
                Poster_ArrayList.clear();
                /*if(!(rs.next())){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ListOfRequests.this);
                    dialog.setCancelable(false);
                    dialog.setTitle("Alert");
                    dialog.setMessage("Sorry No requests to show" );
                }*/

                while (rs.next()) {

                    Event_Arraylist.add(rs.getString("event_name"));
                    Category_ArrayList.add(rs.getString("organisation"));
                }

                ListAdapter = new CustomAdapter(NgoEventsList.this,

                        Event_Arraylist,
                        Category_ArrayList,
                        Poster_ArrayList
                );

            }catch (Exception ex){
                Log.e("ERROR", ex.getMessage());
            }
            return "";
        }

    }

}
