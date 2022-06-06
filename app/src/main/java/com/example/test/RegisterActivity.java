package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressDialog pDialog;
    Button btnRegister;
    FirebaseUser fuser;
    DatabaseReference mReference;
    private EditText userName,password,email,confirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        userName = findViewById(R.id.txt_name);
        password = findViewById(R.id.txt_password);
        email = findViewById(R.id.txt_email);
        confirmPassword = findViewById(R.id.txt_confirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        pDialog = new ProgressDialog(getApplicationContext());

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userName.getText().equals("")){
                    userName.setError("Please enter username");
                } else if(email.getText().toString().equals("")){
                    email.setError("Please enter Email");
                } else if(password.getText().toString().equals("")){
                    password.setError("Please enter password");
                } else if(confirmPassword.getText().toString().equals("")){
                    confirmPassword.setError("Please enter matching password");
                } else{
                    createUser();
                }

            }
        });

    }

    //Method to create new user
    private void createUser(){

        String uEmail = email.getText().toString();
        String uPwd = password.getText().toString();




        pDialog.show();
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setMessage("Please be patient...");
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(uEmail,uPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                pDialog.dismiss();
                sendUserToNextActivity();
                Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                saveUserData();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "ERROR: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    //Send user to home activity
    private void sendUserToNextActivity(){
        Intent i = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    private void saveUserData(){

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        String uEmail = email.getText().toString();

        String uName = userName.getText().toString();

        mReference = FirebaseDatabase.getInstance().getReference("Merchants");
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("userId",fuser.getUid());
        hashMap.put("name",uName);
        hashMap.put("email",uEmail);
        mReference.child(fuser.getUid()).setValue(hashMap);
    }
}