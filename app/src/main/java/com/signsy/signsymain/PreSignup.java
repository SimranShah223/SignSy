package com.signsy.signsymain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class PreSignup extends AppCompatActivity {

    Button user,ngo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_pre_signup);

        user = findViewById(R.id.userType);
        ngo = findViewById(R.id.typeNGO);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PreSignup.this, UserSignup.class);
                startActivity(intent);
                finish();
            }
        });

        ngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PreSignup.this, NgoSignup.class);
                startActivity(intent);
                finish();
            }
        });
    }
}