package com.signsy.signsymain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NgoSignup extends AppCompatActivity {


    EditText ngoName, ngoType, nEmail,ngoPhone,ngoPassword,ngoGovNo,ngoWebsite;
    Button signup2,alreadySigned2;

    String emailPattern, UserID;

    FirebaseAuth auth;
    FirebaseFirestore FStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_ngo_signup);


        ngoName = findViewById(R.id.ngoName);
        ngoType = findViewById(R.id.ngoType);
        nEmail = findViewById(R.id.nEmail);
        ngoPhone = findViewById(R.id.ngoPhone);
        ngoPassword = findViewById(R.id.ngoPassword);
        ngoGovNo = findViewById(R.id.ngoGovNo);
        ngoWebsite = findViewById(R.id.ngoWebsite);

        signup2 = findViewById(R.id.signup2);
        alreadySigned2 = findViewById(R.id.alreadySigned2);

        auth = FirebaseAuth.getInstance();
        FStore = FirebaseFirestore.getInstance();


        signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String FName, NType, Email, PhoneNumber, Password, NgoGovNo,WEBSITE;

                FName = ngoName.getText().toString().trim();
                NType = ngoType.getText().toString().trim();
                Email = nEmail.getText().toString().trim();
                PhoneNumber = ngoPhone.getText().toString().trim();
                Password = ngoPassword.getText().toString().trim();
                NgoGovNo = ngoGovNo.getText().toString().trim();
                WEBSITE = ngoWebsite.getText().toString().trim();

                emailPattern = "^[a-zA-Z0-9_.]+@[A-Za-z]+\\.[a-zA-Z]{2,4}$";

                if(TextUtils.isEmpty(FName)){
                    Toast.makeText(NgoSignup.this, "please enter NGO Name", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(NType)){
                    Toast.makeText(NgoSignup.this, "please enter Type of Ngo", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Email)){
                    Toast.makeText(NgoSignup.this, "please enter your Email", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(PhoneNumber)){
                    Toast.makeText(NgoSignup.this, "please enter valid PhoneNumber", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Password)){
                    Toast.makeText(NgoSignup.this, "please enter your Password", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(NgoGovNo)){
                    Toast.makeText(NgoSignup.this, "please enter Ngo Government Number", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(WEBSITE)){
                    Toast.makeText(NgoSignup.this, "please enter link of your website", Toast.LENGTH_SHORT).show();
                }else{
                    Register(FName,NType,Email,PhoneNumber,Password,NgoGovNo,WEBSITE);
                }
            }
        });


        alreadySigned2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NgoSignup.this,Login.class));
            }
        });


    }

    private void Register(String FName, String NType, String Email, String PhoneNumber, String Password, String NgoGovNo, String WEBSITE){

        auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(NgoSignup.this, new OnCompleteListener<AuthResult>() {
            @Override

            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    String LName = null;
                    String UType = "NGO";
                    String Dob = null;

                    UserID = auth.getCurrentUser().getUid();

                    DocumentReference documentReference = FStore.collection("UserInfo").document(UserID);

                    Map<String,Object> UserInfo = new HashMap<>();

                    UserInfo.put("FirstName", FName);
                    UserInfo.put("LastName", LName);
                    UserInfo.put("UType", UType);
                    UserInfo.put("NType", NType);
                    UserInfo.put("PhoneNumber", PhoneNumber);
                    UserInfo.put("Email",Email);
                    UserInfo.put("Dob",Dob);
                    UserInfo.put("NgoGovNo",NgoGovNo);
                    UserInfo.put("Website", WEBSITE);

                    documentReference.set(UserInfo).addOnSuccessListener(NgoSignup.this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            Toast.makeText(NgoSignup.this, "Registration successful!!!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(NgoSignup.this,Login.class));

                        }
                    });
                }else {

                    Toast.makeText(NgoSignup.this, "you already have account!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(NgoSignup.this,Login.class));

                }

            }
        });
    }


}