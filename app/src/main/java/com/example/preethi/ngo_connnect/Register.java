package com.example.preethi.ngo_connnect;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.Toast;

import com.example.preethi.NGO_Connect.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.example.preethi.ngo_connnect.Login.MyPREFERENCES;
import static droidninja.filepicker.FilePickerConst.REQUEST_CODE;


public class Register extends AppCompatActivity {

    Button register, nc_certificate, nc_pictures;
    SharedPreferences sharedpreferences;
    private EditText nc_name, nc_address, nc_experience, nc_phone, nc_email, nc_password, nc_confirm_password, nc_weblink, nc_facebook, nc_linkedin, nc_domain, nc_description;
    private String name, address, experience, phone, email, password, confirm_password, weblink, facebook, linkedin, domain, description , poster;
    private static final int PICK_IMAGE = 5;
    ImageView iv;
    ImageView nc_poster;
    Uri ImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        System.out.println("In class ");
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        register = (Button) findViewById(R.id.register);
        nc_name = (EditText) findViewById(R.id.Name);
        nc_address = (EditText) findViewById(R.id.postalAddress);
        nc_experience = (EditText) findViewById(R.id.experience);
        nc_phone = (EditText) findViewById(R.id.contact);
        nc_email = (EditText) findViewById(R.id.emailid);
        nc_password = (EditText) findViewById(R.id.password);
        nc_confirm_password = (EditText) findViewById(R.id.conpass);
        nc_domain = (EditText) findViewById(R.id.domain);
        nc_description = (EditText) findViewById(R.id.description);
        nc_pictures = (Button) findViewById(R.id.uploadpics);
        nc_weblink = (EditText) findViewById(R.id.website);
        nc_facebook = (EditText) findViewById(R.id.facebookedit);
        nc_linkedin = (EditText) findViewById(R.id.linkedinedit);
        nc_poster = (ImageView) findViewById(R.id.imageView2);

        iv = (ImageView) findViewById(R.id.imageView2);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent i = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(i , 1);

                Register.DoSignup doSignup = new Register.DoSignup();
                doSignup.execute();

            }
        });
    }


    public void btnClick(View v) {
        /*
        Intent i = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i , 1);
        */
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent , "Select Picture") , REQUEST_CODE);
    }

    public void onActivityResult(int requestCode , int resultCode , Intent data) {
        super.onActivityResult(requestCode , resultCode , data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
             Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver() , uri);
             iv.setImageBitmap(bitmap);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        /*
        switch (requestCode) {
            case PICK_IMAGE:
                if(requestCode == RESULT_OK)  {
                    Uri uri = data.getData();
                    String[]projection={MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(uri , projection , null , null , null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    /**
                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    Drawable d = new BitmapDrawable(yourSelectedImage);
                    iv.setBackground(d);*/
/*
                    ImageView imageView = (ImageView) findViewById(R.id.imageView2);
                    imageView.setImageBitmap(BitmapFactory.decodeFile(filePath));
                }
                break;
        }*/
    }



    public void done(View v) {
        register();
        Register.DoSignup doSignup = new Register.DoSignup();
        doSignup.execute();
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
        Intent myIntent = new Intent(Register.this, Testing.class);
        startActivity(myIntent);
        System.out.print("In signupsuccess method");
    }

    public boolean validate() {
        boolean valid = true;

        if(name.isEmpty() ) {
            nc_name.setError("Please Enter valid name");
            valid = false;
        }

        if(address.isEmpty() || address.length() < 10) {
            nc_address.setError("Please enter full address");
            valid = false;
        }

        if(experience.isEmpty()) {
            nc_experience.setError("Please enter your experience");
            valid = false;
        }

        if(phone.isEmpty() || phone.length() < 10 ) {
            nc_phone.setError("Please enter a valid number");
            valid = false;
        }

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            nc_email.setError("Please Enter valid Email Address");
            valid = false;
        }

        if(domain.isEmpty()) {
            nc_domain.setError("Please enter your domain");
            valid = false;
        }

        if(description.isEmpty()) {
            nc_description.setError("Please describe your NGO");
            valid = false;
        }


        if(password.isEmpty() || password.length() < 8) {
            nc_password.setError("Please Enter valid password");
            valid = false;
        }

        if(confirm_password.isEmpty() || !(confirm_password.equals(password))) {
            nc_confirm_password.setError("Please Enter same as password");
            valid = false;
        }


        if(weblink.isEmpty()) {
            nc_certificate.setError("Please provide your website");
            valid = false;
        }

        if(facebook.isEmpty()) {
            nc_certificate.setError("Please provide your profile");
            valid = false;
        }

        if(linkedin.isEmpty()) {
            nc_certificate.setError("Please provide your profile");
            valid = false;
        }

        System.out.print("In validate method");

        return valid;
    }

    public void intialize() {

        name = nc_name.getText().toString().trim();
        address = nc_address.getText().toString().trim();
        experience = nc_experience.getText().toString().trim();
        phone = nc_phone.getText().toString().trim();
        email = nc_email.getText().toString().trim();
        password = nc_password.getText().toString().trim();
        confirm_password = nc_confirm_password.getText().toString().trim();
        domain = nc_domain.getText().toString().trim();
        description = nc_description.getText().toString().trim();
        weblink = nc_weblink.getText().toString().trim();
        facebook = nc_facebook.getText().toString().trim();
        linkedin = nc_linkedin.getText().toString().trim();

        System.out.print("In initalize method");

    }


    public class DoSignup extends AsyncTask<String,Void,String> {
        String z = "";
        Boolean isSuccess = false;
        String name = nc_name.getText().toString().trim();
        String address = nc_address.getText().toString().trim();
        String experience = nc_experience.getText().toString().trim();
        String phone = nc_phone.getText().toString().trim();
        String email = nc_email.getText().toString().trim();
        String password = nc_password.getText().toString().trim();
        String confirm_password = nc_confirm_password.getText().toString().trim();
        String domian = nc_domain.getText().toString().trim();
        String description = nc_description.getText().toString().trim();
        String weblink = nc_weblink.getText().toString().trim();
        String facebook = nc_facebook.getText().toString().trim();
        String linkedin = nc_linkedin.getText().toString().trim();
        private ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(Register.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(String r) {

            Toast.makeText(Register.this, r, Toast.LENGTH_SHORT).show();

            if (isSuccess) {
                Intent i = new Intent(Register.this, Login.class);
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
                System.out.println("Connection Established to database");
                if (con == null) {
                    z = "Error in connection with SQL server";
                    System.out.println("Connection Error!!");
                } else {
                    String query = "select * from RegisterNgo where name LIKE '" + name + "' and phone LIKE '"+ phone +"'";
                    System.out.println(query);
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        z = "User Already Exists";
                        isSuccess = false;

                    } else {

                        int flag = stmt.executeUpdate("insert into RegisterNgo (name, address , experience , phone , email ,  password , weblink , facebook , linkedin , domain , description , poster) values('" + name + "' , '" + address + "' , '" + experience + "' , '" + phone + "' , '" + email + "' , '" + password + "'  , '" + weblink + "' , '" + facebook + "' , '" + linkedin + "' , '" + domain + "' , '" + description + "' , '"+ iv +"' );");
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
