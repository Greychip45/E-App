package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class LoginActivity extends AppCompatActivity {

    private EditText email,password;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_password);
        pDialog = new ProgressDialog(getApplicationContext());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(email.getText().toString().equals("")){
                    email.setError("Please enter your email");
                } else if(password.getText().toString().equals("")){
                    password.setError("Please enter password");
                } else{
                    performAuth();
                }
            }
        });
    }

    //Method to login the user
    private void performAuth(){

        pDialog.show();
        mAuth = FirebaseAuth.getInstance();
        String uEmail = email.getText().toString();
        String uPassword = password.getText().toString();

        mAuth.signInWithEmailAndPassword(uEmail,uPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                sendUserToNextActivity();
                pDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "ERROR: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        });
    }
    //Send user to home activity
    private void sendUserToNextActivity(){
        Intent i = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}