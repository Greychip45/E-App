package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class UserSignUpActivity extends AppCompatActivity {

    private Button btnRegister;
    private EditText email,password,confirmPassword;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_password);
        confirmPassword = findViewById(R.id.txt_confirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToAnotherActivity();
            }
        });



    }
    private void sendUserToAnotherActivity(){

        email = findViewById(R.id.txt_email);
        Intent i = new Intent(getApplicationContext(),ExtraUserDetailsActivity.class);
        i.putExtra("email","email");
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}