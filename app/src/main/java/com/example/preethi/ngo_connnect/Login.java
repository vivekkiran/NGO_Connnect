package com.example.preethi.ngo_connnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.preethi.NGO_Connect.R;

import org.w3c.dom.Text;

import java.sql.ResultSet;
import java.sql.Statement;

public class Login extends AppCompatActivity {
    DatabaseConnection databaseConnection;
    EditText name, password , email;
    Button loginok;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    Boolean check = false;
    //public static final String ContactDetails = "ContactDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseConnection = new DatabaseConnection();
        email = (EditText) findViewById(R.id.emailid);
        password = (EditText)findViewById(R.id.pass);
        loginok = (Button)findViewById(R.id.button);


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        loginok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Login.DoLogin doLogin = new Login.DoLogin();
                doLogin.execute();

            }
        });
    }



    public class DoLogin extends AsyncTask<String,String,String>
    {
        String z = "";
        String role, cd;
        Boolean isSuccess = false;
        String str_email = email.getText().toString();
        String str_password = password.getText().toString();



        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {

            Toast.makeText(Login.this,r,Toast.LENGTH_SHORT).show();

            if(isSuccess) {
                Intent intent = new Intent(getApplicationContext() , NgoEventsList.class);
                startActivity(intent);

            }
        }

        @Override
        protected String doInBackground(String... params) {
            if(str_password.trim().equals("") ||str_email.trim().equals("")){
                z = "Please fill all the fields";
            }else{
                try {
                    java.sql.Connection con = DatabaseConnection.getConnection();
                    System.out.println(con);
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    }else{
                        String query = "select * from RegisterNgo where email LIKE '"+str_email+"' and password LIKE '"+str_password+"'";
                        System.out.println(query);
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if(rs.next()){
                            z ="Login Successful!";
                            System.out.println("Done!");
                            isSuccess = true;
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("Email", rs.getString("email"));
                            editor.putString("Password", rs.getString("password"));
                            editor.apply();
                        }else{
                            z = "Invalid Credentials";
                            System.out.println("Invalid!");
                            isSuccess = false;
                        }
                    }


                }catch (Exception ex)
                {
                    isSuccess = false;
                    z = "Exceptions";
                    Log.e("ERROR", ex.getMessage());

                }
            }
            return z;
        }
    }
}