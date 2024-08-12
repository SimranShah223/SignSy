package com.signsy.signsymain;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class Rating extends AppCompatActivity {

    ImageView s1, s2, s3, s4, s5,backButt, homeButt, dictButt, profileButt, searchButt;
    EditText feedback;
    Button submitFeedback;

    String UserID, UserOrNgo,un;
    int count1,count2,count3,count4,count5 = 0;

    FirebaseAuth auth;
    FirebaseFirestore FStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_rating);

        s1 = findViewById(R.id.star1);
        s2 = findViewById(R.id.star2);
        s3 = findViewById(R.id.star3);
        s4 = findViewById(R.id.star4);
        s5 = findViewById(R.id.star5);


        backButt = findViewById(R.id.backButt5);
        homeButt = findViewById(R.id.nbarHome5);
        dictButt = findViewById(R.id.nbarDictionary5);
        profileButt = findViewById(R.id.nbarProfile5);
        searchButt = findViewById(R.id.nbarSearch5);

        feedback = findViewById(R.id.feedback);
        submitFeedback = findViewById(R.id.submitFeedback);

        auth = FirebaseAuth.getInstance();

        FStore = FirebaseFirestore.getInstance();
        UserID = auth.getCurrentUser().getUid();

        DocumentReference documentReference = FStore.collection("UserInfo").document(UserID);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                UserOrNgo = documentSnapshot.getString("UType");
                if(UserOrNgo  == null){
                    un = "N";
                }else{
                    un = "U";
                }
            }
        });

        backButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(un.equals("U")){
                    startActivity(new Intent(Rating.this, UserProfile.class));
                }else {
                    startActivity(new Intent(Rating.this, NGOProfile.class));
                }

                finish();
            }
        });

        homeButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Rating.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        dictButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Rating.this, SignDictionary.class);
                startActivity(intent);
                finish();
            }
        });

        profileButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(un.equals("U")){
                    startActivity(new Intent(Rating.this, UserProfile.class));
                }else {
                    startActivity(new Intent(Rating.this, NGOProfile.class));
                }

                finish();
            }
        });

        searchButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Rating.this, ActiveDonations.class));
                finish();
            }
        });

        s1.setOnClickListener(view -> {
            if(count1==0) {
                s1.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_24));
                s2.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_outline_24));
                s3.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_outline_24));
                s4.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_outline_24));
                s5.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_outline_24));

                count1 = 1;
                count2 = 0;
                count3 = 0;
                count4 = 0;
                count5 = 0;
            }
            else {
                unselectAll();
            }
        });

        s2.setOnClickListener(view -> {
            if (count2==0) {
                s1.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_24));
                s2.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_24));
                s3.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_outline_24));
                s4.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_outline_24));
                s5.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_outline_24));

                count1 = 0;
                count2 = 1;
                count3 = 0;
                count4 = 0;
                count5 = 0;
            }
            else {
                unselectAll();
            }
        });

        s3.setOnClickListener(view -> {
            if (count3==0) {
                s1.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_24));
                s2.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_24));
                s3.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_24));
                s4.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_outline_24));
                s5.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_outline_24));

                count1 = 0;
                count2 = 0;
                count3 = 1;
                count4 = 0;
                count5 = 0;
            }
            else {
                unselectAll();
            }
        });

        s4.setOnClickListener(view -> {
            if (count4==0) {
                s1.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_24));
                s2.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_24));
                s3.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_24));
                s4.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_24));
                s5.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_outline_24));

                count1 = 0;
                count2 = 0;
                count3 = 0;
                count4 = 1;
                count5 = 0;
            }
            else {
                unselectAll();
            }
        });

        s5.setOnClickListener(view -> {
            if (count5==0) {
                s1.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_24));
                s2.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_24));
                s3.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_24));
                s4.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_24));
                s5.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_24));

                count1 = 0;
                count2 = 0;
                count3 = 0;
                count4 = 0;
                count5 = 1;
            }
            else {
                unselectAll();
            }
        });


        submitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count;
                String desc;
                desc = feedback.getText().toString();

                if(TextUtils.isEmpty(desc)){
                    Toast.makeText(Rating.this, "please enter description!!", Toast.LENGTH_SHORT).show();
                }
                if (count1 == 1){
                    rating("1",desc);
                }
                if (count2 == 1){
                    rating("2",desc);
                }
                if (count3 == 1){
                    rating("3",desc);
                }
                if (count4 == 1){
                    rating("4",desc);
                }
                if (count5 == 1){
                    rating("5",desc);
                }

            }
        });


    }
    private void rating(String count,String desc){
        UserID = auth.getCurrentUser().getUid();

        DocumentReference documentReference = FStore.collection("RateInfo").document(UserID);

        Map<String,Object> RateInfo = new HashMap<>();

        RateInfo.put("Star", count);
        RateInfo.put("description", desc);

        documentReference.set(RateInfo).addOnSuccessListener(Rating.this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText(Rating.this, "submitted rating successful!!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Rating.this,HomePage.class));

            }
        });
    }

    void unselectAll() {
        s1.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_outline_24));
        s2.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_outline_24));
        s3.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_outline_24));
        s4.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_outline_24));
        s5.setImageDrawable(getDrawable(R.drawable.ic_baseline_star_outline_24));

        count1 = 0;
        count2 = 0;
        count3 = 0;
        count4 = 0;
        count5 = 0;

    }
}