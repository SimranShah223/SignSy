package com.signsy.signsymain;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class AdminProfile extends AppCompatActivity {

    ImageView backButt, homeButt, dictButt, profileButt, searchButt;
    TextView adminName;
    Button userInfoButt,logoutButt,editProfile,changePassword,feedbackDisplayButt,appReportButt;

    String UserID;

    FirebaseAuth auth;
    FirebaseFirestore FStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_admin_profile);

        backButt = findViewById(R.id.backButt16);
        homeButt = findViewById(R.id.nbarHome16_1);
        dictButt = findViewById(R.id.nbarDictionary16_1);
        profileButt = findViewById(R.id.nbarProfile16_1);
        searchButt = findViewById(R.id.nbarSearch16_1);

        adminName = findViewById(R.id.adminName);

        userInfoButt = findViewById(R.id.userInfoButt);
        logoutButt = findViewById(R.id.logoutaButt);
        editProfile = findViewById(R.id.editaProfile);
        changePassword = findViewById(R.id.changeaPassword);
        feedbackDisplayButt = findViewById(R.id.feedbackDisplayButt);
        appReportButt = findViewById(R.id.appReportsButt);

        auth = FirebaseAuth.getInstance();

        FStore = FirebaseFirestore.getInstance();
        UserID = auth.getCurrentUser().getUid();

        DocumentReference documentReference = FStore.collection("UserInfo").document(UserID);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                adminName.setText(documentSnapshot.getString("FirstName"));
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AdminProfile.this, EditUserProfile.class));
                finish();
            }
        });

        userInfoButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminProfile.this, AdminReports.class));
                finish();
            }
        });

        feedbackDisplayButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminProfile.this, FeedbackDisplay.class));
                finish();
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminProfile.this, forgotPassword.class));
                finish();
            }
        });

        appReportButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminProfile.this, AdminReport.class));
                finish();
            }
        });

        logoutButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AdminProfile.this, Login.class));
                finish();

            }
        });

        backButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminProfile.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        homeButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminProfile.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        dictButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminProfile.this, SignDictionary.class);
                startActivity(intent);
                finish();
            }
        });

        profileButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminProfile.this, AdminProfile.class));
                finish();
            }
        });

        searchButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AdminProfile.this, ActiveDonations.class));
                finish();
            }
        });
    }
}