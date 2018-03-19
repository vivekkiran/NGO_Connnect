package com.example.preethi.ngo_connnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.example.preethi.NGO_Connect.R;

public class Sponsor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor);
        TextView tv = (TextView) findViewById(R.id.paytm);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
