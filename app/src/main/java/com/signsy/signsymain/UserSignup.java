package com.signsy.signsymain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Spinner;
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

public class UserSignup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText uSignupFName, uSignupLName, uSignupMail, uSignupMobile,uSignupPassword, uSignupDOB;
    Button signup, alreadySigned;

    Spinner spinner;

    String emailPattern, UserID;

    FirebaseAuth auth;
    FirebaseFirestore FStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_signup);


        uSignupFName = findViewById(R.id.uSignupFName);
        uSignupLName = findViewById(R.id.uSignupLName);
        uSignupMail = findViewById(R.id.uSignupMail);
        uSignupMobile = findViewById(R.id.uSignupMobile);
        uSignupPassword = findViewById(R.id.uSignupPassword);
        uSignupDOB = findViewById(R.id.uSignupDOB);

        signup = findViewById(R.id.signup);
        alreadySigned = findViewById(R.id.alreadySigned);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> arr_adapter = ArrayAdapter.createFromResource(this, R.array.userTypeStrings, R.layout.color_spinner_layout);
        arr_adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner.setAdapter(arr_adapter);
        spinner.setOnItemSelectedListener(this);


        emailPattern = "^[a-zA-Z0-9_.]+@[A-Za-z]+\\.[a-zA-Z]{2,4}$";

        auth = FirebaseAuth.getInstance();
        FStore = FirebaseFirestore.getInstance();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String FName, LName, Email, PhoneNumber, Password, Dob,UType;

                FName = uSignupFName.getText().toString().trim();
                LName = uSignupLName.getText().toString().trim();
                Email = uSignupMail.getText().toString().trim();
                PhoneNumber = uSignupMobile.getText().toString().trim();
                Password = uSignupPassword.getText().toString().trim();
                Dob = uSignupDOB.getText().toString().trim();
                UType = spinner.getSelectedItem().toString();


                if(UType.equals("Select User Type")){
                    Toast.makeText(UserSignup.this, "please select UserType", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(FName)){
                    Toast.makeText(UserSignup.this, "please enter your Firstname", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(LName)){
                    Toast.makeText(UserSignup.this, "please enter your Lastname", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Email)){
                    Toast.makeText(UserSignup.this, "please enter your Email", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(PhoneNumber)){
                    Toast.makeText(UserSignup.this, "please enter your valid PhoneNumber", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Password)){
                    Toast.makeText(UserSignup.this, "please enter your Password", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Dob)){
                    Toast.makeText(UserSignup.this, "please enter your Date of birth", Toast.LENGTH_SHORT).show();
                }else{
                    Register(FName, LName, Email, PhoneNumber, Password, Dob,UType);
                }

            }

        });


        alreadySigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserSignup.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
    }



    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



    private void Register(String FName, String LName, String Email, String PhoneNumber, String Password, String Dob, String UType){

        auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(UserSignup.this, new OnCompleteListener<AuthResult>() {
            @Override

            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    String NType = null;
                    String NgoGovNo = null;
                    String WEBSITE = null;

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

                    documentReference.set(UserInfo).addOnSuccessListener(UserSignup.this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            Toast.makeText(UserSignup.this, "Registration successful!!!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UserSignup.this,Login.class));

                        }
                    });
                }else {

                    Toast.makeText(UserSignup.this, "you already have account!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UserSignup.this,Login.class));

                }
            }
        });
    }
}