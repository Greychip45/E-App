package com.example.test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ExtraUserDetailsActivity extends AppCompatActivity {

    private FirebaseUser fuser;
    private DatabaseReference dbRef;
    private Button btnNext,btnSkip,openCamera;
    private ProgressDialog pDialog;
    private EditText name,phone,email;
    private ImageView profile;
    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_extra_detail);

        btnNext = findViewById(R.id.btnNext);
        btnSkip = findViewById(R.id.btnSkip);
        name = findViewById(R.id.txt_name);
        phone = findViewById(R.id.txt_phone);
        email = findViewById(R.id.txt_email);
        openCamera = findViewById(R.id.btnOpenCamera);
        profile = findViewById(R.id.imgProfile);





        pDialog = new ProgressDialog(getApplicationContext());
        String userEmail = getIntent().getStringExtra("email");
        email.setText(userEmail);

        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(ExtraUserDetailsActivity.this)
                        .galleryOnly()
                        .start();
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHomeActivity();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uName = name.getText().toString();
                int uPhone = Integer.valueOf(phone.getText().toString());
                String uEmail = email.getText().toString();

                if(uName.equals("")){
                    name.setError("Please enter your name");
                }else if (uEmail.equals("")){
                    email.setError("Please enter your email");
                } else if(uPhone <= 10){
                    phone.setError("Please enter your phone");
                } else {
                    saveUserDetails();
                }

            }
        });

    }
    private void goToHomeActivity(){
        Intent i = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    private void saveUserDetails(){


        fuser = FirebaseAuth.getInstance().getCurrentUser();
        String uName = name.getText().toString();
        String uPhone = phone.getText().toString();
        String uEmail = email.getText().toString();
        String UID = fuser.getUid();


        pDialog.show();
        pDialog.setMessage("Please be patient..processing..");
        pDialog.setCanceledOnTouchOutside(false);

        dbRef  = FirebaseDatabase.getInstance().getReference("Users");
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("userId",UID);
        hashMap.put("name",uName);
        hashMap.put("email",uEmail);
        hashMap.put("phone",uPhone);
        dbRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pDialog.dismiss();
                goToHomeActivity();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pDialog.dismiss();
                Toast.makeText(ExtraUserDetailsActivity.this, "ERROR: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData() == null){
            uri = data.getData();
            Picasso.get().load(uri).placeholder(R.drawable.download).into(profile);
        } else {
            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
            Picasso.get().load(R.drawable.download).into(profile);
        }
    }
}