package com.signsy.signsymain;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Locale;
import java.util.Objects;

public class SpeechToSign extends AppCompatActivity {

    ImageView mic, home, search, dictionary;
    TextView convertedText;
    VideoView videoView2;
    Uri uri;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    FirebaseFirestore FStore;
    FirebaseAuth auth;
    String userId;
    String[] arrayWord ={"alright","animal","bathroom","beautiful","bedroom","bird","cat","clean","clock","clothing","computer","cool","cow","curved","deaf","deep","dirty","dog","dream","dry",
                            "expensive","fan","fast","fish","good afternoon","good morning","grey","how are you"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_speech_to_sign);

        mic = findViewById(R.id.mic);
        convertedText = findViewById(R.id.convertedText);

        home = findViewById(R.id.nbarHome1_1);
        search = findViewById(R.id.nbarSearch1_1);
        dictionary = findViewById(R.id.nbarDictionary1_1);
        FStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        videoView2 = findViewById(R.id.videoView2);


        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                }
                catch (Exception e) {
                    Toast.makeText(SpeechToSign.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SpeechToSign.this, HomePage.class));
                finish();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SpeechToSign.this, ActiveDonations.class));
                finish();
            }
        });

        dictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SpeechToSign.this, SignDictionary.class));
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String resultString = Objects.requireNonNull(result).get(0);
                String firstWord = resultString.split(" ")[0];
                int count=0;

                for (String w:arrayWord) {
                    if(w.equals("good afternoon") || w.equals("good morning") || w.equals("how are you")) {
                        count=2;
                        break;
                    }
                    else if(w.equals(firstWord)) {
                        count=1;
                        break;
                    }
                }

                if(count==1) {
                    DocumentReference document = FStore.collection("SignInfo").document(firstWord);
                    document.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                            String link = documentSnapshot.getString("link");
                            uri = Uri.parse(link);
                            videoView2.setVideoURI(uri);
                            videoView2.start();
                        }
                    });

                    convertedText.setText((Objects.requireNonNull(result).get(0)).split(" ")[0]);
                } else if(count==2) {
                    DocumentReference document = FStore.collection("SignInfo").document(resultString);
                    document.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                            String link = documentSnapshot.getString("link");
                            uri = Uri.parse(link);
                            videoView2.setVideoURI(uri);
                            videoView2.start();
                        }
                    });

                    convertedText.setText((Objects.requireNonNull(result).get(0)));
                } else {
                    Toast.makeText(this, "Sign not found. It will be added soon.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}