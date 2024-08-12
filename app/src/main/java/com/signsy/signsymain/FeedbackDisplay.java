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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FeedbackDisplay extends AppCompatActivity {

    ImageView backButt, homeButt, searchButt, profileButt;
    String UserID, UserOrNgo,un;

    FirebaseAuth auth;
    FirebaseFirestore FStore;

    RecyclerView recyclerView;
    ArrayList<fItem> fItemArraylist;
    fAdapter fAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_feedback_display);

        backButt = findViewById(R.id.backButt14);
        homeButt = findViewById(R.id.nbarHome14);
        searchButt = findViewById(R.id.nbarSearch14);
        profileButt = findViewById(R.id.nbarProfile14);

        auth = FirebaseAuth.getInstance();
        FStore = FirebaseFirestore.getInstance();
        UserID = auth.getCurrentUser().getUid();

        recyclerView = findViewById(R.id.feedbackRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fItemArraylist = new ArrayList<fItem>();
        fAdapter = new fAdapter(FeedbackDisplay.this,fItemArraylist);
        recyclerView.setAdapter(fAdapter);
        EventChangeListener();

        DocumentReference documentReference = FStore.collection("UserInfo").document(UserID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                UserOrNgo = documentSnapshot.getString("UType");
                if(UserOrNgo.equals("NGO")){
                    un = "N";
                }else if(UserOrNgo.equals("Admin")){
                    un = "A";
                }else{
                    un = "U";
                }
            }
        });

        backButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FeedbackDisplay.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        homeButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FeedbackDisplay.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        searchButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FeedbackDisplay.this, ActiveDonations.class);
                startActivity(intent);
                finish();
            }
        });

        profileButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(un.equals("U")){
                    startActivity(new Intent(FeedbackDisplay.this, UserProfile.class));
                }else if(un.equals("A")){
                    startActivity(new Intent(FeedbackDisplay.this, AdminProfile.class));
                }
                else {
                    startActivity(new Intent(FeedbackDisplay.this, NGOProfile.class));
                }
                finish();
            }
        });
    }
    private void EventChangeListener() {
        FStore.collection("RateInfo").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.e("Firestore error : ", error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        fItemArraylist.add(dc.getDocument().toObject(fItem.class));
                    }
                    fAdapter.notifyDataSetChanged();
                }
                for (DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.MODIFIED){
                        fItemArraylist.add(dc.getDocument().toObject(fItem.class));
                    }
                    fAdapter.notifyDataSetChanged();
                }
                for (DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.REMOVED){
                        fItemArraylist.add(dc.getDocument().toObject(fItem.class));
                    }
                    fAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}