package com.signsy.signsymain;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class SignDescription extends AppCompatActivity {
    ImageView backButt13;
    String sign_keyword = "happy";
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;
    TextView signKeyword;
    VideoView videoView;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_description);

        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        signKeyword = findViewById(R.id.signKeyword);
        videoView = findViewById(R.id.videoView2);
        backButt13 = findViewById(R.id.backButt13);

        Intent intent = getIntent();
        String word =  intent.getStringExtra("word");
        signKeyword.setText(word);


        DocumentReference document = fireStore.collection("SignInfo").document(word);
        document.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                String link = documentSnapshot.getString("link");
                uri = Uri.parse(link);
                videoView.setVideoURI(uri);
                videoView.start();
            }
        });
        backButt13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SignDescription.this,SignDictionary.class);
                startActivity(intent1);
                finish();
            }
        });

    }
}