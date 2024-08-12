package com.signsy.signsymain;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.opencv.android.OpenCVLoader;

public class HomePage extends AppCompatActivity {

    ImageView play, activeDonationsButt, dictButt, userButt,playButton;
    TextView usersName,signName;
    Button speechToSign, signToSpeech;

    String UserID, UserOrNgo,un;

    FirebaseAuth auth;
    FirebaseFirestore FStore;
    static  {
        if(OpenCVLoader.initDebug()) {
            Log.d("Check", "OpenCV Loaded");
        }
        else {
            Log.d("Check", "OpenCV failed");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home_page);

        activeDonationsButt = findViewById(R.id.nbarSearch);
        dictButt = findViewById(R.id.nbarDictionary);
        speechToSign = findViewById(R.id.speechToSign);
        signToSpeech = findViewById(R.id.signToSpeech);
        userButt = findViewById(R.id.nbarProfile);
        usersName = findViewById(R.id.usersName);
        signName = findViewById(R.id.signName);
        auth = FirebaseAuth.getInstance();
        FStore = FirebaseFirestore.getInstance();
        UserID = auth.getCurrentUser().getUid();
        playButton = findViewById(R.id.playButton);

        String[] arrayWord ={"alright","animal","bathroom","beautiful","bedroom","bird","cat","clean","clock","clothing"};

        int max = arrayWord.length;
        int min = 1;
        Random randomNum = new Random();
        int showMe = min + randomNum.nextInt(max);

        DocumentReference doc = FStore.collection("SignInfo").document(arrayWord[showMe]);
        doc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                signName.setText(documentSnapshot.getString("word"));
                String word = documentSnapshot.getString("word");
                playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HomePage.this, SignDescription.class);
                        intent.putExtra("word", word);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        DocumentReference document = FStore.collection("UserInfo").document(UserID);
        document.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                usersName.setText(documentSnapshot.getString("FirstName"));
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

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast toast = Toast.makeText(getApplicationContext(), "Button Clicked", Toast.LENGTH_SHORT);
                toast.show();
            }
        });


        speechToSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, SpeechToSign.class));
                finish();
            }
        });

        signToSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this,SignToSpeech.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });


        activeDonationsButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, ActiveDonations.class);
                startActivity(intent);
                finish();
            }
        });


        dictButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, SignDictionary.class);
                startActivity(intent);
                finish();
            }
        });

        userButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(un.equals("U")){
                    startActivity(new Intent(HomePage.this, UserProfile.class));
                }
                else if(un.equals("A")){
                    startActivity(new Intent(HomePage.this, AdminProfile.class));
                }
                else {
                    startActivity(new Intent(HomePage.this, NGOProfile.class));
                }
                finish();
            }
        });
    }
}