package com.signsy.signsymain;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class NGOProfile extends AppCompatActivity {

    ImageView backButt, homeButt, dictButt, profileButt, searchButt;
    Button editNgoProfile,changeNgoPassword,newDonationButton,donationHistoryButton,ngoRatingButt,ngoLogoutButt;
    TextView ngoName1,ngoType1;

    String UserID;

    FirebaseAuth auth;
    FirebaseFirestore FStore;

    String File_name = "login_status.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_ngoprofile);

        backButt = findViewById(R.id.backButt7);
        homeButt = findViewById(R.id.nbarHome6);
        dictButt = findViewById(R.id.nbarDictionary6);
        profileButt = findViewById(R.id.nbarProfile6);
        searchButt = findViewById(R.id.nbarSearch6);

        editNgoProfile = findViewById(R.id.editNgoProfile);
        changeNgoPassword = findViewById(R.id.changeNgoPassword);
        newDonationButton = findViewById(R.id.newDonationButton);
        donationHistoryButton = findViewById(R.id.donationHistoryButton);
        ngoRatingButt = findViewById(R.id.ngoRatingButt);
        ngoLogoutButt = findViewById(R.id.ngoLogoutButt);

        ngoName1 = findViewById(R.id.ngoName1);
        ngoType1 = findViewById(R.id.ngoType1);

        auth = FirebaseAuth.getInstance();

        FStore = FirebaseFirestore.getInstance();
        UserID = auth.getCurrentUser().getUid();

        DocumentReference documentReference = FStore.collection("UserInfo").document(UserID);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                ngoName1.setText(documentSnapshot.getString("FirstName"));
                ngoType1.setText(documentSnapshot.getString("NType"));

            }
        });


        editNgoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NGOProfile.this, EditNgoProfile.class));
                finish();
            }
        });

        changeNgoPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NGOProfile.this, forgotPassword.class));
                finish();
            }
        });

        newDonationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NGOProfile.this, NewDonation.class));
                finish();
            }
        });

        donationHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NGOProfile.this, DonationHistory.class));
                finish();
            }
        });

        ngoRatingButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NGOProfile.this, Rating.class));
                finish();
            }
        });

        ngoLogoutButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileOutputStream fos = null;

                try {
                    fos = openFileOutput(File_name, MODE_PRIVATE);
                    fos.write("Not Logged".getBytes());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(fos!=null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                startActivity(new Intent(NGOProfile.this, Login.class));
                finish();
            }
        });

        backButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NGOProfile.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        homeButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NGOProfile.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        dictButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NGOProfile.this, SignDictionary.class);
                startActivity(intent);
                finish();
            }
        });

        profileButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NGOProfile.this, UserProfile.class));
                finish();
            }
        });

        searchButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NGOProfile.this, ActiveDonations.class));
                finish();
            }
        });
    }
}