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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

public class ActiveDonations extends AppCompatActivity {

    ImageView backButt, homeButt, dictButt, profileButt;
    Button applyButton1;
    TextView totalCount;

    RecyclerView recyclerView;
    ArrayList<DItem> dItemArrayList;
    DAdapter dAdapter;
    String UserID, UserOrNgo,un;

    FirebaseAuth auth;
    FirebaseFirestore FStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_active_donations);

        backButt = findViewById(R.id.backButt);
        homeButt = findViewById(R.id.nbarHome2);
        dictButt = findViewById(R.id.nbarDictionary2);
        profileButt = findViewById(R.id.nbarProfile2);
        applyButton1 = findViewById(R.id.applyButton1);


        auth = FirebaseAuth.getInstance();
        FStore = FirebaseFirestore.getInstance();
        UserID = auth.getCurrentUser().getUid();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dItemArrayList = new ArrayList<DItem>();
        dAdapter = new DAdapter(ActiveDonations.this,dItemArrayList);
        recyclerView.setAdapter(dAdapter);
        EventChangeListener();

//        int count = dAdapter.getItemCount();
//        totalCount.setText("Total: "+count);

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
                Intent intent = new Intent(ActiveDonations.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        homeButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActiveDonations.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        dictButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActiveDonations.this, SignDictionary.class);
                startActivity(intent);
                finish();
            }
        });

        profileButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(un.equals("U")){
                    startActivity(new Intent(ActiveDonations.this, UserProfile.class));
                }
                else if(un.equals("A")){
                    startActivity(new Intent(ActiveDonations.this, AdminProfile.class));
                }
                else {
                    startActivity(new Intent(ActiveDonations.this, NGOProfile.class));
                }
                finish();
            }
        });

//        applyButton1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                applyButton1.setText("Applied");
//            }
//        });

    }

    private void EventChangeListener() {
        FStore.collection("DonaAllInfo").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.e("Firestore error : ", error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        dItemArrayList.add(dc.getDocument().toObject(DItem.class));
                    }
                    dAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}