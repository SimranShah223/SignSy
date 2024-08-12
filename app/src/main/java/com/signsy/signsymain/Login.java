package com.signsy.signsymain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.time.Duration;

public class Login extends AppCompatActivity {

    EditText email, password;
    TextView resetPass;
    Button signup, login;

    FirebaseAuth auth;
    FirebaseFirestore FStore;
    String userID;
    String File_name = "login_status.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);


        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);

        resetPass = findViewById(R.id.loginResetPassword);

        signup = findViewById(R.id.createAccount);
        login = findViewById(R.id.login);

        auth = FirebaseAuth.getInstance();
        FStore = FirebaseFirestore.getInstance();
//        userID = auth.getCurrentUser().getUid();

        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login.this, forgotPassword.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login.this, PreSignup.class);
                startActivity(intent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();

                if(TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password)){
                    Toast.makeText(Login.this, "Please enter valid details", Toast.LENGTH_SHORT).show();
                }else {
                    loginUser(Email,Password);
                }

            }
        });

    }

    private void loginUser(String Email,String Password){

        auth.signInWithEmailAndPassword(Email,Password).addOnSuccessListener(Login.this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                FileOutputStream fos = null;

                try {
                    fos = openFileOutput(File_name, MODE_PRIVATE);
                    fos.write("Logged".getBytes());
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

                Toast.makeText(Login.this, "Successfully Logged IN!!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this,HomePage.class));

            }
        }).addOnFailureListener(Login.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, "please enter proper details!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}