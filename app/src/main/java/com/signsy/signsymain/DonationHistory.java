package com.signsy.signsymain;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DonationHistory extends AppCompatActivity {

    ImageView backButt, homeButt, dictButt, profileButt, searchButt;
    RecyclerView recyclerView;
    ArrayList<DHitem> dItemArrayList;
    DHAdapter dHAdapter;
    String UserID, UserOrNgo,un;
    TextView ngoID;
    FirebaseAuth auth;
    FirebaseFirestore FStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_donation_history);

        backButt = findViewById(R.id.backButt11);
        homeButt = findViewById(R.id.nbarHome5_2);
        dictButt = findViewById(R.id.nbarDictionary5_2);
        profileButt = findViewById(R.id.nbarProfile5_2);
        searchButt = findViewById(R.id.nbarSearch5_2);
        auth = FirebaseAuth.getInstance();
        FStore = FirebaseFirestore.getInstance();
        UserID = auth.getCurrentUser().getUid();
        recyclerView = findViewById(R.id.recyclerview2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dItemArrayList = new ArrayList<DHitem>();
        dHAdapter = new DHAdapter(DonationHistory.this,dItemArrayList);
        recyclerView.setAdapter(dHAdapter);
        ngoID = findViewById(R.id.ngoID);
        DocumentReference documentReference = FStore.collection("UserInfo").document(UserID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                ngoID.setText(documentSnapshot.getString("FirstName"));
            }
        });
        EventChangeListener();
        backButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DonationHistory.this, NGOProfile.class);
                startActivity(intent);
                finish();
            }
        });
        homeButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DonationHistory.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        dictButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DonationHistory.this, SignDictionary.class);
                startActivity(intent);
                finish();
            }
        });

        profileButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DonationHistory.this, NGOProfile.class));
                finish();
            }
        });

        searchButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DonationHistory.this, ActiveDonations.class));
                finish();
            }
        });
    }
    private void EventChangeListener() {
        FStore.collection("DonaInfo").document(UserID).collection("PersonalDonation").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.e("Firestore error : ", error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        dItemArrayList.add(dc.getDocument().toObject(DHitem.class));
                    }
                    dHAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}