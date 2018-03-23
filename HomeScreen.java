package com.example.preethi.ngo_connnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.preethi.NGO_Connect.R;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void regNGO(View view) {
        Intent intent = new Intent(getApplicationContext(), Testing.class);
        startActivity(intent);

    }

    public void lfe(View view) {
        Intent intent = new Intent(getApplicationContext(), Testing.class);
        startActivity(intent);
    }
}
