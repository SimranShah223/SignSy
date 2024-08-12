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

public class UserProfile extends AppCompatActivity {

    ImageView backButt, homeButt, dictButt, profileButt, searchButt;
    TextView userName,userType;
    Button ratingButt,logoutButt,editProfile,changePassword;

    String UserID;

    FirebaseAuth auth;
    FirebaseFirestore FStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_profile);

        backButt = findViewById(R.id.backButt4);
        homeButt = findViewById(R.id.nbarHome5_1);
        dictButt = findViewById(R.id.nbarDictionary5_1);
        profileButt = findViewById(R.id.nbarProfile5_1);
        searchButt = findViewById(R.id.nbarSearch5_1);

        userName = findViewById(R.id.userName);
        userType = findViewById(R.id.userType);

        ratingButt = findViewById(R.id.ratingButt);
        logoutButt = findViewById(R.id.logoutButt);
        editProfile = findViewById(R.id.editProfile);
        changePassword = findViewById(R.id.changePassword);

        auth = FirebaseAuth.getInstance();

        FStore = FirebaseFirestore.getInstance();
        UserID = auth.getCurrentUser().getUid();

        DocumentReference documentReference = FStore.collection("UserInfo").document(UserID);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                userName.setText(documentSnapshot.getString("FirstName"));
                userType.setText(documentSnapshot.getString("UType"));

            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(UserProfile.this, EditUserProfile.class));
                finish();
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(UserProfile.this, forgotPassword.class));
                finish();
            }
        });

        ratingButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(UserProfile.this, Rating.class));
                finish();
            }
        });

        logoutButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(UserProfile.this, Login.class));
                finish();

            }
        });

        backButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserProfile.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        homeButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserProfile.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        dictButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserProfile.this, SignDictionary.class);
                startActivity(intent);
                finish();
            }
        });

        profileButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(UserProfile.this, UserProfile.class));
                finish();
            }
        });

        searchButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(UserProfile.this, ActiveDonations.class));
                finish();
            }
        });
    }
}