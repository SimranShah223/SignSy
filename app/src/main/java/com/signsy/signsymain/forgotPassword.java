package com.signsy.signsymain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class forgotPassword extends AppCompatActivity {

    EditText fPassEmail;
    Button createAccount2;

    String UserID;

    FirebaseAuth auth;
    FirebaseFirestore FStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forgot_password);

        fPassEmail = findViewById(R.id.fPassEmail);
        createAccount2 = findViewById(R.id.createAccount2);

        auth = FirebaseAuth.getInstance();

        FStore = FirebaseFirestore.getInstance();
        UserID = auth.getCurrentUser().getUid();

        createAccount2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = fPassEmail.getText().toString();

                auth.sendPasswordResetEmail(Email).addOnSuccessListener(forgotPassword.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(forgotPassword.this, "Reset link is sent!!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(forgotPassword.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(forgotPassword.this, "something wrong happened please try later!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }


}