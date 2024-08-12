package com.signsy.signsymain;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UsersInfo extends AppCompatActivity {
    ImageView backButt, homeButt, dictButt, profileButt, searchButt;

    FirebaseAuth auth;
    FirebaseFirestore FStore;

    RecyclerView recyclerView;
    ArrayList<UInfoCard> uItemArraylist;
    UInfoAdapter uinfoAdapter;
    String UserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_users_info);

        backButt = findViewById(R.id.backButt17);
        homeButt = findViewById(R.id.nbarHome17_1);
        dictButt = findViewById(R.id.nbarDictionary17_1);
        profileButt = findViewById(R.id.nbarProfile17_1);
        searchButt = findViewById(R.id.nbarSearch17_1);

        auth = FirebaseAuth.getInstance();
        FStore = FirebaseFirestore.getInstance();
        UserID = auth.getCurrentUser().getUid();

        recyclerView = findViewById(R.id.userInfoRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        uItemArraylist = new ArrayList<UInfoCard>();
        uinfoAdapter = new UInfoAdapter(UsersInfo.this,uItemArraylist);
        recyclerView.setAdapter(uinfoAdapter);
        EventChangeListener();


        backButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsersInfo.this, AdminProfile.class);
                startActivity(intent);
                finish();
            }
        });

        homeButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UsersInfo.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        dictButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UsersInfo.this, SignDictionary.class);
                startActivity(intent);
                finish();
            }
        });

        profileButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UsersInfo.this, AdminProfile.class));
                finish();
            }
        });

        searchButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UsersInfo.this, ActiveDonations.class));
                finish();
            }
        });
    }
    private void EventChangeListener() {
        FStore.collection("UserInfo").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.e("Firestore error : ", error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        uItemArraylist.add(dc.getDocument().toObject(UInfoCard.class));
                    }
                    if(dc.getType() == DocumentChange.Type.MODIFIED){
                        uItemArraylist.add(dc.getDocument().toObject(UInfoCard.class));
                    }
                    if(dc.getType() == DocumentChange.Type.REMOVED){
                        uItemArraylist.add(dc.getDocument().toObject(UInfoCard.class));
                    }
                    uinfoAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}