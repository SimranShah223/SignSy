package com.signsy.signsymain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class DonationDescription extends AppCompatActivity {

    ImageView backButt, homeButt, dictButt, profileButt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_description);

        backButt = findViewById(R.id.backButt12);
        homeButt = findViewById(R.id.nbarHome12);
        dictButt = findViewById(R.id.nbarDictionary12);
        profileButt = findViewById(R.id.nbarProfile12);
    }
}