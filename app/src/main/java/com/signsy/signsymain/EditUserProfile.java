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

public class EditUserProfile extends AppCompatActivity {

    ImageView backButt, homeButt, dictButt, profileButt, searchButt;
    EditText editUserName,editUserLName,editUserType,editUserPhone,editUserEmail,editUserDob;
    Button saveUserDetails, deleteUser;

    String UserID;

    FirebaseAuth auth;
    FirebaseFirestore FStore;

    String File_name = "login_status.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_user_profile);


        editUserName = findViewById(R.id.editUserName);
        editUserLName = findViewById(R.id.editUserLName);
        editUserType = findViewById(R.id.editUserType);
        editUserPhone = findViewById(R.id.editUserPhone);
        editUserEmail = findViewById(R.id.editUserEmail);
        editUserDob = findViewById(R.id.editUserDob);
        saveUserDetails = findViewById(R.id.saveUserDetails);
        deleteUser = findViewById(R.id.deleteUser);

        backButt = findViewById(R.id.backButt8);
        homeButt = findViewById(R.id.nbarHome4_1);
        dictButt = findViewById(R.id.nbarDictionary4_1);
        profileButt = findViewById(R.id.nbarProfile4_1);
        searchButt = findViewById(R.id.nbarSearch4_1);


        auth = FirebaseAuth.getInstance();

        FStore = FirebaseFirestore.getInstance();
        UserID = auth.getCurrentUser().getUid();

        DocumentReference documentReference = FStore.collection("UserInfo").document(UserID);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                editUserName.setText(documentSnapshot.getString("FirstName"));
                editUserLName.setText(documentSnapshot.getString("LastName"));
                editUserType.setText(documentSnapshot.getString("UType"));
                editUserPhone.setText(documentSnapshot.getString("PhoneNumber"));
                editUserEmail.setText(documentSnapshot.getString("Email"));
                editUserDob.setText(documentSnapshot.getString("Dob"));
            }
        });
        saveUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editUserName.getText().toString();
                String LName = editUserLName.getText().toString();
                String type = editUserType.getText().toString();
                String Dob = editUserDob.getText().toString();
                String phn = editUserPhone.getText().toString();
                String mail = editUserEmail.getText().toString();

                DocumentReference documentReference1 = FStore.collection("UserInfo").document(UserID);

                documentReference1.update("FirstName",name);
                documentReference1.update("LastName",LName);
                documentReference1.update("NType",type);
                documentReference1.update("Dob",Dob);
                documentReference1.update("PhoneNumber",phn);
                documentReference1.update("Email",mail);

                Toast.makeText(EditUserProfile.this, "Updated Profile!!", Toast.LENGTH_SHORT).show();
            }
        });

        deleteUser.setOnClickListener(new View.OnClickListener() {
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

                startActivity(new Intent(EditUserProfile.this, Login.class));
                Toast.makeText(EditUserProfile.this, "Account Deleted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        backButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(EditUserProfile.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        homeButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(EditUserProfile.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        dictButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(EditUserProfile.this, SignDictionary.class);
                startActivity(intent);
                finish();
            }
        });

        profileButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(EditUserProfile.this, UserProfile.class));
                finish();
            }
        });

        searchButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(EditUserProfile.this, ActiveDonations.class));
                finish();
            }
        });
    }
}