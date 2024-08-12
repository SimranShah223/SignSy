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

public class SignDictionary extends AppCompatActivity {

    ImageView backButt, homeButt, searchButt, profileButt;
    ImageView signThumbnail;

    String UserID, UserOrNgo,un;

    FirebaseAuth auth;
    FirebaseFirestore FStore;

    RecyclerView recyclerView;
    ArrayList<SItem> SItemArraylist;
    SAdapter sAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_dictionary);

        backButt = findViewById(R.id.backButt2);
        homeButt = findViewById(R.id.nbarHome3);
        searchButt = findViewById(R.id.nbarSearch3);
        profileButt = findViewById(R.id.nbarProfile3);

        //signThumbnail = findViewById(R.id.signThumbnail);

        auth = FirebaseAuth.getInstance();
        FStore = FirebaseFirestore.getInstance();
        UserID = auth.getCurrentUser().getUid();

        recyclerView = findViewById(R.id.recyclerview3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SItemArraylist = new ArrayList<SItem>();
        sAdapter = new SAdapter(SignDictionary.this,SItemArraylist);
        recyclerView.setAdapter(sAdapter);
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



//        signThumbnail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(SignDictionary.this, SignDescription.class));
//            }
//        });

        backButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignDictionary.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        homeButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignDictionary.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        searchButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignDictionary.this, ActiveDonations.class);
                startActivity(intent);
                finish();
            }
        });

        profileButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(un.equals("U")){
                    startActivity(new Intent(SignDictionary.this, UserProfile.class));
                } else if(un.equals("A")){
                    startActivity(new Intent(SignDictionary.this, AdminProfile.class));
                } else {
                    startActivity(new Intent(SignDictionary.this, NGOProfile.class));
                }
                finish();
            }
        });
    }
    private void EventChangeListener() {
        FStore.collection("SignInfo").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.e("Firestore error : ", error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        SItemArraylist.add(dc.getDocument().toObject(SItem.class));
                    }
                    sAdapter.notifyDataSetChanged();
                }
                for (DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.MODIFIED){
                        SItemArraylist.add(dc.getDocument().toObject(SItem.class));
                    }
                    sAdapter.notifyDataSetChanged();
                }
                for (DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.REMOVED){
                        SItemArraylist.add(dc.getDocument().toObject(SItem.class));
                    }
                    sAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}