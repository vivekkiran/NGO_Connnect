package com.example.preethi.ngo_connnect;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.preethi.NGO_Connect.R;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.preethi.ngo_connnect.Login.MyPREFERENCES;

public class NgoDetails extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    DatabaseConnection databaseConnection;
    NgoDetailsAdapter ListAdapter;
    ArrayList<String> NgoName_ArrayList = new ArrayList<String>();
    ArrayList<String> NgoDescription_ArrayList = new ArrayList<String>();
    ArrayList<String> NgoAddress_ArrayList = new ArrayList<String>();
    ArrayList<String> NgoEmail_ArrayList = new ArrayList<String>();
    ArrayList<String> NgoContact_ArrayList = new ArrayList<String>();
    ArrayList<String> NgoExperience_ArrayList = new ArrayList<String>();
    ArrayList<String> NgoWebsite_ArrayList = new ArrayList<String>();
    ArrayList<String> NgoFacebook_ArrayList = new ArrayList<String>();
    ArrayList<String> NgoLinkedin_ArrayList = new ArrayList<String>();

    ListView LISTVIEW;
    String ngoname;
    int flag = 0;

    ResultSet rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_details);

        Bundle bundle = getIntent().getExtras();
        ngoname = bundle.getString("ngon");
        System.out.println(ngoname);

        LISTVIEW = (ListView) findViewById(R.id.ndlistview);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        NgoDetails.DisplayNgoDetails doGetNgodetails = new NgoDetails.DisplayNgoDetails();
        doGetNgodetails.execute("");
    }

    public class DisplayNgoDetails extends AsyncTask<String, String, String> {

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

                String query = "select name , description , address , email , phone , experience , weblink , facebook , linkedin from RegisterNgo where name = '"+ngoname+"' ";
                System.out.println(query);
                Statement stmt = con.createStatement();
                System.out.println("After Statement stmt");
                rs = stmt.executeQuery(query);
                System.out.println("Executing ResultSet statement");

                while (rs.next()) {

                    flag = 1;
                    NgoName_ArrayList.add(rs.getString("name"));
                    NgoDescription_ArrayList.add(rs.getString("description"));
                    NgoAddress_ArrayList.add(rs.getString("address"));
                    NgoEmail_ArrayList.add(rs.getString("email"));
                    NgoContact_ArrayList.add(rs.getString("phone"));
                    NgoExperience_ArrayList.add(rs.getString("experience"));
                    NgoWebsite_ArrayList.add(rs.getString("weblink"));
                    NgoFacebook_ArrayList.add(rs.getString("facebook"));
                    NgoLinkedin_ArrayList.add(rs.getString("linkedin"));

                    //Log.i("Query   BloodGroup", rs.getString("BloodGroup"));
                    //Log.i("Query   DateOfRequests", rs.getString("DateOfRequest"));
                }
                if(flag == 0){
                    Toast.makeText(NgoDetails.this, "No details to show", Toast.LENGTH_SHORT).show();

                }

                ListAdapter = new NgoDetailsAdapter(NgoDetails.this ,
                        NgoName_ArrayList,
                        NgoDescription_ArrayList,
                        NgoAddress_ArrayList ,
                        NgoEmail_ArrayList,
                        NgoContact_ArrayList,
                        NgoExperience_ArrayList,
                        NgoWebsite_ArrayList,
                        NgoFacebook_ArrayList ,
                        NgoLinkedin_ArrayList
                );
            }catch (Exception ex){
                Log.e("ERROR", ex.getMessage());
            }
            return "";
        }

    }
}
