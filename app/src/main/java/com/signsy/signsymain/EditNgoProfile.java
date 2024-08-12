package com.signsy.signsymain;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class EditNgoProfile extends AppCompatActivity {

    ImageView backButt, homeButt, dictButt, profileButt, searchButt;
    EditText editNgoName,editNgoType,editNgoGovNo,editNgoWebsite,editNgoEmail,editNgoPhone;
    Button saveNgoDetails, deleteNgo;

    String UserID;

    FirebaseAuth auth;
    FirebaseFirestore FStore;

    String File_name = "login_status.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_ngo_profile);

        backButt = findViewById(R.id.backButt9);
        homeButt = findViewById(R.id.nbarHome6_3);
        dictButt = findViewById(R.id.nbarDictionary6_3);
        profileButt = findViewById(R.id.nbarProfile6_3);
        searchButt = findViewById(R.id.nbarSearch6_3);

        editNgoName = findViewById(R.id.editUserName);
        editNgoType = findViewById(R.id.editUserType);
        editNgoGovNo = findViewById(R.id.editNgoGovNo);
        editNgoWebsite = findViewById(R.id.editUserDob);
        editNgoEmail = findViewById(R.id.editUserEmail);
        editNgoPhone = findViewById(R.id.editUserPhone);
        saveNgoDetails = findViewById(R.id.saveNgoDetails);
        deleteNgo = findViewById(R.id.deleteNgo);

        auth = FirebaseAuth.getInstance();
        FStore = FirebaseFirestore.getInstance();
        UserID = auth.getCurrentUser().getUid();


        DocumentReference documentReference = FStore.collection("UserInfo").document(UserID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                editNgoName.setText(documentSnapshot.getString("FirstName"));
                editNgoType.setText(documentSnapshot.getString("NType"));
                editNgoGovNo.setText(documentSnapshot.getString("NgoGovNo"));
                editNgoWebsite.setText(documentSnapshot.getString("Website"));
                editNgoEmail.setText(documentSnapshot.getString("Email"));
                editNgoPhone.setText(documentSnapshot.getString("PhoneNumber"));

            }
        });
        saveNgoDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editNgoName.getText().toString();
                String type = editNgoType.getText().toString();
                String gov = editNgoGovNo.getText().toString();
                String web = editNgoWebsite.getText().toString();
                String phn = editNgoPhone.getText().toString();
                String mail = editNgoEmail.getText().toString();

                DocumentReference documentReference1 = FStore.collection("UserInfo").document(UserID);
                documentReference1.update("FirstName",name);
                documentReference1.update("NType",type);
                documentReference1.update("NgoGovNo",gov);
                documentReference1.update("Website",web);
                documentReference1.update("PhoneNumber",phn);
                documentReference1.update("Email",mail);
                Toast.makeText(EditNgoProfile.this, "Updated Profile!!", Toast.LENGTH_SHORT).show();
            }
        });

        deleteNgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileOutputStream fos = null;

                try {
                    fos = openFileOutput(File_name, MODE_PRIVATE);
                    fos.write("Not Logged".getBytes());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(fos!=null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                startActivity(new Intent(EditNgoProfile.this, Login.class));
                Toast.makeText(EditNgoProfile.this, "Account Deleted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        backButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditNgoProfile.this, NGOProfile.class);
                startActivity(intent);
                finish();
            }
        });

        homeButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditNgoProfile.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        dictButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditNgoProfile.this, SignDictionary.class);
                startActivity(intent);
                finish();
            }
        });

        profileButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditNgoProfile.this, NGOProfile.class));
                finish();
            }
        });

        searchButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditNgoProfile.this, ActiveDonations.class));
                finish();
            }
        });
    }
}