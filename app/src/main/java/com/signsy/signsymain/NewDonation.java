package com.signsy.signsymain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class NewDonation extends AppCompatActivity {

    ImageView backButt, homeButt, dictButt, profileButt, searchButt;
    TextView donationDesc, donationCapacity, startDate, endDate, ngoName3;
    Button button;

    String UserID;

    FirebaseAuth auth;
    FirebaseFirestore FStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_donation);

        backButt = findViewById(R.id.backButt10);
        homeButt = findViewById(R.id.nbarHome6_1);
        dictButt = findViewById(R.id.nbarDictionary6_1);
        profileButt = findViewById(R.id.nbarProfile6_1);
        searchButt = findViewById(R.id.nbarSearch6_1);

        donationDesc = findViewById(R.id.donationDesc);
        donationCapacity = findViewById(R.id.donationCapacity);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        ngoName3 = findViewById(R.id.ngoName3);

        button = findViewById(R.id.button);

        auth = FirebaseAuth.getInstance();

        FStore = FirebaseFirestore.getInstance();
        UserID = auth.getCurrentUser().getUid();

        DocumentReference documentReference = FStore.collection("UserInfo").document(UserID);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                ngoName3.setText(documentSnapshot.getString("FirstName"));

            }
        });

        backButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewDonation.this, NGOProfile.class);
                startActivity(intent);
                finish();
            }
        });

        homeButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewDonation.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        dictButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewDonation.this, SignDictionary.class);
                startActivity(intent);
                finish();
            }
        });

        profileButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewDonation.this, NgoSignup.class));
                finish();
            }
        });

        searchButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewDonation.this, ActiveDonations.class));
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name, cap, desc, StartDate, EndDate;

                name = ngoName3.getText().toString().trim();
                cap = donationCapacity.getText().toString().trim();
                desc = donationDesc.getText().toString().trim();
                StartDate = startDate.getText().toString().trim();
                EndDate = endDate.getText().toString().trim();

                if (name == null) {
                    Toast.makeText(NewDonation.this, "please enter name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(cap)) {
                    Toast.makeText(NewDonation.this, "please enter capacity!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(desc)) {
                    Toast.makeText(NewDonation.this, "please enter details!!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(StartDate)) {
                    Toast.makeText(NewDonation.this, "please enter start date", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(EndDate)) {
                    Toast.makeText(NewDonation.this, "please enter end date", Toast.LENGTH_SHORT).show();
                } else {

                    DonationEntry(name, cap, desc, StartDate, EndDate);
                    DonationEn(name, cap, desc, StartDate, EndDate);

                }
            }
        });
    }

    private void DonationEntry(String name, String cap, String desc, String StartDate, String EndDate) {

        DocumentReference documentReference = FStore.collection("DonaInfo").document(UserID).collection("PersonalDonation").document();

        Map<String, Object> DonaInfo = new HashMap<>();

        DonaInfo.put("FirstName", name);
        DonaInfo.put("Capacity", cap);
        DonaInfo.put("Description", desc);
        DonaInfo.put("StartDate", StartDate);
        DonaInfo.put("EndDate", EndDate);

        documentReference.set(DonaInfo).addOnSuccessListener(NewDonation.this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText(NewDonation.this, "Registration of Donation is successful!!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(NewDonation.this, NGOProfile.class));

            }
        }).addOnFailureListener(NewDonation.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(NewDonation.this, "Registration is Failed!!!", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void DonationEn(String name, String cap, String desc, String StartDate, String EndDate) {

        DocumentReference documentReference = FStore.collection("DonaAllInfo").document();

        Map<String, Object> DonaInfo = new HashMap<>();

        DonaInfo.put("FirstName", name);
        DonaInfo.put("Capacity", cap);
        DonaInfo.put("Description", desc);
        DonaInfo.put("StartDate", StartDate);
        DonaInfo.put("EndDate", EndDate);

        documentReference.set(DonaInfo).addOnSuccessListener(NewDonation.this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText(NewDonation.this, "Registration of Donation is successful!!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(NewDonation.this, NGOProfile.class));

            }
        }).addOnFailureListener(NewDonation.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(NewDonation.this, "Registration is Failed!!!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}